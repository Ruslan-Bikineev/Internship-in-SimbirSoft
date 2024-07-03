package models.sub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Excerpt {
    @JsonProperty("rendered")
    private String rendered;
    @JsonProperty("protected")
    private boolean isProtected;
}
