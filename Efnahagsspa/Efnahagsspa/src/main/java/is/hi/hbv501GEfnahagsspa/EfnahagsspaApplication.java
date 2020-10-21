package is.hi.hbv501GEfnahagsspa;

import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastBuilder;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories
public class EfnahagsspaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfnahagsspaApplication.class, args);
	}
}
	