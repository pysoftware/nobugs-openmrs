package ui.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import ui.enums.RadioOption;
import ui.generators.RandomGenerator;

public class RadioGroup<T extends Enum<T> & RadioOption> extends PageElement {
    private final Class<T> enumClass;

    public RadioGroup(Locator locator, Class<T> enumClass) {
        super(locator.getByRole(AriaRole.GROUP,
                new Locator.GetByRoleOptions().setName(enumClass.getSimpleName())));
        this.enumClass = enumClass;
    }

    public void select(T option) {
        locator.locator("label[for='" + option.label() + "']").click();
    }

    public void selectRandom() {
        T randomOption = RandomGenerator.randomEnum(enumClass);
        locator.locator("label[for='" + randomOption.label() + "']").click();
    }
}
