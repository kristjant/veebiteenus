package hello;

import carSales.wsdl.AddCarRequest;
import carSales.wsdl.Car;
import carSales.wsdl.GetCarRequest;
import carSales.wsdl.GetCarResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SalesClient extends WebServiceGatewaySupport {

  private static final Logger log = LoggerFactory.getLogger(SalesClient.class);

  public GetCarResponse getCars(String reqId) {
    GetCarRequest request = new GetCarRequest();
    request.setApiToken("333");
    request.setRequestId(reqId);
    return (GetCarResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8080/ws", request);
  }

  public void addCar(String reqId) {
    AddCarRequest request = new AddCarRequest();
    request.setApiToken("333");
    request.setRequestId(reqId);
    request.setSalesPersonId(0);
    Car car = new Car();
    car.setModel("Punto");
    car.setMake("Fiat");
    car.setNumberOfDoors(2);
    car.setOdometer(123);
    car.setPrice(3333);
    car.setVin("12344");
    request.setCar(car);
    getWebServiceTemplate().marshalSendAndReceive("http://localhost:8080/ws", request);
  }

  public void printResponse(GetCarResponse response) {
    response.getCar().forEach(car -> log.info(ReflectionToStringBuilder.toString(car)));
  }

}
