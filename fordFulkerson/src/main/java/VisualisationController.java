import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class VisualisationController {


    public void handleButtonClose (ActionEvent event){

        //Close Window
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.println("Result Visualisation closed");
    }
}
