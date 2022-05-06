package dtos;

import utils.types.MountMount;

public class MountMountDTO {
    private SelfDTO key;
    private String name;
    private long id;

    public MountMountDTO(MountMount mm){
        if(mm.getID() != 0)
            this.id = mm.getID();

        this.key = new SelfDTO(mm.getKey());
        this.name = mm.getName();
    }

    public SelfDTO getKey() { return key; }
    public void setKey(SelfDTO value) { this.key = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }
}
