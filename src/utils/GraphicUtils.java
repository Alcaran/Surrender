package utils;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GraphicUtils {

    public static List<Rectangle> createRectangleItemsRow(int iterate, int row) {
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
            rectangles.add(rectangle);
        }

        return rectangles;
    }
}
