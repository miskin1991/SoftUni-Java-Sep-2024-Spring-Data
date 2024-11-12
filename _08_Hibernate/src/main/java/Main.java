import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        townNameToUpperCase(em);
        containsEmployee(em);
        employeesSalary(em);
        employeesFromDepartment(em);
        addingNewAddressAndUpdateEmployee(em);
        addressWithEmployeeCount(em);
        getEmployeesWithProject(em);
        findLatestProjects(em);
        increaseSalaries(em);
        findEmployeesByFirstName(em);
        findMaximumSalaryByDepartment(em);
        removeTowns(em);



        em.getTransaction().commit();
    }

    private static void removeTowns(EntityManager em) throws IOException {
        String townName = READER.readLine();
        em.createQuery("FROM Employee WHERE address.town.name = :townName", Employee.class)
                .setParameter("townName", townName)
                .getResultStream()
                .forEach(e -> {
                    e.setAddress(null);
                    em.persist(e);
                });

        List<Address> addresses = em.createQuery("FROM Address WHERE town.name = :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();
        addresses.forEach(em::remove);

        Town town = em.createQuery("FROM Town WHERE name = :townName", Town.class)
                        .setParameter("townName", townName)
                        .getSingleResult();
        em.remove(town);


        System.out.printf("%d addresses in %s deleted%n", addresses.size(), townName);
    }

    private static void findMaximumSalaryByDepartment(EntityManager em) {
        em.createQuery("SELECT d.name, MAX(e.salary) FROM Department d " +
                "JOIN d.employees e GROUP BY d.name HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000")
                .getResultList()
                .forEach(result -> {
                    Object[] row = (Object[]) result;
                    System.out.printf("%s %.2f%n", row[0], row[1]);
                });
    }

    private static void findEmployeesByFirstName(EntityManager em) throws IOException {
        String name = READER.readLine();
        em.createQuery("FROM Employee e WHERE e.firstName like :firstName", Employee.class)
                .setParameter("firstName", name + "%")
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
                            e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary());
                });
    }

    private static void increaseSalaries(EntityManager em) {
        /// prints same output as in the document
        em.createQuery("FROM Employee WHERE department.name IN " +
                "('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    em.persist(e);
                    System.out.printf("%s %s ($%.2f)%n",
                            e.getFirstName(), e.getLastName(), e.getSalary());
                });
//        List<Employee> employees = em.createQuery("FROM Employee", Employee.class)
//                .getResultStream()
//                .filter(e ->
//                        e.getDepartment().getName().equals("Engineering")
//                                || e.getDepartment().getName().equals("Tool Design")
//                                || e.getDepartment().getName().equals("Marketing")
//                                || e.getDepartment().getName().equals("Information Services")
//                )
//                .collect(Collectors.toList());
//
//        employees.forEach(e -> {
//            e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
//            em.persist(e);
//            System.out.printf("%s %s ($%.2f)%n",
//                    e.getFirstName(), e.getLastName(), e.getSalary());
//        });
    }

    private static void findLatestProjects(EntityManager em) {
        // prints out same output as in the document
        em.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.println("Project name: " + p.getName());
                    System.out.println("\tProject description: " + p.getDescription());
                    System.out.println("\tProject startDate: " + p.getStartDate());
                    System.out.println("\tProject endDate: " + p.getEndDate());
                });
//        em.createQuery("FROM Project ORDER BY startDate DESC, name", Project.class)
//                .setMaxResults(10)
//                .getResultStream()
//                .forEach(p -> {
//                    System.out.println("Project name: " + p.getName());
//                    System.out.println("\tProject description: " + p.getDescription());
//                    System.out.println("\tProject startDate: " + p.getStartDate());
//                    System.out.println("\tProject endDate: " + p.getEndDate());
//                });
    }

    private static void getEmployeesWithProject(EntityManager em) throws IOException {
        Employee employee = em.find(Employee.class, Integer.parseInt(READER.readLine()));

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("\t%s%n", p.getName()));
    }

    private static void addressWithEmployeeCount(EntityManager em) {
        em
                .createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a ->
                System.out.printf("%s, %s - %d employees%n",
                        a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private static void addingNewAddressAndUpdateEmployee(EntityManager em) throws IOException {
        Address address = new Address();
        address.setText("Vitoshka 15");
        em.persist(address);

        String lastName = READER.readLine();
        Query query = em.createQuery("UPDATE Employee e SET e.address = :address WHERE e.lastName = :lastName");
        query.setParameter("address", address);
        query.setParameter("lastName", lastName);
        int i = query.executeUpdate();
        if (i > 0) {
            System.out.println("Update successful");
        } else {
            System.out.println("Noting to update");
        }
    }

    private static void employeesFromDepartment(EntityManager em) {
        em.createQuery(
                "FROM Employee WHERE department.name = 'Research and Development' " +
                    "ORDER BY salary, id", Employee.class)
                .getResultStream()
                .forEach(e ->
                    System.out.printf("%s %s from %s - $%.2f%n",
                            e.getFirstName(), e.getLastName(),
                            e.getDepartment().getName(), e.getSalary()));

        System.out.println();
    }

    private static void employeesSalary(EntityManager em) {
        List<Employee> employees =
                em.createQuery("FROM Employee WHERE salary > 50000", Employee.class).getResultList();

        employees.forEach(e -> System.out.println(e.getFirstName()));
    }

    private static void containsEmployee(EntityManager em) throws IOException {
        String[] fullName = READER.readLine().split(" ");

        Query query = em.createQuery(
                "FROM Employee WHERE firstName = :firstName AND lastName = :lastName",
                    Employee.class);
        query.setParameter("firstName", fullName[0]);
        query.setParameter("lastName", fullName[1]);
        List<Employee> employees = query.getResultList();

        if (employees.isEmpty()) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }

    private static void townNameToUpperCase(EntityManager em) {
        List<Town> towns =
                em.createQuery("FROM Town WHERE length(name) > 5", Town.class).getResultList();

        towns.forEach(town -> {
            town.setName(town.getName().toUpperCase());
            em.persist(town);
        });
    }
}
