package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

public interface VolcanoRepository extends JpaRepository<Volcano, Long> {

    Optional<Volcano> findVolcanoByName(String name);

    List<Volcano> findAllByElevationAfterAndIsActiveAndLastEruptionIsNotNullOrderByElevationDesc(
            int elevation, boolean isActive);
}
