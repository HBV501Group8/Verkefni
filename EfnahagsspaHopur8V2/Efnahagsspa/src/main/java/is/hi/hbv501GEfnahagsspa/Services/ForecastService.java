package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;
import java.util.Optional;

public interface ForecastService {
    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    List<Forecast> findAll();
    Forecast findByforecastName(String forecastName);
}
