package com.app.carrental.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NonNull Integer id;
    private @NonNull String brand;
    private @NonNull String model;
    private @NonNull Integer price;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car", fetch = FetchType.LAZY)
    private List<Booking> bookings;

}
