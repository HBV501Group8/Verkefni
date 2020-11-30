package is.hi.hbv501GEfnahagsspa.Repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast,Long> {
/*
    ForecastRepository.java
    Interface used to specify JPA layer for Forecast entities.
*/

    // Implementation of all methods automatically handled by JPA
    Forecast save(Forecast forecast);
    void delete(Forecast forecast);
    Forecast findById(long id);
    List<Forecast> findAll();
    List<Forecast> findAllByUser(User user);
}


