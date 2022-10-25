package com.example.pokemondbappv2;

public enum Type {
    NOR("Normal", "NOR"),
    FIR ("Fire", "FIR"),
    WAT ("Water", "WAT"),
    GRA ("Grass", "GRA"),
    ELE ("Electric", "ELE"),
    ICE ("Ice", "ICE"),
    FIG ("Fighting", "FIG"),
    POI ("Poison", "POI"),
    GRO ("Ground", "GRO"),
    FLY ("Flying", "FLY"),
    PSY ("Psychic", "PSY"),
    BUG ("Bug", "BUG"),
    ROC ("Rock", "ROC"),
    GHO ("Ghost", "GHO"),
    DRA ("Dragon", "DRA"),
    DAR ("Dark", "DAR"),
    STE ("Steel", "STE"),
    FAI ("Fairy", "FAI"),
    NON ("No Type", "NON");

    private final String value;
    private final String abbr;

    Type(String value, String abbr) {
        this.value = value;
        this.abbr = abbr;
    }

    public String getValue() { return value; }

    public String getAbbr() { return abbr; }

}
