package com.company.mariosy.entity;

public enum MariosType {
    MARIOS_T1,
    MARIOS_T2,
    MARIOS_T3,
    MARIOS_T4,
    MARIOS_T5,
    MARIOS_T6;

    public static boolean checkIfTypeExists(MariosType value) {
        for (MariosType type : values()) {
            if (type.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
