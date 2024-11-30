package softuni.exam.repository;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Visitor;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
//    Optional<Visitor> findByFirstName(@Length(min = 2, max = 20) String firstName);
//
//    Optional<Visitor> findByLastName(@Length(min = 2, max = 20) String lastName);

    Optional<Visitor> findVisitorByPersonalDataId(long personalData);

    Optional<Visitor> findVisitorByFirstNameAndLastName(
            @Length(min = 2, max = 20) String firstName, @Length(min = 2, max = 20) String lastName);
}
