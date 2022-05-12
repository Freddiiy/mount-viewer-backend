package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assets")
public class Assets {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    //Should this be changed to type Key??
    //I'll change it to Key
    //TODO: Make 1-1 relation with Key
    @OneToOne
    @MapsId
    private Key key;

    @Column(name = "value")
    private String value;

    @Column(name = "file_data_id")
    private int file_data_id;

    public Assets() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
