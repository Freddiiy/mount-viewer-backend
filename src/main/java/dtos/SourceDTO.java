package dtos;

import utils.types.Source;

//Delete if Source is unnecessary
public class SourceDTO {
    private String type;
    private String name;

    public SourceDTO(Source s){
        this.type = s.getType();
        this.name = s.getName();
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
