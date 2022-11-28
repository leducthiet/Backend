package com.DoAnTotNghiep.core.user.repository;

import com.DoAnTotNghiep.core.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM users u WHERE MONTH(u.creation_date) = ?1", nativeQuery = true)
    Long countAccountCreatedByMonth(int month);
}
