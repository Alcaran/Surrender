package sample;

import data.api.ApiHelper;
import data.api.Enums.QueueType;
import data.api.Enums.Tiers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    Label playerLevel;

    @FXML
    Circle imageCircle;

    @FXML
    Label summonerName;

    @FXML
    ImageView tier;

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private BorderPane bp;

    @FXML
    private void analysesButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Analyses.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);

        s.show();
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle rb) {
        ApiHelper apiHelper = new ApiHelper();
        try {
            JSONObject summonerInfo = apiHelper.getSumonerInfo("Alcarann");
            JSONObject summonerLeagueInfo = apiHelper.getSumonerLeagueInfo(summonerInfo.getString("id"));

            JSONObject SoloDuoObject = JSONUtils.findIObjectInArrayByKeyStringValue(
                    "queueType",
                    QueueType.SoloDuo.getType(),
                    summonerLeagueInfo
                            .getJSONArray("array")
                    );


            playerLevel.setText(String.valueOf(summonerInfo.getInt("summonerLevel")));
            summonerName.setText(summonerInfo.getString("name"));
                String profileIconId = String.valueOf(summonerInfo.getInt("profileIconId"));
;            Image image = new Image(
                    "http://ddragon.leagueoflegends.com/cdn/9.21.1/img/profileicon/"
                            + profileIconId + ".png", false);

            imageCircle.setFill(new ImagePattern(image));
            File file = new File(Tiers.valueOf(SoloDuoObject.getString("tier")).getImageEloPath());
//            Image  asd = new Image("/src/Assets/ranked-emblems/Emblem_Platinum.png");
            tier.setImage(new Image(file.toURI().toString()));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonAction() throws IOException{

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
