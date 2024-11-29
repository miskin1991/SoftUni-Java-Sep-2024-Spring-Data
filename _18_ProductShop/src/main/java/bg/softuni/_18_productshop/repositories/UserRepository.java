package bg.softuni._18_productshop.repositories;

import bg.softuni._18_productshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByFirstNameAndLastName(String firstName, String lastName);

    @Query("""
        SELECT
            p.name, p.price,
            s.id, s.firstName, s.lastName, s.age
        FROM Product p
        JOIN p.seller s
    """)
    List<Object[]> getUsersWithProducts();
}
