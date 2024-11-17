package bg.softuni.Entities.hospital;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Basic
    private String address;
    @Basic
    private String email;
    @Basic
    private Date birthday;
    @Basic
    private String picture;
    @Column(name = "medical_insurance")
    private boolean medicalInsurance;

    @OneToMany
    private Set<Visitation> visitations;

    public Patient() {}
}
