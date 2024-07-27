package breatheasy.airqualityindexservice.repository;

import breatheasy.airqualityindexservice.model.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    Optional<Coordinate> findByCountryAndStateAndName(String country, String state, String name);
}
