import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VisualisationController{
    private Label outputLabel;

    public VisualisationController() throws Exception {
        start();
    }

    public void handleButtonClose (ActionEvent event){

        //Close Window
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.println("Result Visualisation closed");
    }


    public void start() throws Exception {
        //primaryStage.setTitle("Settings");
        //TODO Connect FXML to rest of code
Stage stage = new Stage();
        GridPane pane = new GridPane();

        Button closeButton = new Button("close");
        closeButton.setOnAction(this::handleButtonClose);
        pane.add(closeButton, 2, 2);

        outputLabel = new Label();
        outputLabel.setWrapText(true);
        pane.add(outputLabel, 1,1);

        Scene scene = new Scene(pane, 400, 500);

        stage.setScene(scene);
        stage.show();
    }

    public void outputSolution(String output){
        outputLabel.setText(output);
    }
}
