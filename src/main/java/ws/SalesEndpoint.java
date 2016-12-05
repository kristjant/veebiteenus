package ws;

import com.google.common.collect.ImmutableList;
import io.kristjan.webservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class SalesEndpoint {

  private static Map<String, List<String>> requestMap = new HashMap<>();
  private static List<String> API_TOKEN = ImmutableList.of("1a", "22b", "333");
  private PersonRepository personsRepository;

  @Autowired
  public SalesEndpoint(PersonRepository personRepository) {
    this.personsRepository = personRepository;
  }

  public AddSalesPersonResponse addSalesPersonRequest(AddSalesPersonRequest request) {
    validateRequest(request.getApiToken(), request.getRequestId());
    AddSalesPersonResponse response = new AddSalesPersonResponse();
    request.getSalesperson().setId(null);
    response.setId(personsRepository.addPerson(request));
    return response;
  }

  private void validateRequest(String apiToken, String requestId) {
    if (API_TOKEN.stream().anyMatch(e -> e.equals(apiToken))) {
      validateRequestUnique(apiToken, requestId);
    } else {
      throw new RuntimeException("API_TOKEN not found");
    }
  }

  private void validateRequestUnique(String apiToken, String requestId) {
    if (requestMap.get(apiToken) == null) {
      List<String> requests = new ArrayList<>();
      requests.add(requestId);
      requestMap.put(apiToken, requests);
    } else if (requestMap.get(apiToken).stream().anyMatch(r -> r.equals(requestId))) {
      throwDuplicateException();
    } else {
      requestMap.get(apiToken).add(requestId);
    }
  }

  protected abstract void throwDuplicateException();

  public GetSalesPersonResponse getSalesPersonResponse(GetSalesPersonRequest request) {
    validateRequest(request.getApiToken(), request.getRequestId());
    GetSalesPersonResponse response = new GetSalesPersonResponse();
    response.getSalesperson().addAll(personsRepository.findPerson(request));
    return response;
  }

  public AddCarResponse addCarRequest(AddCarRequest request) {
    validateRequest(request.getApiToken(), request.getRequestId());
    AddCarResponse response = new AddCarResponse();
    request.getCar().setId(null);
    response.setId(personsRepository.addCar(request));
    return response;
  }

  public GetCarResponse getCarResponse(GetCarRequest request) {
    validateRequest(request.getApiToken(), request.getRequestId());
    GetCarResponse response = new GetCarResponse();
    response.getCar().addAll(personsRepository.findCar(request));
    return response;
  }

}
