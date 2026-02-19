package api.requests.steps;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    PERSON_NAME_IS_NULL("[names on class org.openmrs.Person => Cannot invoke \"java.util.List.iterator()\" because \"personNames\" is null]"),
    IDENTIFIER_TYPE_IS_NULL("Cannot invoke \"org.openmrs.PatientIdentifierType.getUuid()\" because \"identifierType\" is null"),
    OBJECT_DOES_NOT_EXIST("Object with given uuid doesn't exist [null]");


    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
