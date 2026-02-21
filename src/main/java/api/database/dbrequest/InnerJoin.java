package api.database.dbrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnerJoin {

    private String table1;
    private String column1;
    private String table2;
    private String column2;

    public static InnerJoin condition(String table1, String column1, String table2, String column2) {
        return new InnerJoin(table1, column1, table2, column2);
    }
}