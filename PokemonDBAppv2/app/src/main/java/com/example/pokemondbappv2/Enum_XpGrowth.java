package com.example.pokemondbappv2;

public enum Enum_XpGrowth {

    F (800000, "Fast", "F"),
    MF (1000000, "Medium Fast", "MF"),
    MS (1059860, "Medium Slow", "MS"),
    S (1250000, "Slow", "S"),
    ER (0, "Error", "ER");

    private final int rate;
    private final String value, abbr;

    Enum_XpGrowth(int rate, String value, String abbr) {
        this.value = value;
        this.rate = rate;
        this.abbr = abbr;
    }

    public String getValue() { return value; }
    public int getRate() { return rate; }
    public String getAbbr() { return abbr; }
}
