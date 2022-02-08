package com.app.carrental.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "booking_status")
public class BookingStatus {

    private static final int STATUS_PENDING = 1;
    private static final int STATUS_APPROVED = 2;
    private static final int STATUS_EXPIRED = 3;
    private static final int STATUS_DECLINED = 4;

    @Id
    private int id;
    private @NonNull String status;

    public static Integer getStatusCode(String status){
        switch (status){
            case "PENDING" : return STATUS_PENDING;
            case "APPROVED" : return STATUS_APPROVED;
            case "EXPIRED" : return STATUS_EXPIRED;
            case "DECLINED" : return STATUS_DECLINED;
            default: return null;
        }
    }

}
