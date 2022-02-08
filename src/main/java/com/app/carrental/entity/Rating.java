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
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @Column(name = "car_id")
    private int carId;

    @Column(name = "rating")
    private @NonNull Double rating;

    @Column(name = "comment")
    private @NonNull String comment;

    @Column(name = "date")
    private @NonNull String date;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private @NonNull User user;

    @Transient
    private Car car;

}
