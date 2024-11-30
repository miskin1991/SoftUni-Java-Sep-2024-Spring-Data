package softuni.exam.repository;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Attraction;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    Optional<Attraction> findAttractionByName(@Length(min = 5, max = 40) String name);

    List<Attraction>
        findByTypeContainsIgnoreCaseOrTypeContainsIgnoreCaseAndElevationGreaterThanEqualOrderByNameAsc(
            String type1, String type2, double elevation);
}
