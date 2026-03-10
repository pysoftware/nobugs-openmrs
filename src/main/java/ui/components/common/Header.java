package ui.components.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ui.components.BaseForm;
import ui.elements.Button;
import ui.elements.TextInput;
import ui.pages.SearchPage;

public class Header extends BaseForm {
    public final Button searchPatientButton;
    public final TextInput searchInput;
    public final Button searchButton;

    public final Button implementerToolsButton;
    public final Button addPatientButton;
    public final Button userMenuButton;
    public final Button appMenuButton;

    public Header(Locator locator) {
        super(locator);

        searchPatientButton = new Button(locator, "Search patient");
        searchInput = new TextInput(locator.getByTestId("patientSearchBar"));
        searchButton = new Button(locator, "Search");

        implementerToolsButton = new Button(locator, "Implementer Tools");
        addPatientButton = new Button(locator, "Add patient");
        userMenuButton = new Button(locator, "My Account");
        appMenuButton = new Button(locator, "App Menu");
    }

    public SearchPage searchPatientByNameOrId(String search) {
        Page page = locator.page();

        searchPatientButton.click();
        searchInput.fill(search);
        page.waitForTimeout(500);
        searchButton.click();

        return new SearchPage(page);
    }
}
