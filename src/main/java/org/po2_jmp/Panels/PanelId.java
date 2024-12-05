package org.po2_jmp.Panels;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PanelId {

    private final String value;

    public PanelId(String panelId) {
        if (!isPanelIdValid(panelId)) {
            throw new IllegalArgumentException("Illegal value passed " +
                    "to PanelId constructor");
        }
        this.value = panelId;
    }

    public String getValue() {
        return value;
    }

    private boolean isPanelIdValid(String panelId) {
        return (panelId != null) && !panelId.trim().isEmpty();
    }

}
