package api.requests.skelethon;

import api.models.*;
import common.extensions.Prepare;
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

    PERSON_ADDRES(
            "/person",
            PersonAddressCreateRequest.class,
            PersonAddressResponse.class
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

    public static Endpoint findByResponseModel(Prepare type) {
        if (type == null) return null;
        for (Endpoint e : values()) {
            if (type.getModel().equals(e.responseModel)) {
                return e;
            }
        }
        return null;
    }
}
