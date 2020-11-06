package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastInput;

public interface ForecastInputService {
    ForecastInput save(ForecastInput forecastInput);
    void delete(ForecastInput forecastInput);
    ForecastInput findById(long id);
    ForecastInput findByForecast(Forecast forecast);
}
