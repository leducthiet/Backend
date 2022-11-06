package com.DoAnTotNghiep.core.tour.entity;

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
public class TourFeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createDate;

    private Date updateDate;

    private Long rating;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String comment;

    private Boolean isHidden;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne
    @JoinColumn(name = "tour_booking_id")
    private TourBooking tourBooking;
}
