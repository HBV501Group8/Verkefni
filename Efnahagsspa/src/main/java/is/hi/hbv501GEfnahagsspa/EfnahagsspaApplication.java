package is.hi.hbv501GEfnahagsspa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EfnahagsspaApplication {
/*
    EfnahagsspaApplication.java
    Class used to boot application.
*/
	public static void main(String[] args) {
		SpringApplication.run(EfnahagsspaApplication.class, args);
	}

}
	