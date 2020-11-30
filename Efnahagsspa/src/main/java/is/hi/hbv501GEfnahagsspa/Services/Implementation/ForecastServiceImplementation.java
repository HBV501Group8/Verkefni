package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastServiceImplementation implements ForecastService {
/*
    ForecastService.java
    Class used to specify JPA layer interactions for Forecast entities.
*/

    ForecastRepository repository;

    @Autowired
    public ForecastServiceImplementation(ForecastRepository repository) {
        this.repository = repository;
    }


/*
    save
        Use: ForecastService.save(forecast)"
        Parameters: forecast, a Forecast object
        Returns: forecast has been saved to the database
*/
    @Override
    public Forecast save(Forecast forecast) {return repository.save(forecast); }

/*
    delete
        Use: ForecastService.delete(forecast)
        Parameters: forecast, a Forecast object
        Returns: forecast has been deleted from the database
*/
    @Override
    public void delete(Forecast forecast) { repository.delete(forecast); }

/*
        findById
            Use: ForecastService.findById(id)
            Parameters: id, unique id used by database to identify forecast
            Returns: Forecast object which is stored by unique id in database
*/
    @Override
    public Forecast findById(long id) { return repository.findById(id); }

/*
        findALl
            Use: ForecastService.findALl()
            Parameters: None
            Returns: A list of all Forecast objects stored within database
*/
    @Override
    public List<Forecast> findAll() { return repository.findAll(); }

/*
    findAllByUser
        Use: ForecastService.findAllByUser(user)
        Parameters: user, a User object
        Returns: A list of all Forecast objects in database that have a One-to-Many
                 relationship with user.
*/
    @Override
    public List<Forecast> findAllByUser(User user) { return repository.findAllByUser(user); }


}
