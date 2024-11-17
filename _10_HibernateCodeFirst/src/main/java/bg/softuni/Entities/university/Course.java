package bg.softuni.Entities.university;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;
    @Basic
    private String description;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Basic
    private int credits;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private Set<Student> students;

    public Course() {}
}
