package utils;

public class LogicUtils {
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static String usefulNickname(String name) {
        return name != null ? name.replace(" ", "") : null;
    }
}
