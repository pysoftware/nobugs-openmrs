package api.patients;

import api.database.dao.PatientNameDao;
import api.database.dao.PersonUuidDao;
import api.steps.DataBaseSteps;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatientDBTests {

    @Test
    void mySecondTest() {
         String firstname = "John";
         String lastname = "Fam";

         List<PatientNameDao> patients = DataBaseSteps.getPatientsByName(firstname, lastname);

         assertFalse(patients.isEmpty(), "Должен найти хотя бы одного пациента");

         patients.forEach(p -> {
             System.out.println("Найден: " + p.getFullName());
             assertEquals(firstname.trim(), p.getGivenName().trim(), "Имя не совпадает");
             assertEquals(lastname.trim(), p.getFamilyName().trim(), "Фамилия не совпадает");
         });
    }

    @Test
    void thirdTest() {
        String createdUuid = "99f00f99-b3e2-46c8-9bc8-3082735a1f56";  // ← берём из ответа создания

        PersonUuidDao found = DataBaseSteps.getPersonByUuid(createdUuid);

        System.out.println(found.getUuid());

        assertNotNull(found, "Пациент по uuid не найден в базе");
    }
}