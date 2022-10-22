package com.DoAnTotNghiep.core.hotel.entity;

import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
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
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date inDate;

    private Date outDate;

    private Date bookingDate;

    private Long totalPrice;

    private Boolean isPaid;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private BookingState bookingState;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
