package api.requests.skelethon.interfaces;

import java.util.Map;

public interface GetAllEndpointInterface {
    Object getAll(Class<?> clazz);
    Object getAll(Class<?> clazz, Map<String, ?> queryParams);
    Object getAll(Class<?> clazz, String... uuids);
}
