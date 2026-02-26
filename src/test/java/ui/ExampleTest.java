package ui;

import annotations.AdminSession;
import extensions.AdminSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.pages.LocationPage;
import ui.pages.LoginPage;

@ExtendWith(AdminSessionExtension.class)
public class ExampleTest extends BaseUiTest {
    @AdminSession
    @Test
    void loginAndOpenLocation() {
        LocationPage locationPage = new LocationPage(page);
        locationPage.selectFirstLocationAndContinue();
    }

}
