package utils;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import data.enums.ImagesUrl;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GraphicUtils {

    public static List<Rectangle> createRectangleItemsRow(ArrayList<Integer> images, int iterate, int row, int size) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int j = 1; j <= iterate; j++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setId("item" + row + "" + j);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setFill(Paint.valueOf("#9a9a9a"));
            rectangle.setHeight(size);
            rectangle.setWidth(size);
            rectangle.setStroke(Paint.valueOf("TRANSPARENT"));
            if(images.size() >= j) {
                Image itemImage = new Image(ImagesUrl.ITEMS.getUrl() + images.get(j - 1) + ".png");
                rectangle.setFill(new ImagePattern(itemImage));
            }
            rectangles.add(rectangle);
        }

        return rectangles;
    }

    public static List<Circle> createCircles(ArrayList<Integer> images, int iterate) {
        List<Circle> circles = new ArrayList<>();
        for (int j = 1; j <= iterate; j++) {
            Circle circle = new Circle();
            circle.setId("spell" + j);
            circle.setRadius(15);
            String ss = "";
            switch (images.get(j-1)) {
                case 21 :
                    ss = "SummonerBarrier";
                    break;
                case 1 :
                    ss = "SummonerBoost";
                    break;
                case 14 :
                    ss = "SummonerDot";
                    break;
                case 3 :
                    ss = "SummonerExhaust";
                    break;
                case 4 :
                    ss = "SummonerFlash";
                    break;
                case 6 :
                    ss = "SummonerHaste";
                    break;
                case 7 :
                    ss = "SummonerHeal";
                    break;
                case 11 :
                    ss = "SummonerSmite";
                    break;
                case 12 :
                    ss = "SummonerTeleport";
                    break;
            }
            if(images.size() >= j) {
                Image itemImage = new Image(ImagesUrl.SPELLS.getUrl() + ss + ".png");
                circle.setFill(new ImagePattern(itemImage));
            }
            circles.add(circle);
        }

        return circles;
    }

    public static void callSnackbar(String message, Pane pane, String color) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(
                new JFXSnackbarLayout(message),
                Duration.millis(3000),
                new SnackColor(color))
        );
    }
}
