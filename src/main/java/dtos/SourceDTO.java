package dtos;

import utils.types.Source;

//Delete if Source is unnecessary
public class SourceDTO {
    public class SourceDTONotAvailable {
        public static final String TYPE = "SOUCE_NOT_AVAILABLE";
        public static final String NAME = "Source not available";
    }
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
