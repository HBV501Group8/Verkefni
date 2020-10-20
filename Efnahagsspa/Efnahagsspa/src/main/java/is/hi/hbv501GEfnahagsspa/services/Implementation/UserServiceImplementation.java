package is.hi.hbv501GEfnahagsspa.services.Implementation;

import is.hi.hbv501GEfnahagsspa.entities.User;
import is.hi.hbv501GEfnahagsspa.services.UserService;
import is.hi.hbv501GEfnahagsspa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImplementation implements UserService {

    UserRepository repository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.repository = userRepository;
    }


    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }


    }


