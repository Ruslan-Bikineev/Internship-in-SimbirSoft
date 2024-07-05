package models.sub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @JsonProperty("rendered")
    private String rendered;
    @JsonProperty("protected")
    private boolean isProtected;

    public Content(String rendered) {
        this.rendered = rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered.replaceAll("<p>", "")
                .replaceAll("</p>\n", "");
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public boolean isEqual(Content content) {
        boolean result = false;
        if (rendered.equals(content.getRendered()) &&
                isProtected == content.isProtected()) {
            result = true;
        }
        return result;
    }
}
