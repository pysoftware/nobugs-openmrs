package api.database.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientNameDao {

    private String givenName;
    private String familyName;

    public String getFullName() {
        return String.join(" ",
                givenName != null ? givenName.trim() : "",
                familyName != null ? familyName.trim() : ""
        ).trim();
    }
}
