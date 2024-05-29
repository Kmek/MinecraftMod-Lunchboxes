package com.kmek.minecafe.item.registery;

public enum MarshmallowStickEnum {
    X,
    WARM,
    TOASTED,
    GOLDEN,
    WELL_DONE,
    BURNT,
    SCORCHED;

    public int maxBurnTime() {
        return switch(this) {
            case X -> 5;
            case WARM -> 10;
            case TOASTED -> 10;
            case GOLDEN -> 10;
            case WELL_DONE -> 5;
            case BURNT -> 5;
            case SCORCHED -> 5;
        };
    }

    public String displayName() {
        return switch(this) {
            case X -> "";
            case WARM -> "Warm";
            case TOASTED -> "Toasted";
            case GOLDEN -> "Golden";
            case WELL_DONE -> "Well done";
            case BURNT -> "Burnt";
            case SCORCHED -> "Scorched";
        };
    }
}
