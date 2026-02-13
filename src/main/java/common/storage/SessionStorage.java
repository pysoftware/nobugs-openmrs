package common.storage;

import api.models.PersonResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

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
    /**
     * Удаляет через API все сущности заданного типа, которые были сохранены в хранилище
     */
    public static void deleteAllOfTypeViaApi(String type) {
        String normalizedType = type.toLowerCase().trim();

        Map<Integer, Object> typeMap = INSTANCE.get().entitiesByType.get(normalizedType);
        if (typeMap == null || typeMap.isEmpty()) {
            return;
        }

        Endpoint endpoint = Endpoint.findByEntityType(normalizedType);
        if (endpoint == null) {
            System.err.println("Не найден Endpoint для типа: " + type);
            return;
        }

        List<Object> entities = new ArrayList<>(typeMap.values());

        CrudRequester requester = new CrudRequester(
                RequestSpec.adminSpec(),
                endpoint,
                ResponseSpec.requestReturnsNoContent()
        );

        for (Object entity : entities) {
            String uuid = getUuid(entity);
            if (uuid != null && !uuid.isBlank()) {
                try {
                    requester.delete(uuid);
                } catch (Exception e) {
                    System.err.println("Не удалось удалить " + normalizedType +
                            " (uuid: " + uuid + "): " + e.getMessage());
                }
            }
        }

        typeMap.clear();
    }

    /**
     * Удаляет через API все сущности всех типов, сохранённые в хранилище
     */
    public static void deleteAllViaApi() {
        // Копируем ключи, чтобы избежать ConcurrentModificationException
        List<String> types = new ArrayList<>(INSTANCE.get().entitiesByType.keySet());

        for (String type : types) {
            deleteAllOfTypeViaApi(type);
        }
    }
    private static String getUuid(Object entity) {
        if (entity instanceof PersonResponse person) {
            return person.getUuid();
        }
        // Добавьте сюда другие типы по мере необходимости, далее пример:
        // if (entity instanceof PatientResponse p) return p.getUuid();
        // if (entity instanceof VisitResponse v) return v.getUuid();

        System.err.println("Не удалось получить uuid из объекта типа: " +
                (entity != null ? entity.getClass().getSimpleName() : "null"));
        return null;
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

    public static int getPersonCount() {
        return count("person");
    }

}
