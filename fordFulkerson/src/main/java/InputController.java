import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;

public class InputController extends Application {

    private Canvas knotCanvas;
    private GraphicsContext knotGc;
    private Canvas lineCanvas;
    private GraphicsContext lineGc;
    private Alert wrongArrow;
    private Alert wrongPoint;
    private ChoiceDialog<String> dialog;
    private Button editButton;

    private final ArrayList<DPoint> knots = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private int id;
    private boolean dragging = false;
    private boolean editEnabled;
    private DPoint currentPoint;
    private DPoint source;
    private DPoint target;
    private DPoint startPoint;
    private DPoint endPoint;


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

        GridPane pane = new GridPane();
        HBox buttons = new HBox();

        buttons.setPadding(new Insets(10, 50, 50, 50));
        buttons.setSpacing(10);

        lineCanvas = new Canvas(850, 500);
        knotCanvas = new Canvas(850, 500);
        lineGc = lineCanvas.getGraphicsContext2D();
        knotGc = knotCanvas.getGraphicsContext2D();

        Button startButton = new Button("start");
        Button clearButton = new Button("clear");
        editButton = new Button("edit enabled");

        startButton.setOnAction(this::handleButtonStart);
        clearButton.setOnAction(this::handleButtonClear);
        editButton.setOnAction(this::handleButtonEdit);

        pane.add(lineCanvas, 0, 0, 3, 4);
        pane.add(knotCanvas, 0, 0, 3, 4);
        pane.add(buttons, 0, 4, 3, 1);

        buttons.getChildren().add(editButton);
        buttons.getChildren().add(startButton);
        buttons.getChildren().add(clearButton);

        Scene scene = new Scene(pane, 900, 600);
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

    /**
     * StartUp
     */
    private void startUp() {
        wrongPoint = new Alert(Alert.AlertType.ERROR);
        wrongPoint.setTitle("Error Dialog");
        wrongPoint.setHeaderText("Impossible Point");

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

    private void clearCanvas() {
        knots.clear();
        edges.clear();

        editEnabled = true;

        lineGc.clearRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight());
        knotGc.clearRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight());
        knotGc.setFill(Color.DARKGREEN);
        knotGc.fillOval(30, knotCanvas.getHeight() / 2, 25, 25);
        knotGc.fillOval(knotCanvas.getWidth() - 55, knotCanvas.getHeight() / 2, 25, 25);

        knots.add(source);
        knots.add(target);

        currentPoint = source;
        startPoint = target;
    }


    /**
     * If the mouse is released
     *
     * @param mouseEvent Detects the mouse
     */
    private void mouseReleaseAction(MouseEvent mouseEvent) {
        out.println("Mouse Released");

        if (dragging) {
            double mouseX = mouseEvent.getX();
            double mouseY = mouseEvent.getY();
            endPoint = new DPoint(mouseX, mouseY);
            fixEndpoint();

            if (endPoint != null && editEnabled) {

                //arrow part
                double ex = endPoint.getPosX() + 10;
                double ey = endPoint.getPosY() + 10;
                double sx = startPoint.getPosX() + 10;
                double sy = startPoint.getPosY() + 10;

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

                    // part diagonal to main line
                    double ox = (sx - ex) * factorO;
                    double oy = (sy - ey) * factorO;

                    arrow1.setStartX(ex + dx - oy);
                    arrow1.setStartY(ey + dy + ox);
                    arrow2.setStartX(ex + dx + oy);
                    arrow2.setStartY(ey + dy - ox);
                }


                //checks if the arrow points at the source point
                if (startPoint.getPosX() == target.getPosX() && startPoint.getPosY() == target.getPosY()) {
                    out.println("ERROR: arrow from target point");

                    wrongArrow.setContentText("You cannot draw an arrow from the target point!");
                    wrongArrow.showAndWait();
                    //Checks if the arrow starts at the target point
                } else if (endPoint.getPosX() == source.getPosX() && endPoint.getPosY() == source.getPosY()) {
                    out.println("ERROR: arrow to source point");

                    wrongArrow.setContentText("You cannot point an arrow at the source point!");
                    wrongArrow.showAndWait();

                } else if (samePoint(startPoint, endPoint)) {
                    out.println("ERROR: arrow to same point");

                    wrongArrow.setContentText("You cannot have the endpoint be the same as the start point!");
                    wrongArrow.showAndWait();
                } else {
                    int choice;
                    Optional<String> result = dialog.showAndWait();
                    if (thereIsEdge(new Edge(startPoint, endPoint))) {
                        out.println("ERROR: arrow already exists");

                        wrongArrow.setContentText("You cannot draw an arrow that already exists!");
                        wrongArrow.showAndWait();
                    } else if (result.isPresent()) {
                        choice = Integer.parseInt(result.get());
                        out.println("Your choice: " + choice);
                        out.println(choice);

                        double textXPos = startPoint.getPosX() + endPoint.getPosX();
                        textXPos = textXPos / 2;
                        double textYPos = startPoint.getPosY() + endPoint.getPosY();
                        textYPos = textYPos / 2;

                        knotGc.setFill(Color.BLACK);
                        knotGc.fillText(result.get(), textXPos, textYPos);
                        lineGc.setFill(Color.DARKBLUE);
                        lineGc.strokeLine(startPoint.getPosX() + 10, startPoint.getPosY() + 10, endPoint.getPosX() + 10, endPoint.getPosY() + 10);
                        lineGc.strokeLine(arrow1.getStartX(), arrow1.getStartY(), arrow1.getEndX(), arrow1.getEndY());
                        lineGc.strokeLine(arrow2.getStartX(), arrow2.getStartY(), arrow2.getEndX(), arrow2.getEndY());

                        out.print("Arrow drawn");

                        edges.add(new Edge(startPoint, endPoint, choice));
                    } else {
                        wrongArrow.setContentText("You have to enter a number value to draw an arrow!");
                        wrongArrow.showAndWait();
                    }
                }
            } else {
                if (!editEnabled) {
                    out.println("ERROR: edit disabled");

                    wrongArrow.setContentText("Editing is disabled, make sure to enable it!");
                    wrongArrow.showAndWait();
                }
            }

            dragging = false;
        }

    }

    /**
     * makes endpoint an existing point
     *
     */
    private void fixEndpoint() {
        for (DPoint dPoint : knots) {
            if (samePoint(endPoint, dPoint)) {
                endPoint = dPoint;
                return;
            }
        }
        endPoint = null;
    }


    /**
     * if the mouse is pressed
     *
     * @param mouseEvent Detects the mouse
     */
    private void mouseDragAction(MouseEvent mouseEvent) {
        out.println("Mouse Dragged");

        dragging = true;
    }

    /**
     * If the mouse is pressed
     *
     * @param mouseEvent Detects the mouse
     */
    private void mousePressAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();


        out.println("Mouse Pressed");

        if (pointExists(new DPoint(mouseX, mouseY))) {
            setCurrentPoint(mouseX, mouseY);
            setStartPoint(currentPoint);
        }
    }

    private void setStartPoint(DPoint dPoint) {
        startPoint = dPoint;
    }

    /**
     * If the mouse is clicked
     *
     * @param mouseEvent Detects the mouse
     */
    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        out.println("Mouse Clicked");
        if (editEnabled && !editButton.isPressed()) {
            if (checkClick(mouseX, mouseY) && !dragging) {
                id += 1;
                knotGc.setFill(Color.DARKBLUE);
                knotGc.fillOval(mouseX - 10, mouseY - 10, 20, 20);
                currentPoint = new DPoint(mouseX - 10, mouseY - 10, id);
                knots.add(currentPoint);
                out.println(mouseX + "\t" + mouseY);
            }
        }
    }


    /**
     * Detects if there is a point on the position clicked
     *
     * @param mouseX Pos X of mouse
     * @param mouseY Pos Y of mouse
     */
    private void setCurrentPoint(double mouseX, double mouseY) {
        for (DPoint dp : knots) {
            double width = dp.getRadius() * 2;
            if (mouseY < (dp.getPosY() + width) && mouseY > (dp.getPosY() - width / 2))
                if (mouseX < (dp.getPosX() + width) && mouseX > (dp.getPosX() - width / 2)) {
                    currentPoint = dp;
                    return;
                }
        }
    }

    /**
     * checks if the point exists
     *
     * @param pointToCheck point to
     * @return whether or not the point exists
     */
    private boolean pointExists(DPoint pointToCheck) {
        for (DPoint dPoint : knots) {
            if (samePoint(pointToCheck, dPoint)) {
                currentPoint = dPoint;
                return true;
            }
        }
        return false;
    }

    /**
     * compares two DPoints
     *
     * @param firstPoint  first point to compare
     * @param secondPoint second point to compare
     * @return whether or not the points are the same
     */
    private boolean samePoint(DPoint firstPoint, DPoint secondPoint) {
        if (firstPoint.getPosY() < (secondPoint.getPosY() + 60) && firstPoint.getPosY() > (secondPoint.getPosY() - 20))
            return firstPoint.getPosX() < (secondPoint.getPosX() + 60) && firstPoint.getPosX() > (secondPoint.getPosX() - 20);
        return false;
    }

    /**
     * @param e edge to control
     * @return whether or not the edge already exists
     */
    private boolean thereIsEdge(Edge e) {
        for (Edge d : edges) {

            if (samePoint(e.getStartPoint(), d.getStartPoint()) && samePoint(e.getEndPoint(), d.getEndPoint())) {
                return true;
            } else if (samePoint(e.getStartPoint(), d.getEndPoint()) && samePoint(e.getEndPoint(), d.getStartPoint())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the position clicked is optimal for a point
     *
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     * @return Returns boolean, whether or not it is optimal for a point
     */
    private boolean checkClick(double mouseX, double mouseY) {
        if (pointExists(new DPoint(mouseX, mouseY))) {
            return false;
        }

        if (knots.size() + 1 >= 12) {
            wrongPoint.setContentText("You can only place a maximum of 10 points!");
            wrongPoint.showAndWait();
            return false;
        }

        if (mouseX < 75 || mouseX > knotCanvas.getWidth() - 75 || mouseY < 30 || mouseY > knotCanvas.getHeight() - 30) {
            return false;
        }

        return true;
    }

    /**
     * when start button is pressed
     *
     * @param event handles button
     */
    public void handleButtonStart(ActionEvent event) {
        out.println("Person Button pressed");
        editEnabled = false;

        try {
            new MaxFlow(edges, new VisualisationController());
            System.out.println(MaxFlow.getSolution());
        } catch (ImpossibleBottleNeckValueException | ImpossibleOrderException e) {
            out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * When clear button is pressed
     *
     * @param actionEvent handles button
     */
    public void handleButtonClear(ActionEvent actionEvent) {
        startUp();
    }

    /**
     * edit button is pressed
     *
     * @param actionEvent handle button event
     */
    private void handleButtonEdit(ActionEvent actionEvent) {
        if (editEnabled) {
            editEnabled = false;
            editButton.setText("enable Editing");
        } else {
            editEnabled = true;
            editButton.setText("disable Editing");
        }
    }

    public static void main(String[] args) {
        System.out.print("Program started");
        launch(args);
    }
}