package dtos;

import entities.Mount;

public class BasicMountDTO {
    private long id;
    private String name;

    public BasicMountDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BasicMountDTO(Mount mount) {
        if (mount.getId() != null) {
            this.id = mount.getMountId();
            this.name = mount.getName();
        } else {
            throw new NullPointerException();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
