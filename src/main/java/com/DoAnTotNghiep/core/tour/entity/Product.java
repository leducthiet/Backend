package com.DoAnTotNghiep.core.tour.entity;

import com.DoAnTotNghiep.core.user.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private Long price;

    private Long numberOfSlot;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<TravelAgency> travelAgencies;
}
