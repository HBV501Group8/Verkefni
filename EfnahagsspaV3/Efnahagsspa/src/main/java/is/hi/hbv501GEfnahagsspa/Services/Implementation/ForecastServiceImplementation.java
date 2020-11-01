package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForecastServiceImplementation implements ForecastService {
    ForecastRepository repository;

    @Override
    public Forecast save(Forecast foreCast) {
        return repository.save(foreCast);
    }

    @Override
    public void delete(Forecast foreCast) {
        repository.delete(foreCast);
    }

    @Override
    public Optional<Forecast> findById(long id) {
        return repository.findById(id);
    }
/*
    @Override
    public Optional<Forecast> findByforeCastName(String foreCastName) {
        return  repository.findByforeCastName(foreCastName);
    }

 */
}
