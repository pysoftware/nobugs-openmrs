package common.storage;

import api.models.PersonResponse;

import java.util.*;
/**
 * Потокобезопасное хранилище тестовых данных (ThreadLocal).
 * Сущности хранятся по типу с автоматической нумерацией начиная с 1.
 */
public class SessionStorage {

    private static final ThreadLocal<SessionStorage> INSTANCE = ThreadLocal.withInitial(SessionStorage::new);

    // тип сущности (person, patient, visit и т.д.) → Map<индекс с 1, объект>
    private final Map<String, Map<Integer, Object>> entitiesByType = new LinkedHashMap<>();

    private SessionStorage() {
    }

    /**
     * Добавляет сущность определённого типа с автоматической нумерацией (начиная с 1)
     */
    public static void addEntity(String type, Object entity) {
        if (entity == null) {
            return;
        }

        SessionStorage storage = INSTANCE.get();
        String normalizedType = type.toLowerCase();

        Map<Integer, Object> typeMap = storage.entitiesByType
                .computeIfAbsent(normalizedType, k -> new LinkedHashMap<>());

        int nextIndex = typeMap.size() + 1;
        typeMap.put(nextIndex, entity);
    }

    /**
     * Получить сущность по типу и номеру (индекс начинается с 1)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String type, int index, Class<T> clazz) {
        SessionStorage storage = INSTANCE.get();
        String normalizedType = type.toLowerCase();

        Map<Integer, Object> typeMap = storage.entitiesByType.get(normalizedType);
        if (typeMap == null) {
            return null;
        }

        Object obj = typeMap.get(index);
        if (obj == null) {
            return null;
        }

        if (!clazz.isInstance(obj)) {
            throw new ClassCastException(
                    String.format("Сущность типа '%s' с индексом %d имеет класс %s, ожидался %s",
                            type, index, obj.getClass().getName(), clazz.getName()));
        }

        return (T) obj;
    }


    /**
     * Получить все сущности заданного типа в порядке создания
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAll(String type, Class<T> clazz) {
        SessionStorage storage = INSTANCE.get();
        String normalizedType = type.toLowerCase();

        Map<Integer, Object> typeMap = storage.entitiesByType.get(normalizedType);
        if (typeMap == null || typeMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        for (int i = 1; i <= typeMap.size(); i++) {
            Object obj = typeMap.get(i);
            if (obj != null && clazz.isInstance(obj)) {
                result.add((T) obj);
            }
        }
        return result;
    }

    /**
     * Количество сущностей заданного типа
     */
    public static int count(String type) {
        SessionStorage storage = INSTANCE.get();
        String normalizedType = type.toLowerCase();
        Map<Integer, Object> typeMap = storage.entitiesByType.get(normalizedType);
        return typeMap != null ? typeMap.size() : 0;
    }

    /**
     * Полная очистка хранилища
     */
    public static void clear() {
        INSTANCE.get().entitiesByType.clear();
    }

    // Person
    public static void addPerson(PersonResponse person) {
        addEntity("person", person);
    }

    /**
     * Возвращает персону по порядковому номеру (нумерация начинается с 1).
     * Если индекс выходит за границы — возвращает null.
     */
    public static PersonResponse getPerson(int index) {
        return get("person", index, PersonResponse.class);
    }

    public static List<PersonResponse> getAllPersons() {
        return getAll("person", PersonResponse.class);
    }

    public static int getPersonCount() {
        return count("person");
    }

/*
    // Patient (пример, добавь когда будет реализация)
    public static void addPatient(PatientResponse patient) {
        addEntity("patient", patient);
    }

    public static PatientResponse getPatient(int index) {
        return get("patient", index, PatientResponse.class);
    }

    public static List<PatientResponse> getAllPatients() {
        return getAll("patient", PatientResponse.class);
    }
*/


}
