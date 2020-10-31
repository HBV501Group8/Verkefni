package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.ForeCast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastRepository extends JpaRepository<ForeCast,Long> {


    ForeCast save(ForeCast foreCast);
    void delete(ForeCast foreCast);
    Optional<ForeCast> findById(long id);
    Optional<ForeCast> findByforeCastName(String foreCastName);
}
