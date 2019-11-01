package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    JFXComboBox<String> comboBox;
    @FXML
    private BorderPane bp;
    @FXML
    private JFXDrawer drwMenu;

    public void initialize(URL url, ResourceBundle rb){
        try {
            VBox box = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
            drwMenu.setSidePane(box);
            for (Node node : box.getChildren()){
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                    switch (node.getId())
                    {
                        case "btnBack" :
                            drawCloseButton();
                            break;
                        case "btnLogin" :
                            Stage s = new Stage();
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                                s.setScene(new Scene(root));
                                s.initStyle(StageStyle.TRANSPARENT);
                                s.show();
                                Stage stage  = (Stage) bp.getScene().getWindow();
                                stage.close();
                            }
                            catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBox.getItems().add("BR");
        comboBox.getItems().add("LAS");
        comboBox.getItems().add("KR");
        comboBox.getItems().add("EU");
        comboBox.getItems().add("NA");
        comboBox.setStyle("-fx-font: 12px \"Josefin Sans Regular\"; -fx-text-fill: WHITE; -fx-prompt-text-fill: WHITE;");
    }

    @FXML
    private void keyPressed(KeyEvent k) throws IOException{
        if(k.getCode() == KeyCode.ENTER) {
             Stage s = new Stage();
             Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
             s.setScene(new Scene(root));
             s.initStyle(StageStyle.TRANSPARENT);
             s.show();

             Stage stage  = (Stage) bp.getScene().getWindow();
             stage.close();
         }
    }

    @FXML
    private void handleButtonAction(){
        System.exit(0);
    }

    @FXML
    private void minimizeButtonAction(){
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void drawButton() {
        drwMenu.open();
        drwMenu.setPrefWidth(200.00);
        drwMenu.setVisible(true);
        drwMenu.setDisable(false);
    }
    @FXML
    private void drawCloseButton() {
        drwMenu.close();
        drwMenu.setDisable(true);
    }
}
