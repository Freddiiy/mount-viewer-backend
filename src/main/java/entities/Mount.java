package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mount")
public class Mount {

    @Id
    @GeneratedValue
    private Long id;

    private int mountId;

    private String name;

    private int itemId;


    public Mount(int mountId, String name, int itemId) {
        this.mountId = mountId;
        this.name = name;
        this.itemId = itemId;
    }

    public Mount() {
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
