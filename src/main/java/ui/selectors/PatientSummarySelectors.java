package ui.selectors;

public class PatientSummarySelectors {
    // Patient banner
    public static final String PATIENT_BANNER = "header[aria-label='patient banner']";
    public static final String NAME = "xpath=ancestor::div[1]/preceding-sibling::span";
    public static final String IDENTIFIER_REQUIRED = ".cds--tag .cds--tag__label span:nth-child(2)";
    public static final String IDENTIFIER_NOT_REQUIRED = "label[for^='patient-banner-identifier-'] span:nth-child(2)";

    // Action menu
    public static final String ACTION_MENU = "div[role='menu']";
    public static final String ADD_TO_LIST = "[data-extension-id='add-patient-to-patient-list-button']";
    public static final String EDIT_PATIENT_DETAILS = "[data-extension-id='edit-patient-details-button']";
    public static final String ADD_VISIT = "[data-extension-id='start-visit-button']";
    public static final String MARK_PATIENT_DECEASED = "[data-extension-id='mark-patient-deceased-button']";
}
