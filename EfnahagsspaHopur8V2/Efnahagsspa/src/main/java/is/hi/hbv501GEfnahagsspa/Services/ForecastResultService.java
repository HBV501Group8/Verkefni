package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastResult;

public interface ForecastResultService {
    ForecastResult save(ForecastResult forecastResult);
    void delete(ForecastResult forecastResult);
    ForecastResult findById(long id);
    ForecastResult findByForecast(Forecast forecast);
}
