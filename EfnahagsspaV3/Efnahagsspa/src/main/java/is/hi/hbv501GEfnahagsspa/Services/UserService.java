package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Stofna eða uppfæra notanda
    User save(User user);
    // Eyða notanda
    void delete(User user);
    // Finna notanda eftir ID
    User findById(long id);
    // Ná í alla notendur
    List<User> findAll();
    // Ná í notanda eftir notandanafni
    User findByuserName(String userName);
    // Ná í notanda eftir lykilorði
    User findByuserPassword(String userPassword);



}
