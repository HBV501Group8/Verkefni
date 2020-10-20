package is.hi.hbv501GEfnahagsspa.services;

import is.hi.hbv501GEfnahagsspa.entities.User;

import java.util.Optional;

public interface UserService {

    User save(User user);
    void delete(User user);
    Optional<User> findById(long id);

}
