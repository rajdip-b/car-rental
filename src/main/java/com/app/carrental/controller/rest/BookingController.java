package com.app.carrental.controller.rest;

import com.app.carrental.entity.Booking;
import com.app.carrental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking findBookingById(@PathVariable("bookingId") int bookingId){
        return bookingService.findBookingById(bookingId);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> findAllBookings(
            @RequestParam("offset") int offset,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder){
        return bookingService.findAllBookings(offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> findBookingsByUser(
            @PathVariable("userId") int userId,
            @RequestParam("offset") int offset,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder){
        return bookingService.findBookingsByUser(userId, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/car/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> findBookingsByCar(
            @PathVariable("carId") int carId,
            @RequestParam("offset") int offset,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder){
        return bookingService.findBookingsByCar(carId, offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> findBookingsByStatus(
            @PathVariable("status") String status,
            @RequestParam("offset") int offset,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder){
        return bookingService.findBookingsByStatus(status, offset, sortOrder, sortBy);
    }

    @PutMapping(value = "/approve/{bookingId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean setApproved(
            @PathVariable("bookingId") int bookingId){
        return bookingService.setApproved(bookingId);
    }

    @PutMapping(value = "/decline/{bookingId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean setDeclined(
            @PathVariable("bookingId") int bookingId){
        return bookingService.setDeclined(bookingId);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean addBooking(@RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }

    @DeleteMapping(value = "/{bookingId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteBooking(
            @PathVariable("bookingId") int bookingId){
        return bookingService.deleteBooking(bookingId);
    }

}
