package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientCreateFromExistingPersonRequest extends BaseModel {
    private String person;
    private List<IdentifierRequest> identifiers;
}
