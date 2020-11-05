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

    private Canvas canvas;
    private GraphicsContext gc;
    private final ArrayList<DPoint> knots = new ArrayList<>();
    private int id;

    public inputController(){
    }

    /**
     *
     * @param primaryStage This is the stage, the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Settings");

        GridPane pane = new GridPane();
        canvas = new Canvas(750, 500);
        Button startButton = new Button("start");
        Button clearButton = new Button("clear");
        gc = canvas.getGraphicsContext2D();

        startButton.setOnAction(this::handleButtonStart);
        clearButton.setOnAction(this::handleButtonClear);

        pane.add(canvas, 0, 0, 3, 4);
        pane.add(startButton, 3, 0);
        pane.add(clearButton, 3, 1);

        Scene scene = new Scene(pane, 500, 300);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED,
                this::mouseClickAction);
        canvas.setOnMouseClicked(this::mouseClickAction);

        primaryStage.setScene(scene);
        primaryStage.show();

        startUp();
    }

    /**
     *
     */
    private void startUp() {
        gc.setFill(Color.PAPAYAWHIP);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.DARKGREEN);
        gc.fillOval(30, canvas.getHeight() / 2, 25, 25);
        gc.fillOval(canvas.getWidth() - 55, canvas.getHeight() / 2, 25, 25);

        id = 0;

        knots.add(new DPoint(30.0, canvas.getHeight() / 2.0, id));
        knots.add(new DPoint(canvas.getWidth() - 55.0, canvas.getHeight() / 2, id));
    }

    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        anotherCircle(mouseX, mouseY);

        if (checkClick(mouseX, mouseY)){
                gc.strokeOval(mouseX - 10, mouseY - 10, 20, 20);
                knots.add(new DPoint(mouseX - 10, mouseY - 10, id));
                System.out.println(mouseX + "\t" + mouseY);
            id += 1;
        }


    }

    private void anotherCircle(double mouseX, double mouseY){
        for (DPoint dp : knots) {
            double width = dp.getRadius() * 2;

            if (mouseY < (dp.getPosY() + width) && mouseY > (dp.getPosY() - width)) {
                System.out.println("y is same");
            }
            if (mouseX < (dp.getPosX() + width) && mouseX > (dp.getPosX() - width)) {
                System.out.println("x is same");
            }
        }
    }

    private boolean checkClick(double mouseX, double mouseY) {


        //x-border
        if (mouseX < 75 || mouseX > canvas.getWidth() - 75) {
            return false;
        }

        //x-Border
        if (mouseY < 30 || mouseY > canvas.getHeight() - 30) {
            return false;
        }

        for (DPoint dp : knots) {
            double width = dp.getRadius() * 2;

            //System.out.println(dp.getId() + "\t<" + (dp.getPosX() + dp.getRadius()) + "\t>" + (dp.getPosX() - dp.getRadius()));
            //System.out.println("x\t" + mouseX);
            //System.out.println(dp.getId() + "\t>" + (dp.getPosY() + dp.getRadius()) + "\t<" + (dp.getPosY() - dp.getRadius()));
            //System.out.println("y\t" + mouseY);
            if (mouseY < (dp.getPosY() + width) && mouseY > (dp.getPosY() - width / 2))
                if (mouseX < (dp.getPosX() + width) && mouseX > (dp.getPosX() - width / 2)) {
                    return false;
                }
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