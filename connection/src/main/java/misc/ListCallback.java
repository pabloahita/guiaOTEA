package misc;

import com.google.gson.JsonObject;

import java.util.List;

public interface ListCallback {

    void onSuccess(List<JsonObject> data);
    void onError(String errorResponse);
}
