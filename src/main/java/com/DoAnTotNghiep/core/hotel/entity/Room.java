package com.DoAnTotNghiep.core.hotel.entity;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer maxPeople;

    private Integer numOfRoom;

    private String status;

    private Long price;

    private Integer discount;

    private String profilePicture;

    private String createUser;

    private Date createDate;

    private Integer numOfBed;

    @ManyToOne
    @JoinColumn(name = "travelAgency_id")
    private TravelAgency travelAgency;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomBooking> roomBookings;
}
