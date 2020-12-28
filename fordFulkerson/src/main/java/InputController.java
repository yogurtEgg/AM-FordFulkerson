import javafx.application.Application;
import javafx.event.ActionEvent;
<<<<<<< Updated upstream
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
=======
import javafx.geometry.Insets;
>>>>>>> Stashed changes
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
    private Alert wrongPoint;
    private ChoiceDialog<String> dialog;

    private final Button startButton = new Button("start");
    private final Button clearButton = new Button("clear");
    private final Button editButton = new Button("disable editing");

    private final ArrayList<DPoint> knots = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();

    private int id;
    private boolean dragging = false;
    private boolean currentPointIsCircle = false;
<<<<<<< Updated upstream
=======
    private boolean canEdit;
>>>>>>> Stashed changes
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

        GridPane pane = new GridPane();
        HBox buttonBox = new HBox();

        buttonBox.setPadding(new Insets(10, 50, 50, 50));
        buttonBox.setSpacing(10);

        lineCanvas = new Canvas(900, 500);
        knotCanvas = new Canvas(900, 500);
        lineGc = lineCanvas.getGraphicsContext2D();
        knotGc = knotCanvas.getGraphicsContext2D();

        startButton.setOnAction(this::handleButtonStart);
        clearButton.setOnAction(this::handleButtonClear);
        editButton.setOnAction(this::handleButtonEdit);

        startButton.isDefaultButton();
        startButton.setPadding(new Insets(10, 10, 10, 10));
        clearButton.setPadding(new Insets(10, 10, 10, 10));
        editButton.setPadding(new Insets(10, 10, 10, 10));

        pane.add(lineCanvas, 0, 0, 3, 4);
        pane.add(knotCanvas, 0, 0, 3, 4);
        pane.add(buttonBox, 3, 4, 4, 1);

        buttonBox.getChildren().add(startButton);
        buttonBox.getChildren().add(clearButton);
        buttonBox.getChildren().add(editButton);

        Scene scene = new Scene(pane, 1000, 600);
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

<<<<<<< Updated upstream
=======
        canEdit = true;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
        canEdit = true;

>>>>>>> Stashed changes
        wrongArrow = new Alert(Alert.AlertType.ERROR);
        wrongArrow.setTitle("Error Dialog");
        wrongArrow.setHeaderText("Impossible Arrow");

        wrongPoint = new Alert(Alert.AlertType.ERROR);
        wrongPoint.setTitle("Error Dialog");
        wrongPoint.setHeaderText("Impossible Point");

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
     * If the mouse is released
     *
     * @param mouseEvent Detects the mouse
     */
    private void mouseReleaseAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

<<<<<<< Updated upstream
        DPoint endPoint = isThereCircle(mouseX, mouseY);

        System.out.println("Mouse Released");
=======
        DPoint endPoint = setCurrentCircle(mouseX, mouseY);

>>>>>>> Stashed changes
        if (dragging && !(endPoint == null)) {

            //arrow part
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

                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

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

<<<<<<< Updated upstream
=======
            } else if (sameCircle(currentPoint, endPoint)) {
                out.println("ERROR: arrow to same circle");

                wrongArrow.setContentText("You cannot have the endpoint be the same as the start point!");
                wrongArrow.showAndWait();
            } else if (!canEdit) {
                out.println("ERROR: editing is turned off");

                wrongArrow.setContentText("Make sure that editing is turned on again!");
                wrongArrow.showAndWait();
>>>>>>> Stashed changes
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

                    knotGc.setFill(Color.BLACK);
                    knotGc.fillText(result.get(), textXPos, textYPos);
                    lineGc.setFill(Color.DARKBLUE);
                    lineGc.strokeLine(currentPoint.getPosX() + 10, currentPoint.getPosY() + 10, endPoint.getPosX() + 10, endPoint.getPosY() + 10);
                    lineGc.strokeLine(arrow1.getStartX(), arrow1.getStartY(), arrow1.getEndX(), arrow1.getEndY());
                    lineGc.strokeLine(arrow2.getStartX(), arrow2.getStartY(), arrow2.getEndX(), arrow2.getEndY());

<<<<<<< Updated upstream
                    System.out.print("Pfeil gezeichnet");
=======
                    out.print("arrow painted");
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
        System.out.println("Mouse Dragged");

=======
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        System.out.println("Mouse Pressed");

        if (isThereCircle(mouseX, mouseY) != null) {
            currentPoint = isThereCircle(mouseX, mouseY);
=======
        if (circleExists(new DPoint(mouseX, mouseY))) {
            setCurrentCircle(mouseX, mouseY);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

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
=======
        System.out.flush();
        out.println("Mouse Clicked");
        if (canEdit) {
             /*checks if the current location is good for drawing a circle*/if (checkClick(mouseX, mouseY) && !dragging) {
                id += 1;
                knotGc.setFill(Color.DARKBLUE);
                knotGc.fillOval(mouseX - 10, mouseY - 10, 20, 20);
                currentPoint = new DPoint(mouseX - 10, mouseY - 10, id);
                knots.add(currentPoint);
                out.println(mouseX + "\t" + mouseY);
            }
            /* if there is a circle, the current point gets updated */

        } else {
            out.println("ERROR: editing disabled");

            wrongPoint.setContentText("You cannot draw when editing is disabled!");
            wrongPoint.showAndWait();
>>>>>>> Stashed changes
        }
    }


    /**
     * Detects if there is a circle on the position clicked
     *
     * @param mouseX Pos X of mouse
     * @param mouseY Pos Y of mouse
     * @return returns a DPoint if one is at the position clicked
     */
<<<<<<< Updated upstream
    private DPoint isThereCircle(double mouseX, double mouseY) {
=======
    private DPoint setCurrentCircle(double mouseX, double mouseY) {
>>>>>>> Stashed changes
        for (DPoint dp : knots) {
            if (mouseY < (dp.getPosY() + 40) && mouseY > (dp.getPosY() - 20))
                if (mouseX < (dp.getPosX() + 40) && mouseX > (dp.getPosX() - 20)) {
                    currentPoint = dp;
                    return dp;
                }
        }
        return null;
    }

<<<<<<< Updated upstream
=======

    /**
     * Checks if the circle already exists
     * @param mouseClick temp point
     * @return returns boolean
     */
    private boolean circleExists(DPoint mouseClick) {
        for (DPoint dPoint : knots) {
            System.out.println(sameCircle(mouseClick, dPoint));
            if (sameCircle(mouseClick, dPoint)) return true;
        }
        return false;
    }

    /**
     * checks if the two circles are the same
     * @param dPoint1 first point
     * @param dPoint2 second point
     * @return returns boolean
     */
    private boolean sameCircle(DPoint dPoint1, DPoint dPoint2) {
        if (dPoint1.getPosY() < (dPoint2.getPosY() + 40) && dPoint1.getPosY() > (dPoint2.getPosY() - 20))
            return dPoint1.getPosX() < (dPoint2.getPosX() + 40) && dPoint1.getPosX() > (dPoint2.getPosX() - 20);
        return false;
    }

    /**
     * checks if edge already exists
     * @param e temp edge
     * @return returns boolean
     */
    private boolean thereIsEdge(Edge e) {
        for (Edge d : edges) {
            if (sameCircle(e.getStartPoint(), d.getStartPoint()) && sameCircle(e.getEndPoint(), d.getEndPoint())) {
                return true;
            } else if (sameCircle(e.getStartPoint(), d.getEndPoint()) && sameCircle(e.getEndPoint(), d.getStartPoint())) {
                return true;
            }
        }

        return false;
    }

>>>>>>> Stashed changes
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
            out.println("ERROR: point is too far out");

            wrongPoint.setContentText("You cannot draw a point outside the border!");
            wrongPoint.showAndWait();

            return false;
        }

        //x-Border
        if (mouseY < 30 || mouseY > knotCanvas.getHeight() - 30) {
            out.println("ERROR: point is too far out");

            wrongPoint.setContentText("You cannot draw a point outside the border!");
            wrongPoint.showAndWait();

            return false;
        }

<<<<<<< Updated upstream
        if (isThereCircle(mouseX, mouseY) != null) {
=======
        if (circleExists(new DPoint(mouseX, mouseY))) {
            out.println("ERROR: circle already exists");

            wrongPoint.setContentText("You cannot draw an arrow from the target point!");
            wrongPoint.showAndWait();

            setCurrentCircle(mouseX, mouseY);

>>>>>>> Stashed changes
            return false;
        }
 if(knots.size() <= 10){
     out.println("ERROR: arrow from target point");

     wrongArrow.setContentText("You cannot draw an arrow from the target point!");
     wrongArrow.showAndWait();

     return false;
 }
 return true;
    }

    /**
<<<<<<< Updated upstream
     *
     * @param event
     * @throws ImpossibleOrderException
     * @throws ImpossibleBottleNeckValueException
     */
    public void handleButtonStart(ActionEvent event){
        //TODO: Wenn Start gedrückt -> keine Veränderung mehr
        //TODO: Nicht sich selbst verbinden
        //TODO:
        System.out.println("Person Button pressed");
=======
     * starts the calculating of the solution
     * @param event button event
     */
    public void handleButtonStart(ActionEvent event) {
        out.println("Person Button pressed");
        canEdit = false;
>>>>>>> Stashed changes

        try {
            MaxFlow mf = new MaxFlow(edges, new VisualisationController());
        } catch (ImpossibleBottleNeckValueException e) {
            System.out.println(e.getMessage());
        } catch (ImpossibleOrderException m){
            System.out.println(m.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * clears canvas
     * @param actionEvent button event
     */
    public void handleButtonClear(ActionEvent actionEvent) {
        startUp();
    }

<<<<<<< Updated upstream
    public ArrayList<Edge> getEdges(){
        return edges;
    }

=======
    /**
     * Enables and disables editing
     * @param actionEvent button event
     */
    private void handleButtonEdit(ActionEvent actionEvent) {
        if (canEdit) {
            canEdit = false;
            editButton.setText("disable editing");
        } else {
            canEdit = true;
            editButton.setText("enable editing");
        }
    }
>>>>>>> Stashed changes
    public static void main(String[] args) {
        launch(args);
    }
}