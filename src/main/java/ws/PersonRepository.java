package ws;

import io.kristjan.webservice.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.ws.soap.SoapBodyException;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class PersonRepository {
  private static Map<Integer, SalesPerson> salesPersonMap = new HashMap<>();

  private static AtomicInteger personId = new AtomicInteger();
  private static AtomicInteger carId = new AtomicInteger();

  public int addPerson(AddSalesPersonRequest request) {
    Integer id = personId.getAndIncrement();
    SalesPerson salesPerson = request.getSalesperson();
    salesPerson.setId(id);
    salesPersonMap.put(id, salesPerson);
    request.getSalesperson().getCars().forEach(car -> {
      car.setId(carId.getAndIncrement());
      car.setSalesPersonId(id);
    });
    return id;
  }

  public List<SalesPerson> findPerson(GetSalesPersonRequest request) {
    return salesPersonMap.entrySet().stream()
      .filter(e -> eqNotNull(request.getId(), e.getKey())
        && eqStringNotNull(request.getName(), e.getValue().getName())
        && eqStringNotNull(request.getEmployer(), e.getValue().getEmployer()))
      .map(Map.Entry::getValue)
      .collect(Collectors.toList());
  }

  private boolean eqNotNull(Object requestVal, Object mapVal) {
    return requestVal == null || requestVal.equals(mapVal);
  }

  public Integer addCar(AddCarRequest request) {
    SalesPerson salesPerson = salesPersonMap.get(request.getSalesPersonId());
    if (salesPerson == null) {
      throw new SoapBodyException("Person with id: " + request.getSalesPersonId() + " not found");
    }
    Integer id = carId.getAndIncrement();
    Car car = request.getCar();
    car.setId(id);
    car.setSalesPersonId(request.getSalesPersonId());
    salesPerson.getCars().add(car);
    return id;
  }

  public List<Car> findCar(GetCarRequest request) {
    List<Car> cars;
    if (request.getSalesPersonId() != null) {
      cars = getOneSalePersonCars(request);
    } else {
      cars = getAllCars();
    }
    return cars.stream().filter(e -> eqNotNull(request.getId(), e.getId())
      && eqStringNotNull(request.getMake(), e.getMake())
      && eqStringNotNull(request.getModel(), e.getModel()))
      .collect(Collectors.toList());
  }

  private boolean eqStringNotNull(String req, String mapVal) {
    return StringUtils.isEmpty(req) || req.equals(mapVal);
  }

  private List<Car> getOneSalePersonCars(GetCarRequest request) {
    Optional<SalesPerson> salesPerson = salesPersonMap.entrySet().stream()
      .filter(e -> request.getSalesPersonId().equals(e.getKey()))
      .map(Map.Entry::getValue)
      .findFirst();
    if (!salesPerson.isPresent()) {
      return new ArrayList<>();
    } else {
      return salesPerson.get().getCars();
    }
  }

  private List<Car> getAllCars() {
    List<Car> cars;
    cars = salesPersonMap.entrySet().stream()
      .map(Map.Entry::getValue)
      .flatMap(person -> person.getCars().stream())
      .collect(Collectors.toList());
    return cars;
  }


  @PostConstruct
  private void initData() {
    Car car = new Car();
    car.setId(carId.getAndIncrement());
    car.setMake("Ford");
    car.setModel("Mondeo ST");
    car.setNumberOfDoors(3);
    car.setOdometer(12345);
    car.setProductionYear(1995);
    car.setVin("testVin");
    car.setSalesPersonId(personId.get());

    Car car2 = new Car();
    car2.setId(carId.getAndIncrement());
    car2.setMake("Ford");
    car2.setModel("Mondeo RS");
    car2.setNumberOfDoors(2);
    car2.setOdometer(123452);
    car2.setProductionYear(19952);
    car2.setVin("testVin2");
    car2.setSalesPersonId(personId.get());

    SalesPerson salesPerson = new SalesPerson();
    salesPerson.setEmail("emial@mail.ee");
    salesPerson.setEmployer("Auto 25");
    salesPerson.setName("Juhan Maasikas");
    salesPerson.getCars().add(car);
    salesPerson.getCars().add(car2);
    salesPerson.setId(personId.get());

    salesPersonMap.put(personId.getAndIncrement(), salesPerson);
  }


}