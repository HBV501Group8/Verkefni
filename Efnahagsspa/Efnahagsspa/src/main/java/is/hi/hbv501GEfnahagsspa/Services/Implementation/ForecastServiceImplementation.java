package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.ForeCast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForecastServiceImplementation implements ForecastService {
    ForecastRepository repository;

    @Override
    public ForeCast save(ForeCast foreCast) {
        return null;
    }

    @Override
    public void delete(ForeCast foreCast) {

    }

    @Override
    public Optional<ForeCast> findById(long id) {
        return Optional.empty();
    }
}
