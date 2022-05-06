package dtos;

import utils.types.Faction;

public class FactionDTO {
    private String type;
    private String name;

    public FactionDTO(Faction f){
        this.type = f.getType();
        this.name = f.getName();
    }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
