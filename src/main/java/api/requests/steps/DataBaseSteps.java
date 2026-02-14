package api.requests.steps;

import api.database.dao.PatientDao;
import api.database.dbrequest.Condition;
import api.database.dbrequest.DBRequest;
import api.database.dbrequest.InnerJoin;
import lombok.Getter;

public class DataBaseSteps {
    @Getter
    public enum Table {
        PERSON("person"),
        PATIENT("patient");

        Table(String name) {
            this.name = name;
        }

        private String name;
    }

    // Patient
    public static PatientDao getPatientByUuid(String uuid) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(Table.PATIENT.getName())
                .innerJoin(InnerJoin.condition(Table.PERSON.getName(), "person_id", Table.PATIENT.getName(), "patient_id"))
                .where(Condition.equalTo(Table.PERSON.getName(), "uuid", uuid))
                .extractAs(PatientDao.class);
    }

}
