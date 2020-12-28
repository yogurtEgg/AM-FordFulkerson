import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 50, 50, 50));
        pane.setSpacing(10);

        outputLabel = new Label();
        outputLabel.setWrapText(true);
        outputLabel.setText("");
        pane.getChildren().add(outputLabel);

        Button closeButton = new Button("close");
        closeButton.setOnAction(this::handleButtonClose);
        pane.getChildren().add(closeButton);

        Scene scene = new Scene(pane, 400, 500);

        stage.setScene(scene);
        stage.show();
    }

    public void outputSolution(String output){
        outputLabel.setText(output);
    }
}
