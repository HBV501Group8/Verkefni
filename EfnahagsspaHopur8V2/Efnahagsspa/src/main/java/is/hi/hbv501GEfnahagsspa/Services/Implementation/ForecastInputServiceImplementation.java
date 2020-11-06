package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastInput;
import is.hi.hbv501GEfnahagsspa.Services.ForecastInputService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastInputServiceImplementation implements ForecastInputService {


    ForecastInputRepository repository;

    @Autowired
    public ForecastInputServiceImplementation(ForecastInputRepository repository) {
        this.repository = repository;
    }

    @Override
    public ForecastInput save(ForecastInput forecastInput) {
        return repository.save(forecastInput);
    }

    @Override
    public void delete(ForecastInput forecastInput) {
        repository.delete(forecastInput);
    }

    @Override
    public ForecastInput findById(long id) {
        return repository.findById(id);
    }

    @Override
    public ForecastInput findByForecast(Forecast forecast) {
        return repository.findByForecast(forecast);
    }
}
