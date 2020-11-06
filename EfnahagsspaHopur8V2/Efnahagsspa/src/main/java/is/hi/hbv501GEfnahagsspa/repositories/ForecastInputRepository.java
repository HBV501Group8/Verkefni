package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastInput;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastInputRepository extends JpaRepository<ForecastInput,Long> {


    ForecastInput save(ForecastInput forecastInput);
    void delete(ForecastInput forecastInput);
    ForecastInput findById(long id);
    ForecastInput findByForecast(Forecast forecast);
}
