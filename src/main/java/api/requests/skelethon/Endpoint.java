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
            "/person/{uuid}/address",
            PersonAddressCreateRequest.class,
            PersonAddressResponse.class
    ),

    PATIENT(
            "/patient",
            PatientCreateRequest.class,
            PatientResponse.class
    ),

    PATIENT_IDENTIFIER(
            "/patient/{uuid}/identifier",
            PatientIdentifierCreateRequest.class,
            PatientIdentifierResponse.class
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
    ),
    VISITTYPE(
            "/visittype?limit=1&startIndex=2&v=default",
            BaseModel.class,
            VisitTypeResults.class
    ),

    CREATEVISIT(
            "/visit",
            Visit.class,
            VisitResponse.class
    ),
    CREATETAG(
            "/locationtag",
            CreateTagRequest.class,
            CreateTagResponse.class
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
