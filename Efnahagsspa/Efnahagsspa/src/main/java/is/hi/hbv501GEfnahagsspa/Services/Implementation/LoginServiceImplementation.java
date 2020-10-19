package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Login;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.LoginService;
import is.hi.hbv501GEfnahagsspa.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImplementation implements LoginService {
    LoginRepository repository;


    @Override
    public Login save(User user) {
        return null;
    }

    @Override
    public void delete(Login login) {

    }

    @Override
    public Optional<Login> findById(long id) {
        return Optional.empty();
    }
}
