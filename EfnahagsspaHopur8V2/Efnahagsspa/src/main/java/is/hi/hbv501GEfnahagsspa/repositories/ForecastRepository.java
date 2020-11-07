package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast,Long> {


    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    Forecast findByForecastName(String forecastName);
    List<Forecast> findByForecastNameContaining(String forecastName);
}
