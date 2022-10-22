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
public class TourType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(2000)")
    private String description;

    private String imagePath;

    @OneToMany(mappedBy = "tourType", cascade = CascadeType.REMOVE)
    private List<Tour> tours;
}
