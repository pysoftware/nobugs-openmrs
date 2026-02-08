package api.requests.skelethon.interfaces;

import api.models.BaseModel;

public interface CrudEndpointInterface {
    Object post(BaseModel model);
    Object get(String uuid);
    Object get();
    Object update(BaseModel model, String uuid);
    Object delete(String uuid);
}