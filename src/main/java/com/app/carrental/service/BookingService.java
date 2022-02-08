package com.app.carrental.service;

import com.app.carrental.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    Booking findBookingById(int bookingId);
    Page<Booking> findAllBookings(int offset, String sortOrder, String sortBy);
    Page<Booking> findBookingsByUser(int userId, int offset, String sortOrder, String sortBy);
    Page<Booking> findBookingsByCar(int carId, int offset, String sortOrder, String sortBy);
    Page<Booking> findBookingsByStatus(String status, int offset, String sortOrder, String sortBy);
    boolean setApproved(int bookingId);
    boolean setDeclined(int bookingId);
    boolean deleteBooking(int bookingId);
    boolean addBooking(Booking booking);

}