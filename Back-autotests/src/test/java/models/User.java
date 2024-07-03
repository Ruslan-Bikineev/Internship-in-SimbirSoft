package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"description", "link", "avatar_urls", "meta", "_links"})
public class User {
    @JsonProperty("id")
    private long id;
    private String userLogin;
    private String userPass;
    @JsonProperty("slug")
    private String userNicename;
    private String userEmail;
    @JsonProperty("url")
    private String userUrl;
    private Timestamp userRegistered;
    private String userActivationKey;
    private int userStatus;
    @JsonProperty("name")
    private String displayName;

    public static User getDefaultUser() {
        User user = new User(
                0,
                "test_user_login",
                "test_user_pass",
                "test_use_nicename",
                "test_user_email",
                "test_user_url",
                new Timestamp(System.currentTimeMillis()),
                "test_user_activation_key",
                0,
                "test_display_name");
        user.getUserRegistered().setNanos(0);
        return user;
    }

    public boolean isEqual(User user) {
        boolean result = false;
        if (getId() == user.getId() &&
                getUserUrl().equals(user.getUserUrl()) &&
                getDisplayName().equals(user.getDisplayName()) &&
                getUserNicename().equals(user.getUserNicename())) {
            result = true;
        }
        return result;
    }
}
