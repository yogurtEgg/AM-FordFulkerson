import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class inputController extends Application {

    private Canvas knotCanvas;
    private GraphicsContext knotGc;
    private Canvas lineCanvas;
    private GraphicsContext lineGc;
    private final ArrayList<DPoint> knots = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private int id;
    private boolean dragging = false;
    boolean currentPointIsCircle = false;
    private DPoint currentPoint;


    public inputController() {
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

        Scene scene = new Scene(pane, 500, 300);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED,
                this::mouseClickAction);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::mouseClickAction);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,
                this::mouseClickAction);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED,
                this::mouseClickAction);

        knotCanvas.setOnMouseClicked(this::mouseClickAction);
        knotCanvas.setOnMousePressed(this::mousePressAction);
        knotCanvas.setOnMouseDragged(this::mouseDragAction);
        knotCanvas.setOnMouseReleased(this::mouseReleaseAction);

        primaryStage.setScene(scene);
        primaryStage.show();

        startUp();
    }

    /**
     * StartUp
     */
    private void startUp() {
        knots.clear();
        lineGc.setFill(Color.PAPAYAWHIP);
        lineGc.fillRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight());
        knotGc.setFill(Color.DARKGREEN);
        knotGc.fillOval(30, knotCanvas.getHeight() / 2, 25, 25);
        knotGc.fillOval(knotCanvas.getWidth() - 55, knotCanvas.getHeight() / 2, 25, 25);

        id = 0;

        knots.add(new DPoint(30.0, knotCanvas.getHeight() / 2.0, id));
        knots.add(new DPoint(knotCanvas.getWidth() - 55.0, knotCanvas.getHeight() / 2, id));
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
            lineGc.strokeLine(currentPoint.getPosX()+10, currentPoint.getPosY()+10, endPoint.getPosX()+10, endPoint.getPosY()+10);

            //arow part
            double ex = endPoint.getPosX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

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
                double factor = 20 / Math.hypot(sx-ex, sy-ey);
                double factorO = 7 / Math.hypot(sx-ex, sy-ey);

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

            edges.add(new Edge(currentPoint, endPoint));
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

            if(currentPointIsCircle) {
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
     * Start up
     */

    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        System.out.println("Mouse Clicked");

        if (checkClick(mouseX, mouseY) && !dragging) {
            knotGc.setFill(Color.WHITE);
            knotGc.fillOval(mouseX - 10, mouseY - 10, 20, 20);
            knots.add(new DPoint(mouseX - 10, mouseY - 10, id));
            System.out.println(mouseX + "\t" + mouseY);
            id += 1;
        }

        if (isThereCircle(mouseX, mouseY) != null) {
            for (DPoint dp : knots) {
                if (dp.getPosX() == mouseX && dp.getPosY() == mouseY) {
                    currentPoint = dp;
                }
            }
        }
    }

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


    public void handleButtonStart(ActionEvent event) {
        System.out.println("Person Button pressed");
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

    public static void main(String[] args) {
        launch(args);
    }
}