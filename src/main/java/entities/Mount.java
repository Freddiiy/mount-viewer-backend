package entities;

import javax.persistence.*;

@Entity
@Table(name = "Mount")
public class Mount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Attributes from table

    private int mountId;

    private String name;

    private int itemId;

    private String description;

    private String source;

    @Transient
    private boolean is_useable;

    public Mount(int mountId, String name, int itemId, String description, String source) {
        this.mountId = mountId;
        this.name = name;
        this.itemId = itemId;
        this.description = description;
        this.source = source;
    }

    public Mount() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_useable() {
        return is_useable;
    }

    public void setIs_useable(boolean is_useable) {
        this.is_useable = is_useable;
    }

    public int getMountId() {
        return mountId;
    }

    public void setMountId(int mountId) {
        this.mountId = mountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
