package api.requests.skelethon;

import api.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {

    LOGIN(
            "/session",
            LoginRequest.class,
            LoginResponse.class,
            null
    ),

    PERSON(
            "/person",
            PersonCreateRequest.class,
            PersonResponse.class,
            "person"
    ),

    PATIENT(
            "/patient",
            PatientCreateNewRequest.class,
            PatientResponse.class,
            "patient"
    ),

    PATIENT_IDENTIFIER_TYPE(
            "/patientidentifiertype",
            PatientIdentifierTypeCreateRequest.class,
            PatientIdentifierTypeResponse.class,
            null
    ),

    LOCATION(
            "/location",
            LocationCreateRequest.class,
            LocationResponse.class,
            null
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
    private final String entityType;   // null для служебных эндпоинтов (LOGIN и т.п.)

    public static Endpoint findByEntityType(String type) {
        if (type == null) return null;
        String normalized = type.toLowerCase().trim();

        for (Endpoint e : values()) {
            if (normalized.equals(e.entityType)) {
                return e;
            }
        }
        return null;
    }
}
