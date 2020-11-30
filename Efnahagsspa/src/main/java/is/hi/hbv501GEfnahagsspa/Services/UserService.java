package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.User;

import java.util.List;

public interface UserService {
/*
    UserService.java
    Interface used to specify JPA layer interactions for User entities.
*/

    // Implementation of all methods handled by UserServiceImplementation
    User save(User user);
    void delete(User user);
    User findById(long id);
    List<User> findAll();
    User findByUsername(String userName);
    User findByUserPassword(String userPassword);
    List<User> findUsersByUsernameContaining(String username);


}
