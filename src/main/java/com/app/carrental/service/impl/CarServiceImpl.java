package com.app.carrental.service.impl;

import com.app.carrental.entity.Car;
import com.app.carrental.repository.BookingRepository;
import com.app.carrental.repository.CarRepository;
import com.app.carrental.repository.RatingRepository;
import com.app.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarServiceImpl implements CarService {

    private static final List<String> validSortByFields;

    static {
        validSortByFields = List.of("brand", "model", "price", "bookings", "rating");
    }

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Car findCarById(int carId) {
        return carRepository.findById(carId).orElse(null);
    }

    @Override
    public Page<Car> findAllCars(int offset, String sortOrder, String sortBy) {
        return carRepository.findAll(
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Car> findCarsWithBrand(String brand, int offset, String sortOrder, String sortBy) {
        return carRepository.findCarsWithBrand(
                brand,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Car> findCarsWithModelAndBrand(String model, String brand, int offset, String sortOrder, String sortBy) {
        return carRepository.findCarsWithModelAndBrand(
                model,
                brand,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Car> findCarsWithinPriceRange(int minPrice, int maxPrice, int offset, String sortOrder, String sortBy) {
        return carRepository.findCarsWithinPriceRange(
                minPrice,
                maxPrice,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Car> findCarsAbovePriceRange(int price, int offset, String sortOrder, String sortBy) {
        return carRepository.findCarsAbovePriceRange(
                price,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Car> findCarsBelowPriceRange(int price, int offset, String sortOrder, String sortBy) {
        return carRepository.findCarsBelowPriceRange(
                price,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public List<String> getAllBrandNames(String brand) {
        return carRepository.getAllBrandNames(brand);
    }

    @Override
    public List<String> getAllModelNamesUnderBrand(String brand, String model) {
        return carRepository.getAllModelNamesUnderBrand(brand, model);
    }

    @Override
    public boolean updateCar(Car car) {
        Car c = carRepository.findById(car.getId()).orElse(null);
        try{
            assert c != null;
            c.setBrand(car.getBrand());
            c.setPrice(car.getPrice());
            c.setModel(car.getModel());
            carRepository.save(c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at CarServiceImpl.updateCar");
            return false;
        }
    }

    @Override
    public boolean addCar(Car car) {
        try {
            carRepository.save(car);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at CarServiceImpl.addCar");
            return false;
        }
    }

    @Override
    public boolean deleteCar(int carId) {
        try {
            carRepository.deleteById(carId);
            bookingRepository.deleteBookingForCar(carId);
            ratingRepository.deleteRatingForCar(carId);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at CarServiceImpl.addCar");
            return false;
        }
    }

    private String getSortBy(String sortBy){
        if (!validSortByFields.contains(sortBy))
            sortBy = "model";
        return sortBy;
    }

    private Sort.Direction getSortOrder(String sortOrder){
        if ("DESC".equals(sortOrder))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    private int getOffset(int offset){
        if (offset < 0)
            offset = 0;
        return offset;
    }

}
