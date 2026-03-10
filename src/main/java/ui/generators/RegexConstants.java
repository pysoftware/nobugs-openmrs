package ui.generators;

public final class RegexConstants {
    private RegexConstants() {}

    public static final String CORRECT_NAME = "[A-Za-z]{8}";
    public static final String INCORRECT_NAME = "[A-Za-z]{5}\\d[A-Za-z]{5}";
}
