package com.app.carrental.controller.rest;

import com.app.carrental.entity.Car;
import com.app.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car/")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car findCarById(
            @PathVariable Integer carId){
        return carService.findCarById(carId);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findAllCars(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") Integer offset){
        return carService.findAllCars(offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithBrand(
            @PathVariable("brand") String brand,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carService.findCarsWithBrand(brand, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/brand/{brand}/model/{model}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithBrandAndModel(
            @PathVariable("model") String model,
            @PathVariable("brand") String brand,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carService.findCarsWithModelAndBrand(model, brand, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/between/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithinPriceRange(
            @RequestParam("minPrice") int minPrice,
            @RequestParam("maxPrice") int maxPrice,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carService.findCarsWithinPriceRange(minPrice, maxPrice, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/above/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsAbovePriceRange(
            @PathVariable("price") int price,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carService.findCarsAbovePriceRange(price, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/below/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsBelowPriceRange(
            @PathVariable("price") int price,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carService.findCarsBelowPriceRange(price, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/names/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllBrandNames(
            @PathVariable("brand") String brand){
        return carService.getAllBrandNames(brand);
    }

    @GetMapping(value = "/names/brand/{brand}/model/{model}")
    public List<String> getAllModelNamesUnderBrand(
            @PathVariable("brand") String brand,
            @PathVariable("model") String model){
        return carService.getAllModelNamesUnderBrand(brand, model);
    }

    @DeleteMapping(value = "/{carId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteCar(@PathVariable("carId") int carId){
        return carService.deleteCar(carId);
    }

    @PutMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCar(@RequestBody Car car){
        return carService.updateCar(car);
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

}
