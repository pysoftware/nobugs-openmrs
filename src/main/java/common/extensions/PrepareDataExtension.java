package common.extensions;

import common.annotations.PrepareData;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static api.requests.steps.AdminSteps.createPerson;

public class PrepareDataExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        PrepareData annotation = context.getRequiredTestMethod()
                .getAnnotation(PrepareData.class);

        if (annotation == null) {
            return;
        }

        String entityType = annotation.value().toLowerCase();
        int count = annotation.count();

        for (int i = 0; i < count; i++) {
            Object entity;
            switch (entityType) {
                case "person":
                    entity = createPerson();
                    break;
                // case "patient":
                //     entity = createPatient();
                //     break;
                // case "visit":
                //     entity = createVisit();
                //     break;
                default:
                    throw new UnsupportedOperationException("Не поддерживается тип сущности: " + entityType);
            }

        }
    }
}
