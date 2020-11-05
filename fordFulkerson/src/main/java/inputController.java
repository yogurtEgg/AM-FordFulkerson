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

    private GridPane pane;
    private Canvas canvas;
    private Button startButton;
    private Button clearButton;
    private GraphicsContext gc;
    private ArrayList<DPoint> knots;
    private int id;

    public inputController(){
    }

    /**
     *
     * @param primaryStage This is the stage, the primary stage
     * @throws Exception throws any exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Settings");

        pane = new GridPane();
        canvas = new Canvas(750, 500);
        startButton = new Button("start");
        clearButton = new Button("clear");
        gc = canvas.getGraphicsContext2D();

        startButton.setOnAction(event -> handleButtonStart(event));
        clearButton.setOnAction(event -> handleButtonClear(event));

        pane.add(canvas, 0, 0, 3, 4);
        pane.add(startButton, 3, 0);
        pane.add(clearButton, 3, 1);

        Scene scene = new Scene(pane, 500, 300);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> mouseClickAction(e) );
        canvas.setOnMouseClicked(e -> mouseClickAction(e));

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

        knots = new ArrayList<DPoint>();
        knots.add(new DPoint(30.0, canvas.getHeight() / 2.0, id));
        knots.add(new DPoint(canvas.getWidth() - 55.0, canvas.getHeight() / 2, id));
    }

    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        if (checkClick(mouseX, mouseY)){
                gc.strokeOval(mouseX - 10, mouseY - 10, 20, 20);
                knots.add(new DPoint(mouseX - 10, mouseY - 10, id));
                System.out.println(mouseX + "\t" + mouseY);
            id += 1;
        }


    }

    private boolean checkClick(double mouseX, double mouseY) {

        boolean truther = true;

        //x-border
        if (mouseX < 75 || mouseX > canvas.getWidth() - 75) {
            truther = false;
        }

        //x-Border
        if (mouseY < 30 || mouseY > canvas.getHeight() - 30) {
            truther = false;
        }

        for(int a = 0; a < knots.size(); a++) {
            DPoint dp = knots.get(a);

            System.out.println(dp.getId() + "\t<" + (dp.getPosX() + dp.getRadius()) + "\t>" + (dp.getPosX() - dp.getRadius()));
            System.out.println("x\t" + mouseX);
            System.out.println(dp.getId() + "\t>" + (dp.getPosY() + dp.getRadius()) + "\t<" + (dp.getPosY() - dp.getRadius()));
            System.out.println("y\t" + mouseY);
            if(!(mouseX < (dp.getPosX() + dp.getRadius()) && mouseX > (dp.getPosX() - dp.getRadius()))) {
                return true;
            }


            if (!(mouseY > dp.getPosY() + (dp.getRadius()) && mouseY < dp.getPosY() - (dp.getRadius()))){
                return true;
            }
        }

        if (knots.size() > 10) {
            truther = false;
        }

        return false;
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