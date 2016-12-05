package hello;

import carSales.wsdl.GetCarResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//Created using https://spring.io/guides/gs/consuming-web-service/ as an example
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  CommandLineRunner run(SalesClient salesClient) {
    return args -> {
      GetCarResponse response = salesClient.getCars("asdasdv");
      salesClient.printResponse(response);

      salesClient.addCar("ffffff");

      GetCarResponse response2 = salesClient.getCars("asdasdv1");
      salesClient.printResponse(response2);
    };
  }

}
