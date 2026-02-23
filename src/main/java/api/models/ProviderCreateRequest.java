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
public class ProviderCreateRequest extends BaseModel {
    private String person;
    private String identifier;
    private List<Object> attributes;
    private Boolean retired;
}
