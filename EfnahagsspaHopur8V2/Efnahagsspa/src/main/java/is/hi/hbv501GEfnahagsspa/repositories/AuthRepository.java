package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User,Long> {

    User save(User user);
    void delete(User user);
    Optional<User> findById(long id);
    User findByuserName(String userName);
    User findByuserPassword(String userPassword);

}
