package ManagementComunication.communicationCSV;

import ManagementComunication.communicationCSV.Service.RandomCSVReader;
import ManagementComunication.communicationCSV.Service.ReadingCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("ManagementComunication.communicationCSV.Service")
@ComponentScan("ManagementComunication.communicationCSV.configs")
public class MicroserviceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MicroserviceApplication.class, args);

	}

}
