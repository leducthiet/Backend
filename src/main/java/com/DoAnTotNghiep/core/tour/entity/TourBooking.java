package com.DoAnTotNghiep.core.tour.entity;

import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.user.entity.Users;
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
public class TourBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private String note;

    private Long totalPrice;

    private Integer quantityAdult;

    private Integer quantityChild2To5;

    private Integer quantityChild5To11;

    private BookingState bookingState;

    private Date dateCreate;

    private Date dateUpdate;

    @Column(unique=true)
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "tour_date_booking_id")
    private TourDateBooking tourDateBooking;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(mappedBy = "tourBooking", cascade = CascadeType.REMOVE)
    private TourFeedBack tourFeedBack;
}
