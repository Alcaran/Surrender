package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Font.loadFont(Main.class.getResource("JosefinSans-Regular.ttf").toExternalForm(), 10);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene s = new Scene(root);
        s.getStylesheets().add(getClass().getResource("font.css").toExternalForm());
        primaryStage.setScene(s);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
