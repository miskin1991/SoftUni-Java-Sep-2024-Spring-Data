package bg.softuni.Entities.hospital;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;
    @Basic
    private String comments;

    public Diagnose() {}
}
