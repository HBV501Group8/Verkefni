package is.hi.hbv501GEfnahagsspa.Repositories;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
/*
    UserRepository.java
    Interface used to specify JPA layer for User entities.
*/

    // Implementation of all methods automatically handled by JPA
    User save(User user);
    void delete(User user);
    User findById(long id);
    User findByUsername(String userName);
    List<User> findAll();
    User findByUserPassword(String userPassword);
    List<User> findUsersByUsernameContaining(String userName);
}
