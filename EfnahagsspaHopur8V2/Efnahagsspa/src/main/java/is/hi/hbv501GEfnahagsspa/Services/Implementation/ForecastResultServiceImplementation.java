package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastResult;
import is.hi.hbv501GEfnahagsspa.Services.ForecastResultService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastResultServiceImplementation implements ForecastResultService {


    ForecastResultRepository repository;

    @Autowired
    public ForecastResultServiceImplementation(ForecastResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public ForecastResult save(ForecastResult forecastInput) {
        return repository.save(forecastInput);
    }

    @Override
    public void delete(ForecastResult forecastInput) {
        repository.delete(forecastInput);
    }

    @Override
    public ForecastResult findById(long id) {
        return repository.findById(id);
    }

    @Override
    public ForecastResult findByForecast(Forecast forecast) {
        return repository.findByForecast(forecast);
    }
}
