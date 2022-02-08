package com.app.carrental.service.impl;

import com.app.carrental.entity.Booking;
import com.app.carrental.entity.BookingStatus;
import com.app.carrental.repository.BookingRepository;
import com.app.carrental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private static final List<String> validSortByFields;

    static {
        validSortByFields = List.of("booking_date", "approval_date", "expiry_date", "duration", "booking_status");
    }

    @Override
    public Booking findBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Override
    public Page<Booking> findAllBookings(int offset, String sortOrder, String sortBy) {
        return bookingRepository.findAll(
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Booking> findBookingsByUser(int userId, int offset, String sortOrder, String sortBy) {
        return bookingRepository.findBookingsByUser(
                userId,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Booking> findBookingsByCar(int carId, int offset, String sortOrder, String sortBy) {
        return bookingRepository.findBookingsByCar(
                carId,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public Page<Booking> findBookingsByStatus(String status, int offset, String sortOrder, String sortBy) {
        Integer statusCode = BookingStatus.getStatusCode(status);
        if (statusCode == null)
            return null;
        return bookingRepository.findBookingsByStatus(
                statusCode,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public boolean setApproved(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        try{
            assert booking != null;
            LocalDateTime expiryDate = LocalDateTime.parse(booking.getExpiryDate()).plusMonths(booking.getDuration());
            booking.setApprovalDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            booking.setBookingStatus(new BookingStatus("APPROVED"));
            booking.setExpiryDate(expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at BookingServiceImpl.setApproved");
            return false;
        }
    }

    @Override
    public boolean setDeclined(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        try{
            assert booking != null;
            booking.setBookingStatus(new BookingStatus("DECLINED"));
            bookingRepository.save(booking);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at BookingServiceImpl.setDeclined");
            return false;
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        try {
            bookingRepository.deleteById(bookingId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " at BookingServiceImpl.deleteBooking");
            return false;
        }
    }

    @Override
    public boolean addBooking(Booking booking) {
        try{
            booking.setBookingDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            booking.setBookingStatus(new BookingStatus("PENDING"));
            bookingRepository.save(booking);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at BookingServiceImpl.addBooking");
            return false;
        }
    }

    private String getSortBy(String sortBy){
        if (!validSortByFields.contains(sortBy))
            sortBy = "booking_date";
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
