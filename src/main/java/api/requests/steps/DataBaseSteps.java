package api.requests.steps;

import api.database.dao.*;
import api.database.dbrequest.Condition;
import api.database.dbrequest.DBRequest;
import api.database.dbrequest.InnerJoin;
import lombok.Getter;

public class DataBaseSteps {
    public static final String VOID_REASON="web service call";

    @Getter
    public enum Table {
        PERSON("person"),
        PERSON_NAME("person_name"),
        PERSON_ADDRESS("person_address"),
        PATIENT("patient"),
        PATIENT_IDENTIFIER("patient_identifier"),
        PATIENT_IDENTIFIER_TYPE("patient_identifier_type"),
        LOCATION("location");

        Table(String name) {
            this.name = name;
        }

        private final String name;
    }

    // Count
    public static CountDao countRowsOfTable(Table table) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(table.getName())
                .count(true)
                .extractAs(CountDao.class);
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

    // PatientIdentifier
    public static PatientIdentifierDao getPatientIdentifierByUuid(String uuid) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(Table.PATIENT_IDENTIFIER.getName())
                .where(Condition.equalTo("uuid", uuid))
                .extractAs(PatientIdentifierDao.class);
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

    // Person
    public static PersonDao getPersonByUuid(String uuid) {
        return DBRequest.builder()
                .requestType(DBRequest.RequestType.SELECT)
                .table(Table.PERSON.getName())
                .innerJoin(InnerJoin.condition(Table.PERSON.getName(),"person_id", Table.PERSON_NAME.getName(), "person_id"))
                .innerJoin(InnerJoin.condition(Table.PERSON.getName(),"person_id", Table.PERSON_ADDRESS.getName(), "person_id"))
                .where(Condition.equalTo(Table.PERSON.getName(), "uuid", uuid))
                .extractAs(PersonDao.class);
    }

}