package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;

public interface ForecastService {
    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    Forecast findByForecastName(String forecastName);
}
