package ws;

import io.kristjan.webservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesEndpoint {
  protected PersonRepository personsRepository;

  @Autowired
  public SalesEndpoint(PersonRepository personRepository) {
    this.personsRepository = personRepository;
  }

  public AddSalesPersonResponse addSalesPersonRequest(AddSalesPersonRequest request) {
    AddSalesPersonResponse response = new AddSalesPersonResponse();
    request.getSalesperson().setId(null);
    response.setId(personsRepository.addPerson(request));
    return response;
  }

  public GetSalesPersonResponse getSalesPersonResponse(GetSalesPersonRequest request) {
    GetSalesPersonResponse response = new GetSalesPersonResponse();
    response.getSalesperson().addAll(personsRepository.findPerson(request));
    return response;
  }

  public AddCarResponse addCarRequest(AddCarRequest request) {
    AddCarResponse response = new AddCarResponse();
    request.getCar().setId(null);
    response.setId(personsRepository.addCar(request));
    return response;
  }

  public GetCarResponse getCarResponse(GetCarRequest request) {
    GetCarResponse response = new GetCarResponse();
    response.getCar().addAll(personsRepository.findCar(request));
    return response;
  }

}
