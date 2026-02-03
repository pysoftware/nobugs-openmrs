package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse extends BaseModel{
    String uuid;
    String display;
    String username;
    String systemId;
    Map<String, String> userProperties;
    PersonSummary person;
    List<Role> roles;
    List<Privilege> privileges;
}
