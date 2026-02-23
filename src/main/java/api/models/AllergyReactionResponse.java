package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyReactionResponse extends BaseModel {
    private ReactionResponse reaction;
    private String reactionNonCoded;
}
