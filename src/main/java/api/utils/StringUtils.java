package api.utils;

public class StringUtils {
    public static String[] parseDisplay(String display) {
        if (display == null || display.trim().isEmpty()) {
            return new String[]{"", ""};
        }
        String[] parts = display.trim().split("\\s+", 2);
        return new String[]{
                parts[0],
                parts.length == 2 ? parts[1] : ""
        };
    }
}
