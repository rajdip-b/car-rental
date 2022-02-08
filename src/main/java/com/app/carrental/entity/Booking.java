package com.app.carrental.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private @NonNull int id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private @NonNull User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private @NonNull Car car;

    @Column(name = "booking_date")
    private @NonNull String bookingDate;

    @Column(name = "approval_date")
    private String approvalDate;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "duration")
    private int duration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private BookingStatus bookingStatus;

}
