package models.sub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guid {
    private String rendered;

    public boolean isEqual(Guid guid) {
        return rendered.equals(guid.getRendered());
    }
}
