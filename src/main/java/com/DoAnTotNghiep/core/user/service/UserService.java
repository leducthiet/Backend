package com.DoAnTotNghiep.core.user.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.user.domain.Role;
import com.DoAnTotNghiep.core.user.entity.Users;
import com.DoAnTotNghiep.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void register(Users users) {
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        users.setActive(true);
        users.setRole(Role.ROLE_CUSTOMER);
        users.setCreationDate(new Date());
        userRepository.save(users);
    }

    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public void updateUsers(Users users) {
        userRepository.save(users);
    }

    public void deleteUsers(Users users) {
        userRepository.deleteById(users.getId());
    }

    public Integer getPercentAccount() {
        Date today = new Date();
        Long presentNumber = userRepository.countAccountCreatedByMonth(today.getMonth() + 1);
        Long previousNumber = userRepository.countAccountCreatedByMonth(today.getMonth());

        if (presentNumber == null) {
            presentNumber = 0L;
        }

        if (previousNumber == null || previousNumber == 0L) {
            return 100;
        }

        return Math.toIntExact((presentNumber * 100) / previousNumber) - 100;
    }
}
