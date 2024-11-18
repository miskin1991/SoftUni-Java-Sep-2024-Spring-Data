package bg.softuni._12_springdatabookshopsystem.repositories;

import bg.softuni._12_springdatabookshopsystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
