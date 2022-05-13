package dtos;

import utils.types.Source;

//Delete if Source is unnecessary
public class SourceDTO {
    private String type;
    private String name;

    public SourceDTO(String type, String name){
        this.type = type;
        this.name = name;
    }

    public SourceDTO() {
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
