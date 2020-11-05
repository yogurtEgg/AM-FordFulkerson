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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class inputController extends Application {

    private GridPane pane;
    private Canvas canvas;
    private Button startButton;
    private Button clearButton;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Settings");

        pane = new GridPane();
        canvas = new Canvas(300, 250);
        startButton = new Button("start");
        clearButton = new Button("clear");
        gc = canvas.getGraphicsContext2D();

        pane.add(canvas, 0, 0, 3, 6);
        pane.add(startButton, 3, 0);
        pane.add(clearButton, 4, 0);

        Scene scene = new Scene(pane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        startUp();
    }

    private void startUp() {
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        gc.strokeOval(mouseX, mouseY, 5, 5);
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
        prepareClearCanvas();
    }

    private void prepareClearCanvas() {
        gc.clearRect(0,0,canvas.getHeight(),canvas.getWidth());
    }

    public static void main(String[] args) {
        launch(args);
    }
}