package com.sacred.sacredheartacademy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeeStatus {
        PAID,
    PARTIAL,
    UNPAID,
    OVERPAID
}
