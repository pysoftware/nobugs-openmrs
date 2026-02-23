package api.generators;

import api.generators.annotations.GeneratingDoubleRule;
import api.generators.annotations.GeneratingListRule;
import api.generators.annotations.GeneratingOffsetDateTimeRule;
import api.generators.annotations.GeneratingStringRule;
import com.github.curiousoddman.rgxgen.RgxGen;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;

public class RandomModelGenerator {

    private static final Random random = new Random();

    // Метод для генерации без optional значений
    public static <T> T generate(Class<T> clazz) {
        return generateWithFixed(clazz, Collections.emptyMap());
    }

    public static <T> T generate(Class<T> clazz, Consumer<T> customizer) {
        T instance = generate(clazz);
        customizer.accept(instance);
        return instance;
    }


    // Метод с фиксированными полями по имени (самый удобный и рекомендуемый)
    public static <T> T generate(Class<T> clazz, Map<String, Object> fixedValues) {
        return generateWithFixed(clazz, fixedValues != null ? fixedValues : Collections.emptyMap());
    }

    // Основной метод генерации с приоритетами
    private static <T> T generateWithFixed(Class<T> clazz, Map<String, Object> fixedValues) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            List<Field> fields = getAllFields(clazz);

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();

                // Самый высокий приоритет: значение из fixedValues (по имени поля)
                if (fixedValues.containsKey(fieldName)) {
                    field.set(instance, fixedValues.get(fieldName));
                    continue;
                }

                // Генерация по аннотациям или рандомно
                Object value;
                GeneratingStringRule stringRule = field.getAnnotation(GeneratingStringRule.class);
                GeneratingDoubleRule doubleRule = field.getAnnotation(GeneratingDoubleRule.class);
                GeneratingOffsetDateTimeRule offsetDateTimeRule = field.getAnnotation(GeneratingOffsetDateTimeRule.class);
                GeneratingListRule listRule = field.getAnnotation(GeneratingListRule.class);

                if (stringRule != null) {
                    value = generateFromRegex(stringRule.regex(), field.getType());
                } else if (doubleRule != null) {
                    value = generateFromDoubleRule(doubleRule, field.getType());
                } else if (offsetDateTimeRule != null) {
                    value = generateDateOffsetDateTime(offsetDateTimeRule.time());
                } else if (listRule != null) {
                    value = generateList(listRule.count(), field, fixedValues);
                } else {
                    value = generateValue(field.getType(), field, fixedValues);
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

    private static Object generateValue(Class<?> type, Field field, Map<String, Object> fixedValues) {
        // ===== аннотации (если есть поле) =====
        if (field != null) {
            GeneratingStringRule stringRule = field.getAnnotation(GeneratingStringRule.class);
            GeneratingDoubleRule doubleRule = field.getAnnotation(GeneratingDoubleRule.class);
            GeneratingOffsetDateTimeRule offsetDateTimeRule = field.getAnnotation(GeneratingOffsetDateTimeRule.class);
            GeneratingListRule listRule = field.getAnnotation(GeneratingListRule.class);

            if (stringRule != null) {
                return generateFromRegex(stringRule.regex(), type);
            } else if (doubleRule != null) {
                return generateFromDoubleRule(doubleRule, type);
            } else if (offsetDateTimeRule != null) {
                return generateDateOffsetDateTime(offsetDateTimeRule.time());
            } else if (listRule !=null) {
                return generateList(listRule.count(), field, fixedValues);
            }
        }

        // ===== обычная генерация по типу =====
        if (type.equals(List.class) && field != null) {
            return generateList(-1, field, fixedValues);
        }

        if (type.isEnum()) {
            return randomEnum((Class<? extends Enum<?>>) type);
        }

        if (type.equals(String.class)) {
            if (field != null && field.getName().toLowerCase().contains("postal")) {
                return String.format("%05d", 10000 + random.nextInt(90000));
            }
            return UUID.randomUUID().toString().substring(0, 8);
        }

        if (type.equals(Integer.class) || type.equals(int.class)) return random.nextInt(1000);
        if (type.equals(Long.class) || type.equals(long.class)) return random.nextLong();
        if (type.equals(Double.class) || type.equals(double.class)) return random.nextDouble() * 100;
        if (type.equals(Float.class) || type.equals(float.class)) return random.nextFloat() * 100;
        if (type.equals(Boolean.class) || type.equals(boolean.class)) return random.nextBoolean();
        if (type.equals(OffsetDateTime.class)) return generateDateOffsetDateTime(true);
        if (type.equals(Date.class)) return new Date(System.currentTimeMillis() - random.nextInt(1_000_000_000));

        // ===== вложенный объект =====
        return generate(type, fixedValues);
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

    private static List<Object> generateList(int count, Field field, Map<String, Object> fixedValues) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType pt) {
            Type itemType = pt.getActualTypeArguments()[0];

            if (itemType instanceof Class<?> itemClass) {
                count = count < 0 ? random.nextInt(3) + 1 : count; // 1–3 элемента, если нет аннотации с количеством
                List<Object> list = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    //list.add(generate(itemClass, fixedValues));
                    list.add(generateValue(itemClass, null, fixedValues));
                }
                return list;
            }
        }
        return Collections.emptyList();
    }

    private static OffsetDateTime generateDateOffsetDateTime(boolean withTime) {
        // Возраст от 0 до 100 лет
        int age = random.nextInt(101);  // 0..100 включительно

        // Случайное количество дней внутри года
        int extraDays = random.nextInt(365);

        // Генерируем дату рождения
        OffsetDateTime birthDate = OffsetDateTime.now(ZoneOffset.UTC)
                .minusYears(age)                // отнимаем годы
                .minusDays(extraDays);          // добавляем разброс внутри года

        if (withTime) {
            return birthDate
                    .withHour(random.nextInt(24))   // случайное время суток
                    .withMinute(random.nextInt(60))
                    .withSecond(random.nextInt(60))
                    .withNano(0);
        }
        return birthDate.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    private static Enum<?> randomEnum(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
