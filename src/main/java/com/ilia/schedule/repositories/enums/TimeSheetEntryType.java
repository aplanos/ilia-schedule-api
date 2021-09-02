package com.ilia.schedule.repositories.enums;

public enum TimeSheetEntryType {

    START(0),
    PAUSE(1),
    RESUME(2),
    END(3),
    UNKNOWN(4);

    private final int value;

    TimeSheetEntryType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TimeSheetEntryType fromValue(int value) {
        if (value < 0 || value > 3) {
            return TimeSheetEntryType.UNKNOWN;
        }

        return TimeSheetEntryType.values()[value];
    }
}
