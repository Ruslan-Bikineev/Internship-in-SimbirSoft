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
    public void serializeJsonFile(String path, Object object) throws IOException {
        ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        objectMapper.writeValue(new File(path), object);
    }

    public List<Cookie> deserializeCookiesFromJsonFile(String path) throws IOException {
        File file = new File(path);
        List<Cookie> cookieList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.exists()) {
            List<CookiePOJO> cookiePOJOList = objectMapper.readValue(file, new TypeReference<>() { });
            for (CookiePOJO cookiePOJO : cookiePOJOList) {
                cookieList.add(cookiePOJO.toCookie());
            }
        } else {
            throw new IOException("File " + path + " not found");
        }
        return cookieList;
    }
}
