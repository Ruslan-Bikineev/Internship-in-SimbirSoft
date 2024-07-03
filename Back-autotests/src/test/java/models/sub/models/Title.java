package models.sub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Title {
    @JsonProperty("rendered")
    private String rendered;

    public boolean isEqual(Title title) {
        return this.rendered.equals(title.getRendered());
    }
}
