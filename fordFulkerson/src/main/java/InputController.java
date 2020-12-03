import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputController extends Application {

    private Canvas knotCanvas;
    private GraphicsContext knotGc;
    private Canvas lineCanvas;
    private GraphicsContext lineGc;
    private Alert wrongArrow;
    private ChoiceDialog<String> dialog;
    private final ArrayList<DPoint> knots = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();

    private int id;
    private boolean dragging = false;
    private boolean currentPointIsCircle = false;
    private DPoint currentPoint;
    private DPoint source;
    private DPoint target;


    public InputController() {
    }

    /**
     * Creates the FXML GUI
     *
     * @param primaryStage This is the stage, the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Settings");
        //TODO Connect FXML to rest of code

        GridPane pane = new GridPane();
        lineCanvas = new Canvas(750, 500);
        knotCanvas = new Canvas(750, 500);
        Button startButton = new Button("start");
        Button clearButton = new Button("clear");
        lineGc = lineCanvas.getGraphicsContext2D();
        knotGc = knotCanvas.getGraphicsContext2D();

        startButton.setOnAction(this::handleButtonStart);
        clearButton.setOnAction(this::handleButtonClear);

        pane.add(lineCanvas, 0, 0, 3, 4);
        pane.add(knotCanvas, 0, 0, 3, 4);
        pane.add(startButton, 3, 0);
        pane.add(clearButton, 3, 1);

        Scene scene = new Scene(pane, 800, 500);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED,
                this::mouseClickAction);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::mouseDragAction);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, this::mousePressAction);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED,
                this::mouseReleaseAction);

        knotCanvas.setOnMouseClicked(this::mouseClickAction);
        knotCanvas.setOnMouseDragged(this::mouseDragAction);
        knotCanvas.setOnMousePressed(this::mousePressAction);
        knotCanvas.setOnMouseReleased(this::mouseReleaseAction);

        primaryStage.setScene(scene);
        primaryStage.show();

        startUp();
    }

    private void clearCanvas(){
        knots.clear();
        edges.clear();

        lineGc.clearRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight());
        knotGc.clearRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight());
        knotGc.setFill(Color.DARKGREEN);
        knotGc.fillOval(30, knotCanvas.getHeight() / 2, 25, 25);
        knotGc.fillOval(knotCanvas.getWidth() - 55, knotCanvas.getHeight() / 2, 25, 25);

        knots.add(source);
        knots.add(target);
        currentPoint = source;
    }


    /**
     * StartUp
     */
    private void startUp() {
        wrongArrow = new Alert(Alert.AlertType.ERROR);
        wrongArrow.setTitle("Error Dialog");
        wrongArrow.setHeaderText("Impossible Arrow");

        List<String> choices = new ArrayList<>();
        choices.add("1");
        choices.add("2");
        choices.add("3");
        choices.add("4");
        choices.add("5");
        choices.add("6");
        choices.add("7");
        choices.add("8");
        choices.add("9");

        dialog = new ChoiceDialog<>("1", choices);
        dialog.setTitle("");
        dialog.setHeaderText("");
        dialog.setContentText("Choose the value of your line");

        id = 0;
        source = new DPoint(30.0, knotCanvas.getHeight() / 2.0, id);
        id++;
        target = new DPoint(knotCanvas.getWidth() - 55.0, knotCanvas.getHeight() / 2, id);

        clearCanvas();
    }


    /**
     * If the mouse is realeased
     *
     * @param mouseEvent Detects the mouse
     */
    private void mouseReleaseAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        DPoint endPoint = isThereCircle(mouseX, mouseY);

        System.out.println("Mouse Released");
        if (dragging && !(endPoint == null)) {

            //arow part
            double ex = endPoint.getPosX() + 10;
            double ey = endPoint.getPosY() + 10;
            double sx = currentPoint.getPosX() + 10;
            double sy = currentPoint.getPosY() + 10;

            Line arrow1 = new Line();
            Line arrow2 = new Line();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double hypot = Math.hypot(sx - ex, sy - ey);
                double factor = 20 / hypot;
                double factorO = 7 / hypot;

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }


            //checks if the arrow points at the source point
            if(currentPoint.getPosX() == target.getPosX() && currentPoint.getPosY() == target.getPosY()){
                System.out.println("ERROR: arrow from target point");

                wrongArrow.setContentText("You cannot draw an arrow from the target point!");
                wrongArrow.showAndWait();
            //Checks if the arrow starts at the target point
            } else if (endPoint.getPosX() == source.getPosX() && endPoint.getPosY() == source.getPosY()) {
                System.out.println("ERROR: arrow to source point");

                wrongArrow.setContentText("You cannot point an arrow at the source point!");
                wrongArrow.showAndWait();

            } else {
                int choice = 0;
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    choice = Integer.parseInt(result.get());
                    System.out.println("Your choice: " + choice);


                    System.out.println(choice);

                    double textXPos = currentPoint.getPosX() + endPoint.getPosX();
                    textXPos = textXPos / 2;
                    double textYPos = currentPoint.getPosY() + endPoint.getPosY();
                    textYPos = textYPos / 2;


                    //TODO place the text better -> always visible
                    knotGc.setFill(Color.BLACK);
                    knotGc.fillText(result.get(), textXPos, textYPos);
                    lineGc.setFill(Color.DARKBLUE);
                    lineGc.strokeLine(currentPoint.getPosX() + 10, currentPoint.getPosY() + 10, endPoint.getPosX() + 10, endPoint.getPosY() + 10);
                    lineGc.strokeLine(arrow1.getStartX(), arrow1.getStartY(), arrow1.getEndX(), arrow1.getEndY());
                    lineGc.strokeLine(arrow2.getStartX(), arrow2.getStartY(), arrow2.getEndX(), arrow2.getEndY());

                    System.out.print("Pfeil gezeichnet");

                    edges.add(new Edge(currentPoint, endPoint, choice));
                } else {
                    wrongArrow.setContentText("You have to enter a number value to draw an arrow!");
                    wrongArrow.showAndWait();
                }



            }
        }

        currentPointIsCircle = false;
        dragging = false;
    }


    /**
     * if the mouse is pressed
     *
     * @param mouseEvent Detects the mouse
     */
    private void mouseDragAction(MouseEvent mouseEvent) {
        System.out.println("Mouse Dragged");

        if (currentPointIsCircle) {
            dragging = true;
        }
    }

    /**
     * If the mouse is pressed
     *
     * @param mouseEvent Detects the mouse
     */
    private void mousePressAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        System.out.println("Mouse Pressed");

        if (isThereCircle(mouseX, mouseY) != null) {
            currentPoint = isThereCircle(mouseX, mouseY);
            currentPointIsCircle = true;
        }
    }

    /**
     * If the mouse is clicked
     *
     * @param mouseEvent Detects the mouse
     */
    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        System.out.println("Mouse Clicked");

        //checks if the current location is good for drawing a circle
        if (checkClick(mouseX, mouseY) && !dragging) {
            id += 1;
            knotGc.setFill(Color.DARKBLUE);
            knotGc.fillOval(mouseX - 10, mouseY - 10, 20, 20);
            currentPoint = new DPoint(mouseX - 10, mouseY - 10, id);
            knots.add(currentPoint);
            System.out.println(mouseX + "\t" + mouseY);
        }

        //if there is a circle, the currentpoint gets updated
        if (isThereCircle(mouseX, mouseY) != null) {
            for (DPoint dp : knots) {
                if (dp.getPosX() == mouseX && dp.getPosY() == mouseY) {
                    currentPoint = dp;
                }
            }
        }
    }


    /**
     * Detects if there is a circle on the position clicked
     *
     * @param mouseX Pos X of mouse
     * @param mouseY Pos Y of mouse
     * @return returns a DPoint if one is at the position clicked
     */
    private DPoint isThereCircle(double mouseX, double mouseY) {
        for (DPoint dp : knots) {
            double width = dp.getRadius() * 2;
            if (mouseY < (dp.getPosY() + width) && mouseY > (dp.getPosY() - width / 2))
                if (mouseX < (dp.getPosX() + width) && mouseX > (dp.getPosX() - width / 2)) {
                    return dp;
                }
        }
        return null;
    }

    /**
     * Checks if the position clicked is optimal for a circle
     *
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     * @return Returns boolean, whether or not it is optimal for a circle
     */
    private boolean checkClick(double mouseX, double mouseY) {
        //x-border
        if (mouseX < 75 || mouseX > knotCanvas.getWidth() - 75) {
            return false;
        }

        //x-Border
        if (mouseY < 30 || mouseY > knotCanvas.getHeight() - 30) {
            return false;
        }

        if (isThereCircle(mouseX, mouseY) != null) {
            return false;
        }

        return knots.size() <= 10;
    }

    /**
     *
     * @param event
     * @throws ImpossibleOrderException
     * @throws ImpossibleBottleNeckValueException
     */
    public void handleButtonStart(ActionEvent event){
        System.out.println("Person Button pressed");

        try {
            MaxFlow mf = new MaxFlow(edges);
        } catch (ImpossibleBottleNeckValueException e) {
            System.out.println(e.getMessage());
        } catch (ImpossibleOrderException m){
            System.out.println(m.getMessage());
        }

        //src: https://stackoverflow.com/questions/27160951/javafx-open-another-fxml-in-the-another-window-with-button
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/visualisation.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Result Visualisation");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            System.out.println("Result Visualisation open");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleButtonClear(ActionEvent actionEvent) {
        startUp();
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public static void main(String[] args) {
        launch(args);
    }
}