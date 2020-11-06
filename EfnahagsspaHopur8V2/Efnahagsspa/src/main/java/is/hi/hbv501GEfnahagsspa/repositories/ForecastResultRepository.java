package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastResultRepository extends JpaRepository<ForecastResult,Long> {


    ForecastResult save(ForecastResult forecastResult);
    void delete(ForecastResult forecastResult);
    ForecastResult findById(long id);
    ForecastResult findByForecast(Forecast forecast);
}
