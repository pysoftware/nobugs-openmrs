package common.extensions;

import api.models.*;
import api.models.interfaces.HasUuid;
import lombok.Getter;

@Getter
public enum Prepare {
    PERSON(PersonResponse.class),
    PATIENT_IDENTIFIER_TYPE(PatientIdentifierTypeResponse.class),
    LOCATION(LocationResponse.class),
    PATIENT(PatientResponse.class);

    private final Class<? extends HasUuid> model;

    Prepare(Class<? extends HasUuid> model) {
        this.model = model;
    }

    public static Prepare fromModelClass(Class<? extends HasUuid> clazz) {
        for (Prepare p : values()) {
            if (p.model.equals(clazz)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No Prepare for class: " + clazz);
    }
}
