package com.example.pokemondbappv2.pokedex.databaseclasses;

import androidx.annotation.NonNull;

public class EncounterG1Entry {

    private int dexNum, chance, minLevel, maxLevel;
    private String locName, subLocName, method, version;

    public EncounterG1Entry() {
        this.dexNum = 0;
        this.locName = null;
        this.subLocName = null;
        this.method = null;
        this.chance = 0;
        this.minLevel = 0;
        this.maxLevel = 0;
        this.version = null;
    }

    public EncounterG1Entry (int dexNum, String locName, String subLocName, String method,
                             int chance, int minLevel, int maxLevel, String version) {
        this.dexNum = dexNum;
        this.locName = locName;
        this.subLocName = subLocName;
        this.method = method;
        this.chance = chance;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.version = version;
    }

    public int getDexNum() { return dexNum; }
    public String getLocName() { return locName; }
    public String getSubLocName() { return subLocName;}
    public String getMethod() { return method; }
    public int getChance() { return chance; }
    public int getMinLevel() { return minLevel; }
    public int getMaxLevel() { return maxLevel; }
    public String getVersion() { return version; }
    public void setDexNum(int dexNum) { this.dexNum = dexNum; }
    public void setLocName(String locName) { this.locName = locName; }
    public void setSubLocName(String subLocName) { this.subLocName = subLocName; }
    public void setMethod(String method) { this.method = method; }
    public void setChance(int chance) { this.chance = chance; }
    public void setMinLevel(int minLevel) { this.minLevel = minLevel; }
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }
    public void setVersion(String version) { this.version = version; }

    @NonNull
    @Override
    public String toString() {
        return dexNum + " - " + locName + " - " + subLocName + " - " + method + " - " + chance
                + "% - Min " + minLevel + " - Max " + maxLevel + " - " + version;
    }
}
