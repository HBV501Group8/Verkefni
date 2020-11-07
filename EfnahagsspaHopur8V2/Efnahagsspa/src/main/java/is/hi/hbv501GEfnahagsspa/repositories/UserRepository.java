package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    // Stofna eða uppfæra notanda
    User save(User user);
    // Eyða notanda
    void delete(User user);
    // Finna nptanda eftir id
    User findById(long id);
    //Finna notanda eftir notandanafni
    User findByuserName(String userName);
    // Ná í alla notendur
    List<User> findAll();
    // Ná í notanda eftir lykilorði
   User findByuserPassword(String userPassword);
   List<User> findUsersByUserNameContaining(String userName);


}
