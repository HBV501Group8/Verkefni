package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);
    void delete(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    User findByuserName(String userName);
    User findByuserPassword(String userPassword);

}
