package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.Auth;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.AuthService;
import is.hi.hbv501GEfnahagsspa.repositories.AuthRepository;

import java.util.Optional;

public class AuthServiceImplementation implements AuthService {
    AuthRepository repository;
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

    @Override
    public Optional<User> findByuserName(String userName) {
        return repository.findByuserName(userName);
    }

    @Override
    public Optional<User> findByuserPassword(String password) {
        return repository.findByuserPassword(password);
    }


}
