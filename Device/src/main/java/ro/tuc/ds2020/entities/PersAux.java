package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersAux implements Serializable {

    @Id
  //  @Type(type = "uuid-binary")
    @Column(columnDefinition = "UUID")
    private UUID id;

    // Other fields and annotations

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    // Constructors, getters, and setters

    public PersAux(UUID id)
    {
        this.id=id;
    }
        
}
