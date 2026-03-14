package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ui.Helpers;
import ui.components.search.PatientBanner;
import ui.selectors.SearchSelectors;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends BasePage<SearchPage> {
    public final PatientBanner patientBanner;
    public final Locator resultsHeader;

    public SearchPage(Page page) {
        super(page);

        Locator banner = page.locator(SearchSelectors.PATIENT_BANNER); // отдельная карта пациента
        patientBanner = new PatientBanner(banner);
        resultsHeader = page.locator(SearchSelectors.RESULT_HEADER);

        resultsHeader.waitFor();
    }

    @Override
    protected String path() {
        return "search";
    }

    public List<PatientBanner> getBanners() {
        List<Locator> items = patientBanner.all();
        return items.stream()
                .map(PatientBanner::new)
                .collect(Collectors.toList());
    }

    public PatientSummaryPage clickBanner(int index) {
        PatientBanner banner = getBanners().get(index);
        String patientUuid = Helpers.getPatientUuid(banner.locator);
        banner.click();

        return new PatientSummaryPage(page, patientUuid).waitPage();
    }

    public PatientSummaryPage clickBanner(String name) {
        Locator banner = page.locator("div[role='banner']")
                .filter(new Locator.FilterOptions().setHasText(name));
        String patientUuid = Helpers.getPatientUuid(banner);
        banner.click();

        return new PatientSummaryPage(page, patientUuid).waitPage();
    }

    public int countSearchResults() {
        return ((Number) resultsHeader.evaluate(SearchSelectors.SEARCH_RESULT_TO_INT)).intValue();
    }

    public void openActions() {
        patientBanner.actionsButton.click();
    }
}
