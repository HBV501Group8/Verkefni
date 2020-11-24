package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;

public interface ForecastService {
    /**
     * Vistar spá í gagnagrunn
     *
     * @param forecast  sem á að vista
     * @return Vistar forecast
     */
    Forecast save(Forecast forecast);

    /**
     * Eyðir spá úr gagnagrunn
     *
     * @param ID lykill spá sem á að eyða
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
    /**
     * Nær í allar spár úr gagnagrunn
     *
     * @param
     * @return Skilar lista af spám
     */
    List<Forecast> findAll();

    List<Forecast> findAllByUser(User user);
}
