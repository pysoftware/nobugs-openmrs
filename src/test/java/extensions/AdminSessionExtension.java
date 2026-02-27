package extensions;

import annotations.AdminSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.BaseUiTest;

public class AdminSessionExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AdminSession annotation = context.getRequiredTestMethod()
                .getAnnotation(AdminSession.class);

        if (annotation != null) {
            // Получаем экземпляр тестового класса
            Object testInstance = context.getRequiredTestInstance();

            // Проверяем, что тест наследует BaseUiTest
            if (testInstance instanceof BaseUiTest baseTest) {
                baseTest.authAsAdmin();  // ← вызываем нестатический метод
            } else {
                throw new IllegalStateException(
                        "@AdminSession можно использовать только в классах, наследующих BaseUiTest"
                );
            }
        }
    }
}
