package bg.softuni;

import bg.softuni.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure();

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();

        Student student = new Student();
        student.setName("John");
        currentSession.persist(student);

        student = currentSession.get(Student.class, 1);
        System.out.println(student);

        List<Student> fromStudent = currentSession.createQuery("FROM Student", Student.class).list();

        fromStudent.forEach(System.out::println);

        currentSession.getTransaction().commit();
        currentSession.close();
    }
}