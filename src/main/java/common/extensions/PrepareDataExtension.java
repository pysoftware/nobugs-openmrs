package common.extensions;

import api.models.PersonResponse;
import common.annotations.PrepareData;
import common.storage.SessionStorage;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.List;

import static api.requests.steps.AdminSteps.createPerson;

public class PrepareDataExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        PrepareData annotation = context.getRequiredTestMethod()
                .getAnnotation(PrepareData.class);

        if (annotation == null) {
            return;
        }

        String entityType = annotation.value();
        int count = annotation.count();

        String storageKey = annotation.storageKey().isEmpty()
                ? "prepared_" + entityType + "s"
                : annotation.storageKey();

        List<Object> created = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Object entity;

            switch (entityType.toLowerCase()) {
                case "person":
                    entity = createPerson();
                    break;

/*                case "patient":
                    entity = createPatient();          // предполагается, что метод существует
                    break;

                case "visit":
                    entity = createVisit();            // предполагается, что метод существует
                    break;*/

                default:
                    throw new UnsupportedOperationException(
                            "Не поддерживается тип сущности: " + entityType);
            }

            created.add(entity);
        }

        SessionStorage.put(storageKey, created);

        if (!created.isEmpty()) {
            SessionStorage.put("main_" + entityType, created.get(0));
        }
    }
}
