package com.DoAnTotNghiep.core.tour.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Column(columnDefinition = "nvarchar(2550)")
    private String description;

    @OneToMany(mappedBy = "province", cascade = CascadeType.REMOVE)
    private List<Tour> tours;
}
