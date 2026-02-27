package ui;

import annotations.AdminSession;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;
import ui.pages.PatientRegistrationPage;

public class PatientSearchTest extends BaseUiTest{
    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanSearchPatientById(){
        var patient = SessionStorage.get(Prepare.PATIENT,1);
        String id = patient.getUuid();
        new PatientRegistrationPage(page).searchPatientById(id);


    }


}
