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



    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.repository = userRepository;
    }

    /**
     * @param user hlutur af taginu User
     * @return kall á fallið save úr userRepository með user
     */

    @Override

    public User save(User user) {
        return repository.save(user);
    }

    /**
     * @param user hlutur af taginu User
     * @return kall á fallið eyða úr userRepository með user
     */

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    /**
     * @param user hlutur af taginu User
     * @param id sem er lykill notnada
     * @return kall á fallið findById
     */


    @Override
    public User findById(long id) {
        return repository.findById(id);
    }

    /**
     * @param user listi af hlutum af taginu User
     * @return kall á fallið findAll()
     */

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * @param user hlutur af taginu User
     * @param userName er nafn notada
     * @return kall á fallið findByUSerName();
     */


    @Override
    public User findByuserName(String userName) {
        return repository.findByuserName(userName);
    }

    /**
     * @param user hlutur af taginu User
     * @param userPassword er lykilorð notada
     * @return kall á fallið findByUSerPassword();
     */


    @Override
    public User findByuserPassword(String userPassword) {
        return repository.findByuserPassword(userPassword);

    }

    /**
     * @param user hlutur af taginu User
     * @param userName er notandanafn
     * @return kall á fallið findUsersByUserNameContaining(userName);
     */


    @Override
    public List<User> findUsersByUserNameContaining(String userName) {
        return  repository.findUsersByUserNameContaining(userName);
    }


}


