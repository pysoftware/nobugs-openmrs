package common.extensions;

import api.models.interfaces.HasUuid;
import api.requests.steps.LocationSteps;
import api.requests.steps.PatientIdentifierTypeSteps;
import api.requests.steps.PatientSteps;
import api.requests.steps.PersonSteps;
import common.annotations.PrepareData;
import common.storage.SessionStorage;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Map;
import java.util.function.Supplier;

public class PrepareDataExtension implements BeforeEachCallback {
    private static final Map<Prepare, Supplier<HasUuid>> FACTORIES = Map.of(
            Prepare.PERSON, PersonSteps::createPerson,
            Prepare.PATIENT_IDENTIFIER_TYPE, PatientIdentifierTypeSteps::createPatientIdentifierType,
            Prepare.LOCATION, LocationSteps::createLocation,
            Prepare.PATIENT, PatientSteps::createPatient
    );


    @Override
    public void beforeEach(ExtensionContext context) {
        PrepareData[] annotations = context.getRequiredTestMethod()
                .getAnnotationsByType(PrepareData.class);

        for (PrepareData annotation : annotations) {
            Prepare type = annotation.value();
            int count = annotation.count();

            Supplier<HasUuid> factory = FACTORIES.get(type);
            if (factory == null) {
                throw new UnsupportedOperationException("Не поддерживается тип сущности: " + type);
            }

            for (int i = 0; i < count; i++) {
                SessionStorage.add(factory.get());
            }
        }
    }
}
