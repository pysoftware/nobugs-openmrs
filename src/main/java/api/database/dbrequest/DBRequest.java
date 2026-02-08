package api.database.dbrequest;

import api.configs.Config;
import api.database.dao.PatientNameDao;
import api.database.dao.PersonUuidDao;
import lombok.Builder;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DBRequest {

    private RequestType requestType;
    private String table;
    private List<Condition> conditions = new ArrayList<>();
    private String customSql;

    public enum RequestType {
        SELECT
    }

    public List<PatientNameDao> execute() {
        String sql = buildSQL();
        List<PatientNameDao> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                Config.getProperty("db.url"),
                Config.getProperty("db.username"),
                Config.getProperty("db.password"));

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < conditions.size(); i++) {
                stmt.setObject(i + 1, conditions.get(i).getValue());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(PatientNameDao.builder()
                            .givenName(rs.getString("given_name"))
                            .familyName(rs.getString("family_name"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка запроса: " + sql, e);
        }

        return list;
    }

    private String buildSQL() {
        if (customSql != null && !customSql.isBlank()) {
            return customSql.trim();
        }

        if (requestType != RequestType.SELECT) {
            throw new UnsupportedOperationException("Автоматическая генерация SQL поддерживается только для SELECT");
        }

        // Если customSql не задан — генерируем простой SELECT * FROM table WHERE ...
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(table);

        if (!conditions.isEmpty()) {
            sb.append(" WHERE ");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) sb.append(" AND ");
                Condition c = conditions.get(i);
                sb.append(c.getColumn()).append(" ").append(c.getOperator()).append(" ?");
            }
        }

        return sb.toString();
    }

    public static DBRequestBuilder builder() {
        return new DBRequestBuilder();
    }

    public static class DBRequestBuilder {
        private RequestType requestType = RequestType.SELECT;
        private String table;
        private List<Condition> conditions = new ArrayList<>();
        private String customSql;

        public DBRequestBuilder table(String table) {
            this.table = table;
            return this;
        }

        public DBRequestBuilder where(Condition condition) {
            this.conditions.add(condition);
            return this;
        }

        public DBRequestBuilder customSql(String sql) {
            this.customSql = sql;
            return this;
        }

        public List<PatientNameDao> executePatientName() {
            DBRequest request = DBRequest.builder()
                    .requestType(requestType)
                    .table(table)
                    .conditions(conditions)
                    .customSql(customSql)
                    .build();
            return request.execute();
        }

        public PersonUuidDao executePersonUuid() {
            DBRequest request = DBRequest.builder()
                    .requestType(requestType)
                    .table(table)
                    .conditions(conditions)
                    .customSql(customSql)
                    .build();
            return request.extractAsPersonUuid();
        }
    }

    public PersonUuidDao extractAsPersonUuid() {
        String sql = buildSQL();

        try (Connection conn = DriverManager.getConnection(
                Config.getProperty("db.url"),
                Config.getProperty("db.username"),
                Config.getProperty("db.password"));

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conditions != null) {
                for (int i = 0; i < conditions.size(); i++) {
                    stmt.setObject(i + 1, conditions.get(i).getValue());
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return PersonUuidDao.builder()
                            .uuid(rs.getString("uuid"))
                            .build();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка запроса по uuid: " + sql, e);
        }

        return null;
    }

    private Integer getIntOrNull(ResultSet rs, String column) throws SQLException {
        int value = rs.getInt(column);
        return rs.wasNull() ? null : value;
    }
}