package bg.softuni._18_productshop.repositories;

import bg.softuni._18_productshop.entities.Category;
import bg.softuni._18_productshop.entities.interfaces.CategoriesMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
        SELECT
            c.name AS category,
            COUNT(c.id) AS productsCount,
            AVG(p.price) AS averagePrice,
            SUM(p.price) AS totalRevenue
        FROM Product p
        JOIN p.categories c
        GROUP BY c.name
    """)
    List<CategoriesMetrics> getAllCategoriesMetrics();
}
