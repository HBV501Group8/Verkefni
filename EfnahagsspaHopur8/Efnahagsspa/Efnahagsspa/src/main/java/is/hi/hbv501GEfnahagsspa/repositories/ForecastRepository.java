package is.hi.hbv501GEfnahagsspa.repositories;


import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast,Long> {

    //Stofna eða uppfæra spálíkan
    Forecast save(Forecast foreCast);
    //Eyða spálíkan
    void delete(Forecast foreCast);
    Optional<Forecast> findById(long id);
    Optional<Forecast> findByforeCastName(String foreCastName);
}
