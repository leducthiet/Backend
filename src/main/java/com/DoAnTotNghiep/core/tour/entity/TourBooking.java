package com.DoAnTotNghiep.core.tour.entity;

import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.user.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TourBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private String note;

    private Long totalPrice;

    private Integer quantityAdult;

    private Integer quantityChild2To5;

    private Integer quantityChild5To11;

    private Integer quantityBaby;

    private BookingState bookingState;

    private Date dateBooking;

    private Boolean isDelete;

    private Boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
