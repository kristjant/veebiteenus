package ws;

import io.kristjan.webservice.*;

public class SalesEndpoint {
  protected PersonRepository personsRepository;

  public SalesEndpoint(PersonRepository countryRepository) {
    this.personsRepository = countryRepository;
  }

  public AddSalesPersonResponse addSalesPersonRequest(AddSalesPersonRequest request) {
    AddSalesPersonResponse response = new AddSalesPersonResponse();
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
    response.setId(personsRepository.addCar(request));
    return response;
  }

  public GetCarResponse getCarResponse(GetCarRequest request) {
    GetCarResponse response = new GetCarResponse();
    response.getCar().addAll(personsRepository.findCar(request));
    return response;
  }

}
