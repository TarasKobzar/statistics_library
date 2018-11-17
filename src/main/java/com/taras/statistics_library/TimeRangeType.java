package com.taras.statistics_library;

public enum TimeRangeType {
    MINUTE(60_000),
    HOUR(3_600_000),
    DAY(86_400_000);

    private int range;

    TimeRangeType(int value) {
        this.range = value;
    }

    int getRange() {
        return range;
    }
}
