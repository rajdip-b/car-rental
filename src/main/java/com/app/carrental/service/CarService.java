package com.app.carrental.service;

import com.app.carrental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {

    Car findCarById(int carId);
    Page<Car> findAllCars(int offset, String sortOrder, String sortBy);
    Page<Car> findCarsWithBrand(String brand, int offset, String sortOrder, String sortBy);
    Page<Car> findCarsWithModelAndBrand(String model, String brand, int offset, String sortOrder, String sortBy);
    Page<Car> findCarsWithinPriceRange(int minPrice, int maxPrice, int offset, String sortOrder, String sortBy);
    Page<Car> findCarsAbovePriceRange(int price, int offset, String sortOrder, String sortBy);
    Page<Car> findCarsBelowPriceRange(int price, int offset, String sortOrder, String sortBy);
    List<String> getAllBrandNames(String brand);
    List<String> getAllModelNamesUnderBrand(String brand, String model);
    boolean updateCar(Car car);
    boolean addCar(Car car);
    boolean deleteCar(int carId);

}
