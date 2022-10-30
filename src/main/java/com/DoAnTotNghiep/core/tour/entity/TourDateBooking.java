package com.DoAnTotNghiep.core.tour.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TourDateBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateBooking;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @OneToMany(mappedBy = "tourDateBooking", cascade = CascadeType.REMOVE)
    private List<TourBooking> tourBookings;
}
