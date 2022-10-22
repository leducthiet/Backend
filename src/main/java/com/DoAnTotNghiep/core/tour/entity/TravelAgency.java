package com.DoAnTotNghiep.core.tour.entity;

import com.DoAnTotNghiep.core.hotel.entity.Room;
import com.DoAnTotNghiep.core.tour.domain.Status;
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
public class TravelAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private Status status;

    private String description;

    private Boolean isDelete;

    private String userCreate;

    private Date dateCreate;

    private String businessLicense;

    @OneToMany(mappedBy = "travelAgency", cascade = CascadeType.REMOVE)
    private List<Tour> tours;

    @OneToMany(mappedBy = "travelAgency", cascade = CascadeType.REMOVE)
    private List<Room> rooms;
}
