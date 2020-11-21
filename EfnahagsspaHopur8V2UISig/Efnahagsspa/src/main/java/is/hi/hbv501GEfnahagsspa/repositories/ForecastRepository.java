package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast,Long> {

    /**
     * Vistar spá í gagnagrunn
     *
     * @param foreCast  sem á að vista
     * @return Vistar forecast
     */
    Forecast save(Forecast forecast);
    /**
     * Eyðir spá úr gagnagrunn
     *
     * @param id lykill spá sem á að eyða
     * @return spá hefur verið eydd
     */

    void delete(Forecast forecast);
    /**
     * Nær í spá  spá úr gagnagrunn
     *
     * @param id lykill spá sem á að ná í
     * @return skilar spá
     */

    Forecast findById(long id);

    /**
     * Leitar eftir nafni spáar
     *
     * @param forecastName er nafn sem á að leita eftir
     * @return Skilar spá
     */

    Forecast findByForecastName(String forecastName);
    /**
     * Nær í allar spár úr gagnagrunn
     *
     * @param
     * @return Skilar lista af spám
     */

    List<Forecast> findAll();
}
