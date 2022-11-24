package com.DoAnTotNghiep.core.user.entity;

import com.DoAnTotNghiep.core.hotel.entity.RoomBooking;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.entity.TourFeedBack;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.user.domain.Role;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    private String mobilePhone;

    private Date creationDate;

    private Date lastLogin;

    private boolean isActive;

    private Role role;

    private String avatar;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<TourBooking> tourBookings;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<RoomBooking> roomBookings;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<TourFeedBack> tourFeedBacks;

    @ManyToOne
    @JoinColumn(name = "travelAgency_id")
    private TravelAgency travelAgency;
}
