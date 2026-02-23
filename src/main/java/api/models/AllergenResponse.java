package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergenResponse extends BaseModel {
    private String allergenType;
    private CodedAllergenResponse codedAllergen;
    private String nonCodedAllergen;
}
