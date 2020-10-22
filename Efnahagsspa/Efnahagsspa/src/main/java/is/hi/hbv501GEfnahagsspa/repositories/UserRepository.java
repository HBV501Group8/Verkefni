package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User save(User user);
    void delete(User user);
    Optional<User> findById(long id);
    User findByUserName(String userName);
    //User findByUs(String userEmail);
    List<User> findAll();



}
