package com.spring.tobi.ch5;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;
    private final Level nextLevel;

    Level(int value, Level nextLevel) {
        this.value = value;
        this.nextLevel = nextLevel;
    }

    public int intValue() {
        return value;
    }

    public Level getNextLevel() {
        return this.nextLevel;
    }

    public static Level valueToLevel(int value) throws AssertionError{
        switch (value) {
            case 1:
                return BASIC;
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            default:
                throw new AssertionError("UnKnown value: " + value);
        }
    }

    public static int valueToInt(Level level) throws AssertionError{
        switch (level) {
            case BASIC: return 1;
            case SILVER: return 2;
            case GOLD: return 3;
            default: throw new AssertionError("Unknown Level: " + level);
        }
    }
}
