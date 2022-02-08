package com.app.carrental.controller.rest;

import com.app.carrental.entity.Car;
import com.app.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car/")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    private final List<String> validSortByFields = List.of("brand", "model", "price");

    @GetMapping(value = "/car/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car findCarById(
            @PathVariable Integer carId){
        return carRepository.findById(carId).orElse(null);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findAllCars(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") Integer offset){
        return carRepository.findAll(
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithBrand(
            @PathVariable("brand") String brand,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carRepository.findCarsWithBrand(
                brand,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/brand/{brand}/model/{model}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithBrandAndModel(
            @PathVariable("model") String model,
            @PathVariable("brand") String brand,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carRepository.findCarsWithModelAndBrand(
                model,
                brand,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/between/{minPrice}/{maxPrice}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsWithinPriceRange(
            @PathVariable("minPrice") int minPrice,
            @PathVariable("maxPrice") int maxPrice,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carRepository.findCarsWithinPriceRange(
                minPrice,
                maxPrice,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/above/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsAbovePriceRange(
            @PathVariable("price") int price,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carRepository.findCarsAbovePriceRange(
                price,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/below/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Car> findCarsBelowPriceRange(
            @PathVariable("price") int price,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return carRepository.findCarsBelowPriceRange(
                price,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/names/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllBrandNames(
            @PathVariable("brand") String brand
    ){
        return carRepository.getAllBrandNames(brand);
    }

    @GetMapping(value = "/names/brand/{brand}/model/{model}")
    public List<String> getAllModelNamesUnderBrand(
            @PathVariable("brand") String brand,
            @PathVariable("model") String model){
        return carRepository.getAllModelNamesUnderBrand(brand, model);
    }

    @DeleteMapping(value = "/{carId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteCar(
            @PathVariable("carId") int carId){
        try{
            carRepository.deleteById(carId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PutMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCar(
            @RequestBody Car car){
        try{
            carRepository.updateCar(car.getId(), car.getBrand(), car.getModel(), car.getPrice());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addCar(
            @RequestBody Car car){
        try{
            carRepository.save(car);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String getSortBy(String sortBy){
        if (!validSortByFields.contains(sortBy))
            return "price";
        return sortBy;
    }

    private Sort.Direction getSortOrder(String sortOrder){
        if ("DESC".equals(sortOrder)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
