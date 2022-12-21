package com.DoAnTotNghiep.core.tour.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdDate;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private String email;

    private String phone;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String content;
}
