package is.hi.hbv501GEfnahagsspa.Services.Implementation;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import is.hi.hbv501GEfnahagsspa.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
/*
    UserServiceImplementation.java
    Class used to specify JPA layer interactions for User entities.
*/

    UserRepository repository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.repository = userRepository;
    }

/*
    save
        Use: UserService.save(user)"
        Parameters: user, a User object
        Returns: user has been saved to the database
*/
    @Override
    public User save(User user) { return repository.save(user); }

/*
    delete
        Use: UserService.delete(user)"
        Parameters: user, a User object
        Returns: user has been deleted from the database
*/
    @Override
    public void delete(User user) { repository.delete(user); }


/*
    findById
        Use: UserService.findById(id)"
        Parameters: id, unique id used by database to identify user
        Returns: User object which is stored by unique id in database
*/
    @Override
    public User findById(long id) { return repository.findById(id); }


/*
        findALl
            Use: UserService.findALl()
            Parameters: None
            Returns: A list of all User objects stored within database
*/
    @Override
    public List<User> findAll() { return repository.findAll(); }


/*
        findByUsername
            Use: UserService.findByUsername(username)
            Parameters: username, string
            Returns: User object from database where the username attribute equals username
*/
    public User findByUsername(String username) { return repository.findByUsername(username); }

/*
        findByUserPassword
            Use: UserService.findByUserPassword(userPassword)
            Parameters: userPassword, string
            Returns: User object from database where the userPassword
                     attribute equals userPassword
*/
    @Override
    public User findByUserPassword(String userPassword) {
        return repository.findByUserPassword(userPassword);
    }

/*
        findUsersByUsernameContaining
            Use: UserService.findUsersByUsernameContaining(username)
            Parameters: username, string
            Returns: User object frome database where the username attribute equals username
*/
    @Override
    public List<User> findUsersByUsernameContaining(String username) {
        return  repository.findUsersByUsernameContaining(username);
    }


}


