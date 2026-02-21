package api.database.dao.comparison;

import java.lang.reflect.Field;
import java.util.List;
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
        // Разделяем на части по точке
        String[] parts = fieldName.split("\\.");
        Object current = obj;

        for (String part : parts) {
            if (current == null) return null;

            // Если часть содержит индекс [n]
            if (part.contains("[")) {
                current = getIndexedValue(current, part);
            } else {
                current = getDirectFieldValue(current, part);
            }
        }

        return current;
    }

    private static Object getIndexedValue(Object obj, String fieldWithIndex) {
        // Извлекаем имя поля и индекс
        int bracketIndex = fieldWithIndex.indexOf('[');
        String fieldName = fieldWithIndex.substring(0, bracketIndex);
        int index = Integer.parseInt(
                fieldWithIndex.substring(bracketIndex + 1, fieldWithIndex.indexOf(']'))
        );

        // Получаем коллекцию/массив
        Object collection = getDirectFieldValue(obj, fieldName);
        if (collection == null) return null;

        // Обрабатываем список
        if (collection instanceof List) {
            List<?> list = (List<?>) collection;
            return index >= 0 && index < list.size() ? list.get(index) : null;
        }

        // Обрабатываем массив
        if (collection.getClass().isArray()) {
            Object[] array = (Object[]) collection;
            return index >= 0 && index < array.length ? array[index] : null;
        }

        throw new RuntimeException("Field '" + fieldName + "' is not a collection or array");
    }

    private static Object getDirectFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field: " + fieldName, e);
            }
        }
        throw new RuntimeException("Field not found: " + fieldName + " in class " + obj.getClass().getName());
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