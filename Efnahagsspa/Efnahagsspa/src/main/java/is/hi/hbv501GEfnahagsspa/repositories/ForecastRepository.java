package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.ForeCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForecastRepository extends JpaRepository<ForeCast,Long> {


    ForeCast save(ForeCast foreCast);
    void delete(ForeCast foreCast);
    Optional<ForeCast> findById(long id);
    List<ForeCast> findAll();
    ForeCast findByForeCastName(String foreCastName);
    ForeCast getForeCast();
}
