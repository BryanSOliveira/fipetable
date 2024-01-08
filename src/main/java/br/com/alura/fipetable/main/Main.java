package br.com.alura.fipetable.main;

import br.com.alura.fipetable.model.Data;
import br.com.alura.fipetable.model.Models;
import br.com.alura.fipetable.model.Vehicle;
import br.com.alura.fipetable.service.ApiConsumer;
import br.com.alura.fipetable.service.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  private Scanner scanner = new Scanner(System.in);
  private ApiConsumer apiConsumer = new ApiConsumer();
  private DataConverter dataConverter = new DataConverter();

  private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

  public void displayMenu() {
    String menu = """
            *** OPTIONS ***
            Car
            Motorcycle
            Truck
                        
            Enter one of the options to query:
            """;

    System.out.println(menu);
    String option = scanner.nextLine();
    String address;

    if (option.toLowerCase().contains("car")) {
      address = URL_BASE + "carros/marcas";
    } else if (option.toLowerCase().contains("mot")) {
      address = URL_BASE + "motos/marcas";
    } else {
      address = URL_BASE + "caminhoes/marcas";
    }

    String json = apiConsumer.getData(address);
    List<Data> brands = dataConverter.getList(json, Data.class);
    brands.stream()
            .sorted(Comparator.comparing(Data::code))
            .forEach(System.out::println);

    System.out.println("Enter the brand code for querying:");
    String brandCode = scanner.nextLine();

    address += "/" + brandCode + "/modelos";
    json = apiConsumer.getData(address);
    var models = dataConverter.getData(json, Models.class);

    System.out.println("\nModels of this brand:");
    models.models().stream()
            .sorted(Comparator.comparing(Data::code))
            .forEach(System.out::println);

    System.out.println("\nEnter a snippet of the car name to be searched:");
    String vehicleName = scanner.nextLine();

    List<Data> filteredModels = models.models().stream()
            .filter(m -> m.name().toLowerCase().contains(vehicleName.toLowerCase()))
            .collect(Collectors.toList());

    System.out.println("\nFiltered models");
    filteredModels.forEach(System.out::println);

    System.out.println("\nPlease enter the model code to retrieve valuation values:");
    String modelCode = scanner.nextLine();

    address += "/" + modelCode + "/anos";
    json = apiConsumer.getData(address);
    List<Data> years = dataConverter.getList(json, Data.class);

    List<Vehicle> vehicles = new ArrayList<>();
    for (int i = 0; i < years.size(); i++) {
      var yearsAddress = address + "/" + years.get(i).code();
      json = apiConsumer.getData(yearsAddress);
      Vehicle vehicle = dataConverter.getData(json, Vehicle.class);
      vehicles.add(vehicle);
    }

    System.out.println("\nAll filtered vehicles with assessments by year:");
    vehicles.forEach(System.out::println);

  }
}
