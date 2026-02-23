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
public class LoginResponse extends BaseModel{
    private boolean authenticated;
    private String locale;
    private List<String> allowedLocales;
    private Object user;
    private Object sessionLocation;
    private Object currentProvider;

}
