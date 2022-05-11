package dtos;

public class CharacterElementDTO {
    private KeyDTO key;
    private String name;
    private int id;

    public CharacterElementDTO(KeyDTO key, String name, int id) {
        this.key = key;
        this.name = name;
        this.id = id;
    }
    
    public KeyDTO getKey() {
        return key;
    }

    public void setKey(KeyDTO key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
