package umc.spring.ringleader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication

public class RingleaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RingleaderApplication.class, args);
	}

}
