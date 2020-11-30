package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;

public interface ForecastService {
/*
    ForecastService.java
    Interface used to specify JPA layer interactions for Forecast entities.
*/

    // Implementation of all methods handled by UserServiceImplementation
    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    List<Forecast> findAll();
    List<Forecast> findAllByUser(User user);
}
