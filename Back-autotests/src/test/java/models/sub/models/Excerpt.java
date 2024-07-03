package models.sub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Excerpt {
    @JsonProperty("rendered")
    private String rendered;
    @JsonProperty("protected")
    private boolean isProtected;

    public void setRendered(String rendered) {
        this.rendered = rendered.replaceAll("<p>", "")
                .replaceAll("</p>\n", "");
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isEqual(Excerpt excerpt) {
        boolean result = false;
        if (rendered.equals(excerpt.getRendered()) &&
                isProtected == excerpt.isProtected()) {
            result = true;
        }
        return result;
    }
}
