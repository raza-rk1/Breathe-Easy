package breatheasy.airqualityindexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AirQualityIndexServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirQualityIndexServiceApplication.class, args);
	}

}
