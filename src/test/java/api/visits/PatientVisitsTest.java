package api.visits;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.VisitSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class PatientVisitsTest extends BaseTest {

    @PrepareData(Prepare.PATIENT)
    @PrepareData(Prepare.LOCATION)
    @Test
    public void userAbleToAddVisits() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);

        VisitTypeResponse getFirstVisitType =  VisitSteps.getVisitType();

        OffsetDateTime now = OffsetDateTime.now();
        Visit visitRequest = Visit.builder()
                .visitType(getFirstVisitType.getUuid())
                .patient(patient.getUuid())
                .location(location.getUuid())
                .startDatetime(now)
                .stopDatetime(now.plusMinutes(15))
                .build();

        VisitResponse createdVisit = VisitSteps.createVisit(visitRequest);

        ModelAssertions.assertThatModels(createdVisit, visitRequest).match();
    }

    @Test
    public void userAbleToToCreateVisitTypeTest() {

        VisitTypeCreateRequest newVisitType = RandomModelGenerator.generate(VisitTypeCreateRequest.class);

        VisitTypeResponse createdVisitType = VisitSteps.createVisitType(newVisitType);

        ModelAssertions.assertThatModels(newVisitType, createdVisitType).match();
    }

    @Test
    public void useAbleToUpdateVisitTypeTest() {

        VisitTypeResponse visitType = VisitSteps.getVisitType();

        VisitTypeCreateRequest newVisitType = RandomModelGenerator.generate(VisitTypeCreateRequest.class);

        VisitTypeResponse updatedVisitType = VisitSteps.updateVisitType(newVisitType, visitType.getUuid());

        ModelAssertions.assertThatModels(newVisitType, updatedVisitType).match();
    }
}
