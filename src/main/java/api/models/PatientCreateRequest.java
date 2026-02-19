package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public sealed abstract class PatientCreateRequest extends BaseModel
        permits PatientCreateNewRequest, PatientCreateFromExistingPersonRequest {
    private List<IdentifierRequest> identifiers;
}
