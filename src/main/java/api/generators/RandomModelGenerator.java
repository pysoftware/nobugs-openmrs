package api.generators;

import api.generators.annotations.GeneratingDoubleRule;
import api.generators.annotations.GeneratingStringRule;
import api.generators.annotations.Optional;
import api.models.enums.Gender;
import com.github.curiousoddman.rgxgen.RgxGen;
import common.annotations.DateFormat;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RandomModelGenerator {

    private static final Random random = new Random();

    // Метод для генерации без optional значений
    public static <T> T generate(Class<T> clazz) {
        return generateWithFixed(clazz, Collections.emptyMap());
    }

    // Метод с фиксированными полями по имени (самый удобный и рекомендуемый)
    public static <T> T generate(Class<T> clazz, Map<String, Object> fixedValues) {
        return generateWithFixed(clazz, fixedValues != null ? fixedValues : Collections.emptyMap());
    }

    // Старый varargs-метод оставлен для совместимости
    public static <T> T generate(Object... valuesAndClass) {
        if (valuesAndClass == null || valuesAndClass.length == 0) {
            throw new IllegalArgumentException("At least class must be provided");
        }
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) valuesAndClass[valuesAndClass.length - 1];
        Map<String, Object> fixed = new HashMap<>();
        List<Field> optionalFields = getOptionalFields(clazz);

        if (valuesAndClass.length - 1 > optionalFields.size()) {
            throw new IllegalArgumentException("Too many optional values provided");
        }

        for (int i = 0; i < valuesAndClass.length - 1; i++) {
            Field field = optionalFields.get(i);
            fixed.put(field.getName(), valuesAndClass[i]);
        }

        return generateWithFixed(clazz, fixed);
    }

    // Основной метод генерации с приоритетами
    private static <T> T generateWithFixed(Class<T> clazz, Map<String, Object> fixedValues) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            List<Field> fields = getAllFields(clazz);

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();

                // 1. Самый высокий приоритет: значение из fixedValues (по имени поля)
                if (fixedValues.containsKey(fieldName)) {
                    field.set(instance, fixedValues.get(fieldName));
                    continue;
                }

                // 2. Если поле @Optional и значение не передано — генерируем как обычное поле
                // (никакого continue — просто идём дальше по логике генерации)

                // 3. Генерация по аннотациям или рандомно
                Object value;
                GeneratingStringRule stringRule = field.getAnnotation(GeneratingStringRule.class);
                if (stringRule != null) {
                    value = generateFromRegex(stringRule.regex(), field.getType());
                } else {
                    GeneratingDoubleRule doubleRule = field.getAnnotation(GeneratingDoubleRule.class);
                    value = doubleRule != null
                            ? generateFromDoubleRule(doubleRule, field.getType())
                            : generateRandomValue(field);
                }

                field.set(instance, value);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate entity", e);
        }
    }

    private static Object generateFromDoubleRule(GeneratingDoubleRule rule, Class<?> type) {
        double value = rule.min() + (rule.max() - rule.min()) * random.nextDouble();
        if (type.equals(Float.class) || type.equals(float.class)) {
            return (float) (Math.round(value * Math.pow(10, rule.precision())) / Math.pow(10, rule.precision()));
        }
        return Math.round(value * Math.pow(10, rule.precision())) / Math.pow(10, rule.precision());
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static List<Field> getOptionalFields(Class<?> clazz) {
        List<Field> optional = new ArrayList<>();
        for (Field field : getAllFields(clazz)) {
            if (field.getAnnotation(Optional.class) != null) {
                optional.add(field);
            }
        }
        return optional;
    }

    private static Object generateRandomValue(Field field) {
        Class<?> type = field.getType();

        return switch (type) {
            case Class<?> c when c.equals(List.class) -> generateList(field);
            case Class<?> c when c.equals(Gender.class) -> randomEnum(Gender.class);
            case Class<?> c when c.equals(String.class) -> {
                if (field.getName().toLowerCase().contains("postal")) {
                    yield String.format("%05d", 10000 + random.nextInt(90000));
                }
                if (field.getAnnotation(DateFormat.class) != null) {
                    yield generateDateString(field.getAnnotation(DateFormat.class).pattern());
                }
                yield UUID.randomUUID().toString().substring(0, 8);
            }
            case Class<?> c when c.equals(Integer.class) || c.equals(int.class) -> random.nextInt(1000);
            case Class<?> c when c.equals(Long.class) || c.equals(long.class) -> random.nextLong();
            case Class<?> c when c.equals(Double.class) || c.equals(double.class) -> random.nextDouble() * 100;
            case Class<?> c when c.equals(Float.class) || c.equals(float.class) -> random.nextFloat() * 100;
            case Class<?> c when c.equals(Boolean.class) || c.equals(boolean.class) -> random.nextBoolean();
            case Class<?> c when c.equals(Date.class) -> new Date(System.currentTimeMillis() - random.nextInt(1000000000));
            default -> generate(type);
        };
    }

    private static Object generateFromRegex(String regex, Class<?> type) {
        RgxGen rgxGen = new RgxGen(regex);
        String result = rgxGen.generate();
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return Integer.parseInt(result);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.parseLong(result);
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            return Float.parseFloat(result);
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.parseDouble(result);
        } else {
            return result;
        }
    }

    private static List<String> generateRandomList(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType pt && pt.getActualTypeArguments()[0] == String.class) {
            return List.of(
                    UUID.randomUUID().toString().substring(0, 5),
                    UUID.randomUUID().toString().substring(0, 5)
            );
        }
        return Collections.emptyList();
    }

    private static List<Object> generateList(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType pt) {
            Type itemType = pt.getActualTypeArguments()[0];
            if (itemType instanceof Class<?> itemClass) {
                int count = random.nextInt(3) + 1; // 1–3 элемента
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    list.add(generate(itemClass));
                }
                return list;
            }
        }
        return Collections.emptyList();
    }

    private static String generateDateString(String pattern) {
        int yearsAgo = random.nextInt(101);  // 0–100 лет
        int extraDays = random.nextInt(366);

        LocalDate date = LocalDate.now()
                .minusYears(yearsAgo)
                .minusDays(extraDays);

        return date.atStartOfDay(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern(pattern));
        // → по умолчанию формат "yyyy-MM-dd'T'HH:mm:ss.SSSZ" - "2003-05-08T00:00:00.000+0000"
    }

    private static <E extends Enum<E>> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}