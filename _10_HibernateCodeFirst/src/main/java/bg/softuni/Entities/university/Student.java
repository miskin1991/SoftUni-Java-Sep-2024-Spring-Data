package bg.softuni.Entities.university;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {

    @Column(name="average_grade")
    private double averageGrade;
    @Basic
    private int attendance;

    @ManyToMany(mappedBy = "students", targetEntity = Course.class)
    private Set<Course> courses;

    public Student() {
        this.courses = new HashSet<>();
    }
}
