package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Auth;
//import is.hi.hbv501GEfnahagsspa.Entities.Login;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.Optional;

public interface AuthService {

    void save(User user);
    void delete(User user);
    Optional<Auth> findById(long id);


}
