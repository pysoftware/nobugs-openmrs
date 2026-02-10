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
            LoginResponse.class
    ),

    PERSON(
            "/person",
            PersonCreateRequest.class,
            PersonResponse.class
    ),

    PATIENT(
            "/patient",
            PatientCreateNewRequest.class,
            PatientResponse.class
    ),

    PATIENT_IDENTIFIER_TYPE(
            "/patientidentifiertype",
            PatientIdentifierTypeCreateRequest.class,
            PatientIdentifierTypeResponse.class
    ),

    LOCATION(
            "/location",
            LocationCreateRequest.class,
            LocationResponse.class
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
