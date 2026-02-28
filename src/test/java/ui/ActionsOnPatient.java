package ui;

import annotations.AdminSession;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import org.junit.jupiter.api.Test;

public class ActionsOnPatient {

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanAddVisitToPatient() {
        
    }
}
