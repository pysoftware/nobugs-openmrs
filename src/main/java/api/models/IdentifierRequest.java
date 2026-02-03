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
public class IdentifierRequest extends BaseModel {
    private String identifier;
    private String identifierType;
    private String location;
    private Boolean preferred;
}
