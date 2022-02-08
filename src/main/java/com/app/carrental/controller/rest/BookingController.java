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



}
