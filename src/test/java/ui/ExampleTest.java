package ui;

import annotations.AdminSession;
import org.junit.jupiter.api.Test;
import ui.pages.LocationPage;

public class ExampleTest extends BaseUiTest {
    @AdminSession
    @Test
    void loginAndOpenLocation() {
        LocationPage locationPage = new LocationPage(page);
        locationPage.selectFirstLocationAndContinue();
    }

}
