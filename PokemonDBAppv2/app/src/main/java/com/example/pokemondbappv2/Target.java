package com.example.pokemondbappv2;

public enum Target {

    OPP (0, "Chosen Target"),
    SELF (1, "User"),
    ALL_OPP (2, "All Opponents"),
    ALL (3, "Field"),
    USER_TEAM (4, "User Team"),
    RAND (5, "Random Target");

    private final int index;
    private final String value;

    Target(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public String getValue() { return value; }

    public static Target indexToTarget(int idx) {
        switch (idx) {
            case 0:
                return OPP;
            case 1:
                return SELF;
            case 2:
                return ALL_OPP;
            case 3:
                return ALL;
            case 4:
                return USER_TEAM;
            case 5:
                return RAND;
            default:
                return null;
        }
    }
}
