package com.DoAnTotNghiep.core.tour.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdDate;

    private String createdUser;

    private Long price;

    private Integer month;

    @ManyToOne
    @JoinColumn(name = "travelAgency_id")
    private TravelAgency travelAgency;
}
