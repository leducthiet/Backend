package com.DoAnTotNghiep.core.user.entity;

import com.DoAnTotNghiep.core.user.domain.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
}
