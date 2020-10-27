package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.ForeCast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImplementation implements ForecastService {
    ForecastRepository repository;

    @Autowired
    public ForecastServiceImplementation(ForecastRepository forecastRepository){
        this.repository = forecastRepository;
    }

    @Override
    public ForeCast save(ForeCast foreCast) {
        return null;
    }

    @Override
    public void delete(ForeCast foreCast) {
        repository.delete(foreCast);
    }

    @Override
    public Optional<ForeCast> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<ForeCast> findAll(){
        return repository.findAll();
    }

    @Override
    public ForeCast findByForeCastName(String foreCastName) {
        return repository.findByForeCastName(foreCastName);
    }

    @Override
    public ForeCast getForeCast() {
        return repository.getForeCast();
    }


}



