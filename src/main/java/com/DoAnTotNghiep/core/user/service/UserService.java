package com.DoAnTotNghiep.core.user.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.user.domain.Role;
import com.DoAnTotNghiep.core.user.entity.Users;
import com.DoAnTotNghiep.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void register(Users users) {
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        users.setActive(true);
        users.setRole(Role.ROLE_CUSTOMER);
        userRepository.save(users);
    }

    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(BadRequestException::new);
    }
}
