package com.DoAnTotNghiep.core.tour.domain;

import com.DoAnTotNghiep.core.user.domain.Role;

import java.util.Arrays;

public enum BookingState {
    PROCESSING(0),
    CANCELLED(1),
    COMPLETED(2);

    private final Integer value;

    BookingState(Integer value) {
        this.value = value;
    }

    public static BookingState of(Integer value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getValue() {
        return value;
    }
}
