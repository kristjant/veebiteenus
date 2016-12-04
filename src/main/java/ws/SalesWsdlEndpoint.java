package ws;


import io.kristjan.webservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class SalesWsdlEndpoint extends SalesEndpoint {
  private static final String NAMESPACE_URI = "http://kristjan.io/webService";

  @Autowired
  public SalesWsdlEndpoint(PersonRepository countryRepository) {
    super(countryRepository);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addSalesPersonRequest")
  @ResponsePayload
  public AddSalesPersonResponse addSalesPersonRequest(@RequestPayload AddSalesPersonRequest request) {
    return super.addSalesPersonRequest(request);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSalesPersonRequest")
  @ResponsePayload
  public GetSalesPersonResponse getSalesPersonResponse(@RequestPayload GetSalesPersonRequest request) {
    return super.getSalesPersonResponse(request);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCarRequest")
  @ResponsePayload
  public AddCarResponse addCarRequest(@RequestPayload AddCarRequest request) {
    return super.addCarRequest(request);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarRequest")
  @ResponsePayload
  public GetCarResponse getCarResponse(@RequestPayload GetCarRequest request) {
    return super.getCarResponse(request);
  }
}