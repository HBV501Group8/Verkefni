package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Auth;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.AuthService;
import is.hi.hbv501GEfnahagsspa.repositories.AuthRepository;
import is.hi.hbv501GEfnahagsspa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AuthServiceImplementation implements AuthService {
    AuthRepository repository;

    @Autowired
    public AuthServiceImplementation(AuthRepository authRepository,UserRepository userRepository)
    {
        this.repository =authRepository;

    }


    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public Optional<Auth> findById(long id) {
        return Optional.empty();
    }


}
