package api.visits;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.PatientSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class PatientVisitsTest extends BaseTest {

    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @PrepareData(Prepare.LOCATION)
    @Test
    public void userAbleToAddVisits() {

        PatientIdentifierTypeResponse patientIdentifierType = SessionStorage.get(Prepare.PATIENT_IDENTIFIER_TYPE, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);

        PatientCreateNewRequest patientRequest =
                RandomModelGenerator.generate(PatientCreateNewRequest.class,
                        fields -> {
                            for (IdentifierRequest identifier : fields.getIdentifiers()) {
                                identifier.setIdentifierType(patientIdentifierType.getUuid());
                                identifier.setLocation(location.getUuid());
                                identifier.setPreferred(false);
                            }
                            Person person = fields.getPerson();
                            person.setDead(false);
                            person.setCauseOfDeath(null);
                            person.setDeathDate(null);
                        });
        PatientResponse patient = PatientSteps.createPatient(patientRequest);

        VisitTypeResponse getFirstVisitType =  PatientSteps.getVisitType();

        OffsetDateTime now = OffsetDateTime.now();
        Visit visitRequest = Visit.builder()
                .visitType(getFirstVisitType.getUuid())
                .patient(patient.getUuid())
                .location(location.getUuid())
                .startDatetime(now)
                .stopDatetime(now.plusMinutes(15))
                .build();

        VisitResponse createdVisit = PatientSteps.createVisit(visitRequest);

        System.out.println(createdVisit.toString());

        ModelAssertions.assertThatModels(createdVisit, visitRequest).match();
    }
}
