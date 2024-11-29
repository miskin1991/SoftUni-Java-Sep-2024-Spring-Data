package bg.softuni._18_productshop.repositories;

import bg.softuni._18_productshop.entities.Product;
import bg.softuni._18_productshop.entities.interfaces.SellerIdByProductSoldCount;
import bg.softuni._18_productshop.entities.interfaces.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("FROM Product WHERE buyer IS null AND price BETWEEN :lower AND :upper")
    List<Product> getNotSoldProductWithPriceBetween(BigDecimal lower, BigDecimal upper);

    @Query("""
        SELECT p.name AS name, p.price AS price,
        p.buyer.firstName as buyerFirstName, p.buyer.lastName as buyerLastName
        FROM Product p
        WHERE p.seller.id = :id
    """)
    List<SoldProduct> findAllBySellerId(Long id);

    @Query("""
        SELECT COUNT(p.id) AS count, p.seller.id AS sellerId
        FROM Product p
        GROUP BY p.seller.id
        HAVING COUNT(p.id) > :count
    """)
    List<SellerIdByProductSoldCount> findSellerIdByProductSoldCounts(long count);
}
