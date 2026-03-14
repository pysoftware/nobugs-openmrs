package common.storage;

import api.models.interfaces.HasUuid;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.extensions.Prepare;

import java.util.*;

/**
 * Потокобезопасное хранилище тестовых данных (ThreadLocal).
 * Сущности хранятся по типу с уникальным uuid
 */
public class SessionStorage {

    private static final ThreadLocal<SessionStorage> INSTANCE = ThreadLocal.withInitial(SessionStorage::new);

    // тип сущности (person, Prepare.PERSON, Prepare.PATIENT и т.д.) → Map< uuid , объект >
    private final Map<Prepare, Map<String, HasUuid>> entitiesByType = new LinkedHashMap<>();

    private SessionStorage() {
    }

    /**
     * Добавляет сущность определённого типа с уникальным uuid
     */
    public static void add(HasUuid model) {
        if (model == null) {
            return;
        }

        SessionStorage storage = INSTANCE.get();

        Map<String, HasUuid> typeMap = storage.entitiesByType
                .computeIfAbsent(Prepare.fromModelClass(model.getClass()), k -> new LinkedHashMap<>());

        typeMap.put(model.getUuid(), model);
    }

    public static void delete(String uuid) {
        if (uuid == null) {
            return;
        }

        SessionStorage storage = INSTANCE.get();

        Iterator<Map.Entry<Prepare, Map<String, HasUuid>>> iterator =
                storage.entitiesByType.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Prepare, Map<String, HasUuid>> entry = iterator.next();

            Map<String, HasUuid> typeMap = entry.getValue();

            if (typeMap.remove(uuid) != null) {
                if (typeMap.isEmpty()) {
                    iterator.remove(); // удаляем пустой тип
                }
                break;
            }
        }
    }

    /**
     * Получить сущность по типу и номеру (индекс начинается с 1)
     */
    @SuppressWarnings("unchecked")
    public static <T extends HasUuid> T get(Prepare type, int index) {
        SessionStorage storage = INSTANCE.get();

        Map<String, HasUuid> typeMap = storage.entitiesByType.get(type);
        if (typeMap == null || index < 1 || index > typeMap.size()) {
            return null;
        }

        HasUuid model = typeMap.values().stream()
                .skip(index - 1) // индексы с 1
                .findFirst()
                .orElse(null);

        return (T) model;
    }

    /**
     * Получить сущность по типу и uuid
     */
    public static <T extends HasUuid> T get(Prepare type, String uuid) {
        SessionStorage storage = INSTANCE.get();

        Map<String, HasUuid> typeMap = storage.entitiesByType.get(type);
        if (typeMap == null) return null;

        HasUuid model = typeMap.get(uuid);
        if (model == null) return null;

        return (T) model;
    }


    /**
     * Получить все сущности заданного типа в порядке создания
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAll(Prepare type, Class<T> clazz) {
        SessionStorage storage = INSTANCE.get();

        Map<String, HasUuid> typeMap = storage.entitiesByType.get(type);
        if (typeMap == null || typeMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        for (Map.Entry<String, HasUuid> entry : typeMap.entrySet()) {
            HasUuid model = entry.getValue();
            if (clazz.isInstance(model)) {
                result.add((T) model);
            }
        }
        return result;
    }

    /**
     * Количество сущностей заданного типа
     */
    public static int count(Prepare type) {
        SessionStorage storage = INSTANCE.get();
        Map<String, HasUuid> typeMap = storage.entitiesByType.get(type);
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
    public static void deleteAllOfTypeViaApi(Prepare type) {

        Map<String, HasUuid> typeMap = INSTANCE.get().entitiesByType.get(type);
        if (typeMap == null || typeMap.isEmpty()) {
            return;
        }

        Endpoint endpoint = Endpoint.findByResponseModel(type);
        if (endpoint == null) {
            System.err.println("Не найден Endpoint для типа: " + type);
            return;
        }

        List<HasUuid> models = new ArrayList<>(typeMap.values());

        CrudRequester requester = new CrudRequester(
                RequestSpec.adminSpec(),
                endpoint,
                ResponseSpec.requestReturnsNoContent()
        );

        for (HasUuid model : models) {
            String uuid = null;
            try {
                uuid = model.getUuid();
                requester.delete(uuid);
            } catch (Exception e) {
                System.err.println("Не удалось удалить " + type +
                        " (uuid: " + uuid + "): " + e.getMessage());
            }
        }

        typeMap.clear();
    }

    /**
     * Удаляет через API все сущности всех типов, сохранённые в хранилище
     */
    public static void deleteAllViaApi() {
        // Копируем ключи, чтобы избежать ConcurrentModificationException
        List<Prepare> types = new ArrayList<>(INSTANCE.get().entitiesByType.keySet());

        for (Prepare type : types) {
            deleteAllOfTypeViaApi(type);
        }
    }
}
