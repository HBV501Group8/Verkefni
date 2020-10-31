package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast,Long> {


    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Optional<Forecast> findById(long id);
    Optional<Forecast> findByforecastName(String forecastName);
}
