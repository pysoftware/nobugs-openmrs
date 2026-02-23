package api.database.dao.comparison;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс для загрузки и парсинга правил сравнения из файла dao-comparison.properties.
 * Формат свойств: ModelClassName=DaoClassName:field1=modelField1=daoField1,field2=modelField2=daoField2,...
 * Пример: CreateUserResponseModel=UserDao:username=username,role=role,id=id,name=name
 */
public class DaoComparisonConfig {
    private final Map<String, ComparisonRule> rules = new HashMap<>();

    public DaoComparisonConfig() {
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dao-comparison.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find dao-comparison.properties");
            }
            properties.load(input);

            for (String modelClassName : properties.stringPropertyNames()) {
                String value = properties.getProperty(modelClassName);
                String[] parts = value.split(":", 2);
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid format for rule: " + value);
                }
                String daoClassName = parts[0];
                Map<String, String> fieldMappings = parseFieldMappings(parts[1]);

                rules.put(modelClassName, new ComparisonRule(daoClassName, fieldMappings));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading dao-comparison.properties", e);
        }
    }

    private Map<String, String> parseFieldMappings(String mappingsStr) {
        Map<String, String> mappings = new HashMap<>();
        String[] pairs = mappingsStr.split(",");
        for (String pair : pairs) {
            String[] fields = pair.split("=");
            if (fields.length != 2) {
                throw new IllegalArgumentException("Invalid field mapping: " + pair);
            }
            mappings.put(fields[0], fields[1]); // modelField -> daoField
        }
        return mappings;
    }

    public ComparisonRule getRuleForModel(String modelClassName) {
        return rules.get(modelClassName);
    }

    public static class ComparisonRule {
        private final String daoClassName;
        private final Map<String, String> fieldMappings; // modelField -> daoField

        public ComparisonRule(String daoClassName, Map<String, String> fieldMappings) {
            this.daoClassName = daoClassName;
            this.fieldMappings = fieldMappings;
        }

        public String getDaoClassName() {
            return daoClassName;
        }

        public Map<String, String> getFieldMappings() {
            return fieldMappings;
        }
    }
}
