package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImplementation implements ForecastService {


    ForecastRepository repository;

    @Autowired
    public ForecastServiceImplementation(ForecastRepository repository) {
        this.repository = repository;
    }

    @Override
    public Forecast save(Forecast forecast) {
        return repository.save(forecast);
    }

    @Override
    public void delete(Forecast forecast) {
        repository.delete(forecast);
    }

    @Override
    public Forecast findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Forecast findByForecastName(String forecastName) {
        return repository.findByForecastName(forecastName);
    }
}
