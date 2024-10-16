package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int identifier;

    @Column(name = "Description", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "MaxHours", nullable = false)
    private double maxHours;

    @ManyToOne
    @JoinColumn(name = "pers_aux_id") // Rename the column to match the database
    private PersAux boss;

    // Constructors, getters, and setters

    public Device(int identifier, String name, String address, double maxHours)
    {
        this.identifier=identifier;
        this.name=name;
        this.address=address;
        this.maxHours=maxHours;
    }
}
