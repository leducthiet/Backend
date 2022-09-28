package com.DoAnTotNghiep.core.user.domain;

import java.util.Arrays;

public enum Role {
    ROLE_SYSTEM_ADMIN(0),
    ROLE_CUSTOMER(1);

    private final Integer value;

    Role(Integer value) {
        this.value = value;
    }

    public static Role of(Integer value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getValue() {
        return value;
    }
}
