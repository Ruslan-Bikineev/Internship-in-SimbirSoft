package helpers;

import lombok.Data;
import org.openqa.selenium.Cookie;

import java.util.Date;

@Data
public class CookiePOJO {
    private String name;
    private String value;
    private String domain;
    private String path;
    private Date expiry;
    private boolean isSecure;
    private boolean isHttpOnly;
    private String sameSite;

    public Cookie toCookie() {
        return new Cookie(name, value, domain, path, expiry, isSecure, isHttpOnly, sameSite);
    }
}
