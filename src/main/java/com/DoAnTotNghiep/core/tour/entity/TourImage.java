package com.DoAnTotNghiep.core.tour.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TourImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    private String note;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
}
