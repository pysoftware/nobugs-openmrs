package api.utils;

import groovy.lang.GString;

public class StringUtils {
    public static String[] parseDisplay(String display) {
        return parseDisplay(display, "\\s+");
    }

    public static String[] parseDisplay(String display, String splitRegex) {
        if (display == null || display.trim().isEmpty()) {
            return new String[]{"", ""};
        }
        String[] parts = display.trim().split(splitRegex, 2);
        return new String[]{
                parts[0].trim(),
                parts.length == 2 ? parts[1].trim() : ""
        };
    }
}
