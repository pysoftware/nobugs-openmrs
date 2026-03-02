package ui;

import annotations.AdminSession;
import api.models.PatientResponse;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;
import ui.pages.PatientSummaryPage;

public class ActionsPatientTest extends BaseUiTest {

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanAddVisitToPatient() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String uuid = patient.getUuid();
        PatientSummaryPage patientSummaryPage = new PatientSummaryPage(page, uuid);
        patientSummaryPage.open()
                .openActions().getActionsMenu().clickStartVisit();
    }
}
