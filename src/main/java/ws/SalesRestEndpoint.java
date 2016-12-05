package ws;


import io.kristjan.webservice.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.stream.Stream;


@RestController
@RequestMapping("/rest")
public class SalesRestEndpoint extends SalesEndpoint {

  @Autowired
  public SalesRestEndpoint(PersonRepository personRepository) {
    super(personRepository);
  }

  @Override
  protected void throwDuplicateException() {
    throw new RestClientException("Duplicate request!");
  }

  @RequestMapping(value = "/salesperson", method = RequestMethod.POST)
  public AddSalesPersonResponse addSalesPerson(@RequestBody AddSalesPersonRequest request) {
    return super.addSalesPersonRequest(request);
  }

  @RequestMapping(value = "/salesperson", method = RequestMethod.GET)
  public GetSalesPersonResponse getSalesPerson(@RequestParam String apiToken,
                                               @RequestParam String requestId,
                                               @RequestParam(required = false) Integer id,
                                               @RequestParam(required = false) String employer,
                                               @RequestParam(required = false) String name) {
    GetSalesPersonRequest salesPersonRequest = new GetSalesPersonRequest();
    salesPersonRequest.setApiToken(apiToken);
    salesPersonRequest.setRequestId(requestId);
    salesPersonRequest.setId(id);
    salesPersonRequest.setEmployer(employer);
    salesPersonRequest.setName(name);
    return super.getSalesPersonResponse(salesPersonRequest);
  }

  @RequestMapping(value = "/car", method = RequestMethod.POST)
  @ApiOperation(value = "Adds a car object", notes = "NumberOfDoors must be 2,3,4 or 5")
  public AddCarResponse addCar(@RequestBody AddCarRequest request) {
    if (Stream.of(2,3,4,5).noneMatch(doors -> doors.equals(request.getCar().getNumberOfDoors()))) {
      throw new RestClientException("Number of doors not allowed");
    }
    return super.addCarRequest(request);
  }

  @RequestMapping(value = "/car", method = RequestMethod.GET)
  @ApiOperation(value = "Gets a car object", notes = "NumberOfDoors must be 2,3,4 or 5")
  public GetCarResponse getCar(@RequestParam String apiToken,
                               @RequestParam String requestId,
                               @RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String make,
                               @RequestParam(required = false) String model,
                               @RequestParam(required = false) Integer salesPersonId) {
    GetCarRequest carRequest = new GetCarRequest();
    carRequest.setApiToken(apiToken);
    carRequest.setRequestId(requestId);
    carRequest.setId(id);
    carRequest.setMake(make);
    carRequest.setModel(model);
    carRequest.setSalesPersonId(salesPersonId);
    return super.getCarResponse(carRequest);
  }
}