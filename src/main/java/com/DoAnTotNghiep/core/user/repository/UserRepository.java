package com.DoAnTotNghiep.core.user.repository;

import com.DoAnTotNghiep.core.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
