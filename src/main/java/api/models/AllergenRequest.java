package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergenRequest extends BaseModel {
    private String allergenType;
    private CodedAllergenRequest codedAllergen;
}
