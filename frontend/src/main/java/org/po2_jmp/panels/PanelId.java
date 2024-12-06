package org.po2_jmp.panels;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
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

    private boolean isPanelIdValid(String panelId) {
        return (panelId != null) && !panelId.trim().isEmpty();
    }

}
