package bg.softuni;

import bg.softuni.entities.User;
import bg.softuni.orm.config.MyConnector;
import bg.softuni.orm.context.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("root", "12345", "soft_uni");

        Connection connection = MyConnector.getConnection();
        EntityManager<User> em = new EntityManager<>(connection);

        em.doCreate(User.class);

        em.persist(new User("user1", "pass1", 20, LocalDate.now()));
        em.persist(new User("user2", "pass2", 20, LocalDate.now()));
        em.persist(new User("user3", "pass3", 20, LocalDate.now()));

        System.out.println("Find any first:");
        System.out.println(em.findFirst(User.class).toString());
        System.out.println("Find any first with age > 21:");
        System.out.println(em.findFirst(User.class, "WHERE age > 21").toString());

        System.out.println("Find all:");
        Iterable<User> users = em.find(User.class);
        users.forEach(System.out::println);

        System.out.println("Find all with age > 21:");
        users = em.find(User.class, "WHERE age > 21");
        users.forEach(System.out::println);

//        em.doAlter(User.class);

        em.doDelete(User.class);

        MyConnector.getConnection().close();
    }
}