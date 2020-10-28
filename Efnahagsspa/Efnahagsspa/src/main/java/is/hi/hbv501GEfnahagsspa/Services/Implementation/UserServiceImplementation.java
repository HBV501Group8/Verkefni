package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import is.hi.hbv501GEfnahagsspa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImplementation implements UserService {

    UserRepository repository;

    // Binda repository í smið

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.repository = userRepository;
    }

    // Stofna eða uppfæra notanda
    @Override
    public User save(User user) {
        return repository.save(user);
    }
    // Eyða notanda
    @Override
    public void delete(User user) {
        repository.delete(user);
    }
    // Finna notanda eftir id
    @Override
    public User findById(long id) {
        return repository.findById(id);
    }
    // Ná í alla notendur
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    // Ná í notanda eftir notandanafni
    @Override
    public User findByuserName(String userName) {
        return repository.findByuserName(userName);
    }
    // Ná í notanda eftir lykilorði
    @Override
    public User findByuserPassword(String userPassword) {
        return repository.findByuserPassword(userPassword);

    }


}


