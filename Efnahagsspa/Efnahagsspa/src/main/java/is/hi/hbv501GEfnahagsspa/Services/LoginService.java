package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.Login;
import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.Optional;

public interface LoginService {
    Login save(User user);
    void delete(Login login);
    Optional<Login> findById(long id);
}
