package dtos;

import entities.Mount;
import utils.types.MountElement;

public class MountElementDTO {
    private String name;
    private long id;

    public MountElementDTO(Mount m){
        this.name  = m.getName();

        this.id = m.getMountId();
    }

    public MountElementDTO(MountElement mountElement) {
        this.name = mountElement.getMount().getName();
        this.id = mountElement.getMount().getID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
