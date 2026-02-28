package ui.elements;

import com.microsoft.playwright.Locator;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class PatientBanner extends BaseElement {

    private final Locator nameLink;              // ссылка с именем пациента → ведёт в chart
    private final Locator avatar;                // аватар (img или контейнер)
    private Locator gender;                // пол пациента (Male / Female / Other)
    private final Locator status;                // статус визита (Active Visit и т.п.)
    private final Locator age;                   // возраст ("30 yrs")
    private final Locator birth;                 // дата рождения ("24-Feb-1996")
    private final Locator patientId;             // "OpenMRS ID: 10001YY"
    private final Locator actionsButton;         // кнопка "Actions"
    private final Locator startVisitButton;      // кнопка "Start visit"
    private final Locator showMoreButton;        // кнопка "Show more"

    public PatientBanner(Locator root) {
        super(root);

        // Основная ссылка на карту пациента
        this.nameLink = root.locator("a[href*='/chart/']");

        // Аватар
        this.avatar = root.locator("[class*='patientAvatar'], [class*='patient-avatar']");

        // Пол (Male / Female / Other) — можно уточнить селектор, если есть иконка/класс
        this.gender = root.getByText(
                Pattern.compile("Male|Female|Other|Unknown", Pattern.CASE_INSENSITIVE)
        ).first();

        // Статус визита — ищем текст, содержащий "Visit" или "Active"
        this.status = root.getByText(
                Pattern.compile("Active Visit|Inactive|No Visit", Pattern.CASE_INSENSITIVE)
        ).first();

        // Возраст — строка, содержащая "yrs"
        this.age = root.getByText(
                Pattern.compile("yrs", Pattern.CASE_INSENSITIVE)
        ).first();

        // Дата рождения — обычно идёт после возраста через дефис
        // Если структура стабильна, можно уточнить: text после "yrs - "
        this.birth = root.locator("text/yrs/").locator("xpath=following-sibling::text()[contains(., '-')]").first();

        // OpenMRS ID
        this.patientId = root.locator("text/OpenMRS ID:");

        // Кнопка Actions
        this.actionsButton = root.locator("button:has-text('Actions')");

        // Кнопка Start visit
        this.startVisitButton = root.locator("button:has-text('Start visit')");

        // Кнопка Show more
        this.showMoreButton = root.locator("button:has-text('Show more'), a:has-text('Show more')");
    }

    public void clickPatientName() {
        nameLink.click();
    }

    public void startVisit() {
        startVisitButton.click();
    }

    public void openActions() {
        actionsButton.click();
    }

    public void clickShowMore() {
        showMoreButton.click();
    }
}