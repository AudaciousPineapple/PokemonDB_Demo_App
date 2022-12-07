package com.example.pokemondbappv2.pokeEnums;

public enum XpGrowth {

    FAST (800000, "Fast", "fast"),
    MEDIUM_FAST (1000000, "Medium Fast", "medium"),
    MEDIUM_SLOW (1059860, "Medium Slow", "medium-slow"),
    SLOW (1250000, "Slow", "slow"),
    ERRATIC (600000, "Erratic", "slow-then-very-fast"),
    FLUCTUATING (1640000, "Fluctuating", "fast-then-very-slow");

    private final int max;
    private final String name, apiName;

    XpGrowth(int max, String name, String apiName) {
        this.name = name;
        this.max = max;
        this.apiName = apiName;
    }

    public static XpGrowth getGrowthRate(String apiName) {
        if (apiName.contentEquals(FAST.apiName))
            return FAST;
        else if (apiName.contentEquals(MEDIUM_FAST.apiName))
            return MEDIUM_FAST;
        else if (apiName.contentEquals(MEDIUM_SLOW.apiName))
            return MEDIUM_SLOW;
        else if (apiName.contentEquals(SLOW.apiName))
            return SLOW;
        else if (apiName.contentEquals(ERRATIC.apiName))
            return ERRATIC;
        else if (apiName.contentEquals(FLUCTUATING.apiName))
            return FLUCTUATING;
        else
            return null;
    }

    /*
    public static int checkMax(String apiName) {
        if (apiName.contentEquals(FAST.apiName))
            return FAST.max;
        else if (apiName.contentEquals(MEDIUM_FAST.apiName))
            return MEDIUM_FAST.max;
        else if (apiName.contentEquals(MEDIUM_SLOW.apiName))
            return MEDIUM_SLOW.max;
        else if (apiName.contentEquals(SLOW.apiName))
            return SLOW.max;
        else if (apiName.contentEquals(ERRATIC.apiName))
            return ERRATIC.max;
        else if (apiName.contentEquals(FLUCTUATING.apiName))
            return FLUCTUATING.max;
        else
            return 0;
    } */

    public static XpGrowth checkName (String name) {
        if (name.contentEquals(FAST.name))
            return FAST;
        else if (name.contentEquals(MEDIUM_FAST.name))
            return MEDIUM_FAST;
        else if (name.contentEquals(MEDIUM_SLOW.name))
            return MEDIUM_SLOW;
        else if (name.contentEquals(SLOW.name))
            return SLOW;
        else if (name.contentEquals(ERRATIC.name))
            return ERRATIC;
        else if (name.contentEquals(FLUCTUATING.name))
            return FLUCTUATING;
        else
            return null;
    }

    public String getName() { return name; }
    public int getMax() { return max; }
    public String getApiName() { return apiName; }
}
