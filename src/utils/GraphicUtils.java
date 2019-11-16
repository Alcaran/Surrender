package utils;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import data.enums.ImagesUrl;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class GraphicUtils {

    public static List<Rectangle> createRectangleItemsRow(ArrayList<Integer> images, int iterate, int row) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int j = 1; j <= iterate; j++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setId("item" + row + "" + j);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setFill(Paint.valueOf("#9a9a9a"));
            rectangle.setHeight(50);
            rectangle.setWidth(50);
            rectangle.setStroke(Paint.valueOf("TRANSPARENT"));
            if(images.size() >= j) {
                Image itemImage = new Image(ImagesUrl.ITEMS.getUrl() + images.get(j - 1) + ".png");
                rectangle.setFill(new ImagePattern(itemImage));
            }
            rectangles.add(rectangle);
        }

        return rectangles;
    }

    public static void callSnackbar(String message, Pane pane) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.setStyle("-fx-background-color: #BF616A");
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(
                new JFXSnackbarLayout(message),
                Duration.millis(3000),
                null)
        );

    }
}
