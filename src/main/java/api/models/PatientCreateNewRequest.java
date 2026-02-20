package api.models;

import api.generators.annotations.GeneratingListRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class PatientCreateNewRequest extends PatientCreateRequest {
    @GeneratingListRule(count = 2)
    private List<IdentifierRequest> identifiers;
    private Person person;
}
