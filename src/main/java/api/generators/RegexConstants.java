package api.generators;

public final class RegexConstants {
    private RegexConstants() {}

    public static final String UUID_V4 =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String PATIENT_IDENTIFIER_TYPE_FORMAT = "^\\d{3}[A-Z]{5}\\d$";
    public static final String PATIENT_IDENTIFIER_TYPE_DESCRIPTION = "^Three digits, five uppercase letters, and one digit.$";
}
