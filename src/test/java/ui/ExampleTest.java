package ui;

import org.junit.jupiter.api.Test;
import ui.pages.LoginPage;


public class ExampleTest extends BaseUiTest {
    @Test
    void loginAndOpenLocation() {
        new LoginPage(page).loginAsAdmin();
    }

}
