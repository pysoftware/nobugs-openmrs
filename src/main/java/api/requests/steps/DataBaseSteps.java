package api.requests.steps;

import api.database.dao.PatientNameDao;
import api.database.dao.PersonUuidDao;
import api.database.dbrequest.Condition;
import api.database.dbrequest.DBRequest;

import java.util.List;

public class DataBaseSteps {

    public static List<PatientNameDao> getPatientsByName(String givenName, String familyName) {
        return DBRequest.builder()
                 .table("person_name pn")
                 .where(Condition.equalTo("pn.given_name", givenName))
                 .where(Condition.equalTo("pn.family_name", familyName))
                 .executePatientName();
    }

    public static PersonUuidDao getPersonByUuid(String uuid) {
         return DBRequest.builder()
                 .requestType(DBRequest.RequestType.SELECT)
                 .table("person p")
                 .where(Condition.equalTo("p.uuid", uuid))
                 .executePersonUuid();
    }

}
