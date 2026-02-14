package api.database.dbrequest;

import api.configs.Config;
import lombok.Builder;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DBRequest {

    private RequestType requestType;
    private String table;
    private List<InnerJoin> innerJoins = new ArrayList<>();
    private List<Condition> conditions = new ArrayList<>();
    private String customSql;
    private Class<?> extractAsClass;


    public enum RequestType {
        SELECT
    }

    public <T> T extractAs(Class<T> clazz) {
        this.extractAsClass = clazz;
        return executeQuery(clazz);
    }

    private <T> T executeQuery(Class<T> clazz) {
        String sql = buildSQL();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        // Создаём PreparedStatementSetter для подстановки параметров
        PreparedStatementSetter pss = ps -> {
            if (conditions != null) {
                for (int i = 0; i < conditions.size(); i++) {
                    ps.setObject(i + 1, conditions.get(i).getValue());
                }
            }
        };

        // Получаем список результатов
        try {
            List<T> results = jdbcTemplate.query(
                    sql,
                    pss,
                    new UtcRowMapper<>(clazz)
            );

            // Возвращаем первый элемент или null
            return results.isEmpty() ? null : results.get(0);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database query failed", e);
        }
    }

    private DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(Config.getProperty("db.url"));
        ds.setUsername(Config.getProperty("db.username"));
        ds.setPassword(Config.getProperty("db.password"));
        return ds;
    }

    private String buildSQL() {
        if (customSql != null && !customSql.isBlank()) {
            return customSql.trim();
        }

        if (requestType != RequestType.SELECT) {
            throw new UnsupportedOperationException("Автоматическая генерация SQL поддерживается только для SELECT");
        }

        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(table);

        if (!innerJoins.isEmpty()) {

            for (int i = 0; i < innerJoins.size(); i++) {
                sb.append(" INNER JOIN ");
                InnerJoin join = innerJoins.get(i);
                sb.append(join.getTable1()).append(" ON ")
                        .append(join.getTable1()).append(".").append(join.getColumn1()).append(" = ")
                        .append(join.getTable2()).append(".").append(join.getColumn2());
            }
        }

        if (!conditions.isEmpty()) {
            sb.append(" WHERE ");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) sb.append(" AND ");
                Condition c = conditions.get(i);
                if (c.getTable() != null) sb.append(c.getTable()).append(".");
                sb.append(c.getColumn()).append(" ").append(c.getOperator()).append(" ?");
            }
        }

        return sb.toString();
    }

    public static DBRequestBuilder builder() {
        return new DBRequestBuilder();
    }

    private Integer getIntOrNull(ResultSet rs, String column) throws SQLException {
        int value = rs.getInt(column);
        return rs.wasNull() ? null : value;
    }

    public static class DBRequestBuilder {
        private RequestType requestType = RequestType.SELECT;
        private String table;
        private List<InnerJoin> innerJoins = new ArrayList<>();
        private List<Condition> conditions = new ArrayList<>();

        public DBRequestBuilder table(String table) {
            this.table = table;
            return this;
        }

        public DBRequestBuilder innerJoin(InnerJoin innerJoin) {
            this.innerJoins.add(innerJoin);
            return this;
        }

        public DBRequestBuilder where(Condition condition) {
            this.conditions.add(condition);
            return this;
        }

        public <T> T extractAs(Class<T> clazz) {
            this.extractAsClass = clazz;
            DBRequest request = DBRequest.builder()
                    .requestType(requestType)
                    .table(table)
                    .innerJoins(innerJoins)
                    .conditions(conditions)
                    .extractAsClass(extractAsClass)
                    .build();
            return request.extractAs(clazz);
        }
    }
}