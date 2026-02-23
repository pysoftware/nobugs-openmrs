package api.requests.steps;

public enum ErrorMessages {
    PERSON_NAME_IS_NULL("[names on class org.openmrs.Person => Cannot invoke \"java.util.List.iterator()\" because \"personNames\" is null]"),
    IDENTIFIER_TYPE_IS_NULL("Cannot invoke \"org.openmrs.PatientIdentifierType.getUuid()\" because \"identifierType\" is null"),
    OBJECT_DOES_NOT_EXIST("Object with given uuid doesn't exist [null]"),
    FAILED_TO_VALIDATE("['%s' failed to validate with reason: Identifier \"%s\" does not match : \"Three digits, five uppercase letters, and one digit\"]"),
    PERSON_ADDRESS_IS_NULL("[addresses on class org.openmrs.Person => Cannot invoke \"java.util.List.iterator()\" because \"addresses\" is null]");
    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String toString(String... args) {
        return String.format(message, args);
    }
}
