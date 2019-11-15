package utils;

import java.awt.*;

public enum ColorsEnum {
    RED("#bf616a");

    public String getHexColor() {
        return hexColor;
    }

    private final String hexColor;

    ColorsEnum(String hexColor) {
        this.hexColor = hexColor;
    }


}
