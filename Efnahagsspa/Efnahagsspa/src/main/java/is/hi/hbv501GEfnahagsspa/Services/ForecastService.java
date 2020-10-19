package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.ForeCast;

import java.util.Optional;

public interface ForecastService {
    ForeCast save(ForeCast foreCast);
    void delete(ForeCast foreCast);
    Optional<ForeCast> findById(long id);
}
