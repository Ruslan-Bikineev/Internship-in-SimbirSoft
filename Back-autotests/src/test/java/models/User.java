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
