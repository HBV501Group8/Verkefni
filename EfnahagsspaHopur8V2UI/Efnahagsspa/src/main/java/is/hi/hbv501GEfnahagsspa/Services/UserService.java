package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Vistar notanda í gagnagrunn
     *
     * @param user sem á að vista
     * @return Notandi hefur verið vistaður
     */
    User save(User user);

    /**
     * Eyðir notanda úr gagnagrunn
     *
     * @param id lykill spá sem á að eyða
     * @return spá hefur verið eydd
     */
    void delete(User user);
    /**
     * Nær í spá  spá úr gagnagrunn
     *
     * @param id lykill spá sem á að ná í
     * @return skilar spá
     */
    User findById(long id);

    /**
     * Nær í allar spár úr gagnagrunn
     *
     * @param
     * @return Skilar lista af spám
     */
    List<User> findAll();

    /**
     * Leitar eftir nafni notanda
     *
     * @param userName er nafn sem á að leita eftir
     * @return Skilar notanda
     */
    User findByuserName(String userName);

    /**
     * Leitar eftir lykilorði notanda
     *
     * @param userPassword er strengur sem á að leita eftir
     * @return Skilar notanda
     */

    User findByuserPassword(String userPassword);
    /**
     * Nær í notanda efir leitaroði
     *
     * @param
     * @return Skilar lista notanda
     */
    List<User> findUsersByUserNameContaining(String userName);


}
