package com.zerobase.domain.entity.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BookingStatus {
    PENDING("예약 대기"),
    APPROVED("예약 승인"),
    REJECTED("예약 거부");

    private final String code;

}
