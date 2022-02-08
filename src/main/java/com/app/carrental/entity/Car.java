package com.app.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "brand")
    private @NonNull String brand;

    @Column(name = "model")
    private @NonNull String model;

    @Column(name = "price")
    private @NonNull Integer price;

    @Column(name = "bookings", nullable = true)
    private Integer bookings;

    @Column(name = "rating", nullable = true)
    private Double rating;

}
