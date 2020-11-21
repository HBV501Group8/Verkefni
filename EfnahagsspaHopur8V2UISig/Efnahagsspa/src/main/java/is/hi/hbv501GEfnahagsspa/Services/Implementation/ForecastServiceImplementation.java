package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastServiceImplementation implements ForecastService {


    ForecastRepository repository;

    @Autowired
    public ForecastServiceImplementation(ForecastRepository repository) {
        this.repository = repository;
    }
    /**
     * @param user hlutur af taginu User
     * @return kall á fallið save úr userRepository með user
     */

    @Override
    public Forecast save(Forecast forecast) {
        return repository.save(forecast);
    }
    /**
     * @param user hlutur af taginu User
     * @return kall á fallið eyða úr userRepository með user
     */

    @Override
    public void delete(Forecast forecast) {
        repository.delete(forecast);
    }
    /**
     * @param user hlutur af taginu User
     * @param id sem er lykill notnada
     * @return kall á fallið findById
     */

    @Override
    public Forecast findById(long id) {
        return repository.findById(id);
    }

    /**
     * @param user listi af hlutum af taginu User
     * @return kall á fallið findAll()
     */
    @Override
    public Forecast findByForecastName(String forecastName) {
        return repository.findByForecastName(forecastName);
    }
    /**
     * @param user hlutur af taginu User
     * @param userName er nafn notada
     * @return kall á fallið findByUSerName();
     */
    @Override
    public List<Forecast> findAll() {
        return repository.findAll();
    }
}
