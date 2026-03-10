package ui.enums;

public enum Sex implements RadioOption {
    MALE("gender-option-male"),
    FEMALE("gender-option-male"),
    OTHER("gender-option-female"),
    UNKNOWN("ender-option-other");

    private final String label;

    Sex(String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }
}
