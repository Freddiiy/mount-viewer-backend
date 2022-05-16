package dtos;

import entities.Mount;

import java.util.List;

public class ExtendedMountDTO extends MountDTO {

    private long itemId;
    private String iconDisplay;

    public ExtendedMountDTO(Mount m, Long itemId, String iconDisplay) {
        super(m);
        this.itemId = itemId;
        this.iconDisplay = iconDisplay;
    }

    public ExtendedMountDTO(Long mountId, String name, String description, String source, String display, Long itemId, String iconDisplay) {
        super(new Mount(mountId, name, description, source, display));
        this.itemId = itemId;
        this.iconDisplay = iconDisplay;
    }


    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getIconDisplay() {
        return iconDisplay;
    }

    public void setIconDisplay(String iconDisplay) {
        this.iconDisplay = iconDisplay;
    }
}
