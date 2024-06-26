package heplers;

import org.json.JSONObject;

public class helpers {
    public static JSONObject getDefaultJsonBody() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("status", "publish");
        requestBody.put("title", "Test title");
        requestBody.put("content", "Test content");
        requestBody.put("excerpt", "Test excerpt");
        requestBody.put("comment_status", "open");
        requestBody.put("ping_status", "open");
        return requestBody;
    }
}
