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
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String tourName;

    private Integer totalPeople;

    private Integer duration;

    @Column(columnDefinition = "nvarchar(255)")
    private String schedule;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    private Boolean isDelete;

    private Integer priceAdult;

    private Integer priceChild2To5;

    private Integer priceChild5To11;

    private String userCreate;

    private Date dateCreate;

    private String userUpdate;

    private Date dateUpdate;

    private String thumbnailPath;

    @ManyToOne
    @JoinColumn(name = "tourType_id")
    private TourType tourType;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.REMOVE)
    private List<TourImage> tourImages;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "travelAgency_id")
    private TravelAgency travelAgency;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.REMOVE)
    private List<TourBooking> tourBookings;
}
