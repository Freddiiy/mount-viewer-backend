package dtos;

import utils.types.Source;

//Delete if Source is unnecessary
public class SourceDTO {
    private String name;
    private String description;

    public SourceDTO(Source s){
        this.name = s.getName();
        this.description = s.getDescription();
    }
}
