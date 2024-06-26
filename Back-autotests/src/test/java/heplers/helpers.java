package heplers;

import java.util.HashMap;
import java.util.Map;

public class helpers {
    public static Map<String, String> getDefaultJsonBodyPost() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("status", "publish");
        requestBody.put("title", "Test title");
        requestBody.put("content", "Test content");
        requestBody.put("excerpt", "Test excerpt");
        requestBody.put("comment_status", "open");
        requestBody.put("ping_status", "open");
        return requestBody;
    }
}
