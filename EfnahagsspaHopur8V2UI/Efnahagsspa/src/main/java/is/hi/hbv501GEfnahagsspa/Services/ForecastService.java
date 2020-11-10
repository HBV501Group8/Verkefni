package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;

public interface ForecastService {
    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    Forecast findByForecastName(String forecastName);
    List<Forecast> findByForecastNameContaining(String forecastName);
    List<Forecast> findAll();

}
