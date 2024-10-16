package monotoringSystemVersion2.monotoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("monotoringSystemVersion2.monotoring.DTOO")
@ComponentScan("monotoringSystemVersion2.monotoring")
@ComponentScan("monotoringSystemVersion2.monotoring.entity")
@ComponentScan("monotoringSystemVersion2.monotoring.Service")
@ComponentScan("monotoringSystemVersion2.monotoring.configs")

public class MonotoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonotoringApplication.class, args);
	}

}
