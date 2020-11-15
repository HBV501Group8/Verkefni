package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

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
     * Leitar eftir nafni notanda
     *
     * @param userName er nafn sem á að leita eftir
     * @return Skilar notanda
     */
    User findByuserName(String userName);

    /**
     * Nær í allar spár úr gagnagrunn
     *
     * @param
     * @return Skilar lista af spám
     */
    List<User> findAll();

    /**
     * Nær í spár eftir lykilorði
     *
     * @param
     * @return Skilar notanda
     */

   User findByuserPassword(String userPassword);

    /**
     * Nær í spár eftir lykilorði
     *
     * @param
     * @return Skilar notanda
     */
   List<User> findUsersByUserNameContaining(String userName);


}
