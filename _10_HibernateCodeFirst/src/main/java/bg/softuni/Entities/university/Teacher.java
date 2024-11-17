package bg.softuni.Entities.university;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Basic
    private String email;
    @Column(name = "hourly_rate")
    private double hourlyRate;

    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
    private Set<Course> courses;

    public Teacher() {
        this.courses = new HashSet<>();
    }
}
