package bg.softuni;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate_code_first");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();




        entityManager.getTransaction().commit();
        entityManager.close();

    }
}