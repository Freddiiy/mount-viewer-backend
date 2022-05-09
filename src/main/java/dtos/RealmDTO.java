package dtos;

public class RealmDTO {
    private KeyDTO key;
    private String name;
    private int id;
    private String slug;

    public RealmDTO(KeyDTO key, String name, int id, String slug) {
        this.key = key;
        this.name = name;
        this.id = id;
        this.slug = slug;
    }

    public RealmDTO(String name, int id, String slug) {
        this.name = name;
        this.id = id;
        this.slug = slug;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
