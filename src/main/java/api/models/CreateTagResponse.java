package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagResponse extends BaseModel {
    private String uuid;
    private String display;
    private String name;
    private String description;
    private String retired;
    private List<Link>links;
    private String resourceVersion;
}
