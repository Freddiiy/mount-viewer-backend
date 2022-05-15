package entities;

import dtos.CreatureDisplayDTO;
import dtos.MountDTO;
import dtos.SourceDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Mount")
public class Mount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Attributes from table
    private Long mountId;

    private String name;

    private Long itemId;

    @Column(length = 8000)
    private String description;

    private String source;

    private String display;

    @Transient
    private boolean is_useable;

    public Mount(Long mountId, String name, Long itemId, String description, String source, String display) {
        this.mountId = mountId;
        this.name = name;
        this.itemId = itemId;
        this.description = description;
        this.source = source;
        this.display = display;
    }

    public Mount(MountDTO mountDTO){
        this.mountId = mountDTO.getID();
        this.description = mountDTO.getDescription();

        if (mountDTO.getSource() == null) {
            this.source = new SourceDTO("NO_SOURCE_AVAILABLE", "No source available").getType();
        } else {
            this.source = mountDTO.getSource().getName();
        }
        this.name = mountDTO.getName();
    }

    public Mount() {
    }



    public boolean isFieldsNotNull(){
        if(this.id == null || this.mountId == null || this.description == null
        || this.name == null || this.itemId == null || this.source == null || this.display == null){
            return false;
        }
        else {
            return true;
        }
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
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
