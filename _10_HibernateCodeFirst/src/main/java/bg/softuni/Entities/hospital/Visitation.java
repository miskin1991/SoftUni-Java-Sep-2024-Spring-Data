package bg.softuni.Entities.hospital;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Instant date;
    @Basic
    private String comments;

    @ManyToOne
    private Patient patient;

    public Visitation() {}
}
