package dtos;

import utils.types.Assets;

public class AssetsDTO {
    private String key;
    private String value;
    private int file_data_id;

    public AssetsDTO(Assets a){
        this.key = a.getKey();
        this.value = a.getValue();
        this.file_data_id = a.getFile_data_id();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getFile_data_id() {
        return file_data_id;
    }
}
