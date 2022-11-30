package com.DoAnTotNghiep.core.tour.entity;

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
public class TravelAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    private String phone;

    private String email;

    private boolean isActive;

    private String description;

    private Boolean isDelete;

    private String userCreate;

    private Date dateCreate;

    private String businessLicense;

    private Integer type;

    private Date expiredDate;

    private String bank;

    private String accountNumber;

    private String accountHolder;

    @OneToMany(mappedBy = "travelAgency", cascade = CascadeType.REMOVE)
    private List<Tour> tours;

    @OneToMany(mappedBy = "travelAgency", cascade = CascadeType.REMOVE)
    private List<Users> users;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
