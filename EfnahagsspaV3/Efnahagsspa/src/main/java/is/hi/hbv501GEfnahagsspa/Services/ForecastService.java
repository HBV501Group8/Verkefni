package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;

import java.util.Optional;

public interface ForecastService {
    Forecast save(Forecast foreCast);
    void delete(Forecast foreCast);
    Optional<Forecast> findById(long id);
    //Optional<Forecast> findByforeCastName(String foreCastName);
}
