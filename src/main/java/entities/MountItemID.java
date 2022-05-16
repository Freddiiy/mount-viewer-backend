package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MountItemID")
public class MountItemID {
    @Id
    @GeneratedValue
    private Long id;

    private Long mountId;

    private String name;

    private String iconDisplay;

    private Long itemId;


    public MountItemID(Long mountId, String name, Long itemId, String iconDisplay) {
        this.mountId = mountId;
        this.name = name;
        this.itemId = itemId;
        this.iconDisplay = iconDisplay;
    }

    public MountItemID() {
    }

    public String getIconDisplay() {
        return iconDisplay;
    }

    public void setIconDisplay(String iconDisplay) {
        this.iconDisplay = iconDisplay;
    }

    public Long getMountId() {
        return mountId;
    }

    public void setMountId(Long mountId) {
        this.mountId = mountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
