package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import is.hi.hbv501GEfnahagsspa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForecastServiceImplementation implements ForecastService {

    @Autowired
    ForecastRepository repository;

    public ForecastServiceImplementation(ForecastRepository ForecastRepository) {
        this.repository = ForecastRepository;
    }

    @Override
    public Forecast save(Forecast foreCast) {
        return repository.save(foreCast);
    }

    @Override
    public void delete(Forecast foreCast) {
        repository.delete(foreCast);
    }

    @Override
    public Forecast findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Forecast findByforecastName(String forecastName) {
        return  repository.findByforecastName(forecastName);
    }
}
