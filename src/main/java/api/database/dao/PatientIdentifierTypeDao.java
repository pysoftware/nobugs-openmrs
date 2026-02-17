package api.database.dao;

import api.models.enums.LocationBehavior;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientIdentifierTypeDao {
    private String uuid;
    private String name;
    private String description;
    private String format;
    private String formatDescription;
    private String validator;
    private LocationBehavior locationBehavior;
    private String uniquenessBehavior;
}
