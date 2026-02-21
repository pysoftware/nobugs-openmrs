package api.database.dbrequest;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class UtcRowMapper<T> extends BeanPropertyRowMapper<T> {

    public UtcRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        Object value = rs.getObject(index);
        Class<?> targetType = pd.getPropertyType();

        // DATE → OffsetDateTime (UTC, начало дня)
        if (value instanceof java.sql.Date && targetType == OffsetDateTime.class) {
            return ((java.sql.Date) value)
                    .toLocalDate()
                    .atStartOfDay()
                    .atOffset(ZoneOffset.UTC);
        }

        // TIMESTAMP → OffsetDateTime (UTC)
        if (value instanceof Timestamp && targetType == OffsetDateTime.class) {
            return ((Timestamp) value)
                    .toInstant()
                    .atOffset(ZoneOffset.UTC);
        }

        // Если драйвер уже дал OffsetDateTime → нормализуем в UTC
        if (value instanceof OffsetDateTime odt && targetType == OffsetDateTime.class) {
            return odt.withOffsetSameInstant(ZoneOffset.UTC);
        }

        return super.getColumnValue(rs, index, pd);
    }
}