package controllers;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private JFXProgressBar jfxProgress;
    @FXML
    private BorderPane bp;

    public void initialize(URL url, ResourceBundle rb) {

        Task task = taskWorker(100);
        jfxProgress.progressProperty().unbind();
        jfxProgress.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e->{
            try{
                close();
            }
            catch (IOException exv){
                 exv.getMessage();
            }
        });
        Thread th = new Thread(task);
        th.start();
    }

    public void close() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../screens/Menu.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    private Task taskWorker(int seconds){
        return new Task(){
            @Override
            protected Object call() throws Exception{
                for(int i =0; i<seconds ; i++){
                    updateProgress(i+1, seconds);
                    Thread.sleep(10);
                }
                return null;
            }
        };
    }
}
