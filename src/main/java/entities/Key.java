package entities;

import javax.persistence.*;

@Entity
@Table(name = "key")
public class Key {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String href;


    public Key(){
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
