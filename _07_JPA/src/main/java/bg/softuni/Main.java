package bg.softuni;

import bg.softuni.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school-unit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = new Student();
        student.setName("Sara");
        em.persist(student);

        List<Student> fromStudent = em.createQuery("FROM Student", Student.class).getResultList();

        fromStudent.forEach(System.out::println);

        em.getTransaction().commit();
    }
}