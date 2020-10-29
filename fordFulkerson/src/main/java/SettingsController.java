import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {
    @FXML
    Canvas canvas;
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public SettingsController(){
    }

    public void mouseClickAction(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

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
}
