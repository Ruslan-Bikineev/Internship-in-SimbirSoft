package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @JsonProperty("rendered")
    private String rendered;
    @JsonProperty("protected")
    private boolean isProtected;
}
