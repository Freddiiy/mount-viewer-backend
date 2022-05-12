package dtos;

public class RealmDTO {
    private String name;
    private int id;
    private String slug;

    public RealmDTO(String name, int id, String slug) {
        this.name = name;
        this.id = id;
        this.slug = slug;
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
