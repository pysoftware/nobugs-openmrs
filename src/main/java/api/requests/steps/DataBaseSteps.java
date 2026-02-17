package api.requests.steps;

import api.database.dao.LocationDao;
import api.database.dao.PatientDao;
import api.database.dao.PatientIdentifierTypeDao;
import api.database.dbrequest.Condition;
import api.database.dbrequest.DBRequest;
import api.database.dbrequest.InnerJoin;
import lombok.Getter;

public class DataBaseSteps {
    @Getter
    public enum Table {
        PERSON("person"),
        PATIENT("patient"),
        PATIENT_IDENTIFIER_TYPE("patient_identifier_type"),
        LOCATION("location");

        Table(String name) {
            this.name = name;
        }

        private final String name;
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

    // PatientIdentifierType
    public static PatientIdentifierTypeDao getPatientIdentifierTypeByUuid(String uuid) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(Table.PATIENT_IDENTIFIER_TYPE.getName())
                .where(Condition.equalTo("uuid", uuid))
                .extractAs(PatientIdentifierTypeDao.class);
    }

    // Location
    public static LocationDao getLocationByUuid(String uuid) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(Table.LOCATION.getName())
                .where(Condition.equalTo("uuid", uuid))
                .extractAs(LocationDao.class);
    }
}