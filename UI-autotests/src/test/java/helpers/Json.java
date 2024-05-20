package helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Json {
    public void serializeJsonFile(String path, Object object) {
        ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            objectMapper.writeValue(new File(path), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cookie> deserializeCookiesFromJsonFile(String path) {
        File file = new File(path);
        List<Cookie> cookieList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.exists()) {
            try {
                List<CookiePojo> cookiePojoList = objectMapper.readValue(file, new TypeReference<>() {
                });
                cookiePojoList.forEach(cookie -> cookieList.add(cookie.toCookie()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cookieList;
    }
}
