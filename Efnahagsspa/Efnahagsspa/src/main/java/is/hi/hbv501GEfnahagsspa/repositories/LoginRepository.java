package is.hi.hbv501GEfnahagsspa.repositories;

import is.hi.hbv501GEfnahagsspa.Entities.Login;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginRepository  extends JpaRepository<Login,Long> {

    Login save(Login login);
    void delete(Login login);
    Optional<Login> findById(long id);

}
