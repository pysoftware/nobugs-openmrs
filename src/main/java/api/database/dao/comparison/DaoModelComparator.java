package api.database.dao.comparison;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Компаратор, который бросает исключение при первом несовпадении
 * с подробным сообщением о том, какое поле не прошло проверку.
 */
public class DaoModelComparator {
    private final DaoComparisonConfig config;

    public DaoModelComparator(DaoComparisonConfig config) {
        this.config = config;
    }

    /**
     * Сравнивает модель и DAO по правилам из конфигурации.
     * Если всё совпадает — метод просто завершается.
     * Если есть расхождение — бросает ComparisonException с описанием проблемы.
     *
     * @param model объект модели (например CreateUserResponseModel)
     * @param dao   объект DAO (например UserDao)
     * @throws ComparisonException если найдено хотя бы одно несовпадение
     */
    public void compare(Object model, Object dao) throws ComparisonException {
        if (model == null || dao == null) {
            throw new ComparisonException(
                    "Сравнение невозможно: один или оба объекта null (model=" + model + ", dao=" + dao + ")");
        }

        String modelClassName = model.getClass().getSimpleName();
        DaoComparisonConfig.ComparisonRule rule = config.getRuleForModel(modelClassName);

        if (rule == null) {
            throw new ComparisonException(
                    "Не найдено правило сравнения для модели: " + modelClassName);
        }

        String expectedDaoClassName = rule.getDaoClassName();
        String actualDaoClassName = dao.getClass().getSimpleName();

        if (!expectedDaoClassName.equals(actualDaoClassName)) {
            throw new ComparisonException(
                    String.format("Несоответствие типа DAO: ожидался %s, получен %s",
                            expectedDaoClassName, actualDaoClassName));
        }

        Map<String, String> mappings = rule.getFieldMappings();

        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            String modelFieldName = entry.getKey();
            String daoFieldName = entry.getValue();

            Object modelValue = getFieldValue(model, modelFieldName);
            Object daoValue   = getFieldValue(dao,   daoFieldName);

            if (!equals(modelValue, daoValue)) {
                throw new ComparisonException(
                        String.format(
                                "Несовпадение поля: %s (модель = %s) ≠ %s (DAO = %s)",
                                modelFieldName, formatValue(modelValue),
                                daoFieldName,   formatValue(daoValue)
                        ));
            }
        }

        // если дошли сюда — всё совпало, исключение не бросается
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new ComparisonException(
                    "Поле не найдено в классе " + obj.getClass().getSimpleName() + ": " + fieldName, e);
        } catch (IllegalAccessException e) {
            throw new ComparisonException(
                    "Нет доступа к полю " + fieldName + " в классе " + obj.getClass().getSimpleName(), e);
        }
    }

    private boolean equals(Object a, Object b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    private String formatValue(Object value) {
        if (value == null) return "null";
        return value.toString();
        // Можно улучшить: для массивов, коллекций, дат и т.д. — но для начала достаточно toString()
    }
}
