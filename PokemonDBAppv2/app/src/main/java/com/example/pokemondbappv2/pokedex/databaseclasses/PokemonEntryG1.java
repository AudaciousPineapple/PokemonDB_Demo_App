package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.pokemondbappv2.pokeEnums.*;

public class PokemonEntryG1 {

    private int dexNum, height, weight, capRate, baseHp, baseAtk, baseDef, baseSpc, baseSpe, id;
    private String name, nameJp, nameJpEng, nameFr, nameGer, nameKor, classification;
    private Type type1;
    private Type type2;
    private XpGrowth xpRate;
    private byte[] sprite1, sprite2;

    public PokemonEntryG1() {
        this.dexNum = 0;
        this.name = null;
        this.sprite1 = null;
        this.sprite2 = null;
        this.nameJp = null;
        this.nameJpEng = null;
        this.nameFr = null;
        this.nameGer = null;
        this.nameKor = null;
        this.type1 = Type.UNK;
        this.type2 = Type.UNK;
        this.classification = null;
        this.height = 0;
        this.weight = 0;
        this.capRate = 0;
        this.xpRate = null;
        this.baseHp = 0;
        this.baseAtk = 0;
        this.baseDef = 0;
        this.baseSpc = 0;
        this.baseSpe = 0;
    }

    public PokemonEntryG1 (int dexNum, String name, byte[] sprite1, byte[] sprite2, String nameJp,
                           String nameJpEng, String nameFr, String nameGer, String nameKor,
                           Type type1, Type type2, String classification, int height, int weight,
                           int capRate, XpGrowth xpRate, int baseHp, int baseAtk, int baseDef,
                           int baseSpc, int baseSpe, int id) {
        this.dexNum = dexNum;
        this.name = name;
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
        this.nameJp = nameJp;
        this.nameJpEng = nameJpEng;
        this.nameFr = nameFr;
        this.nameGer = nameGer;
        this.nameKor = nameKor;
        this.type1 = type1;
        this.type2 = type2;
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.capRate = capRate;
        this.xpRate = xpRate;
        this.baseHp = baseHp;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseSpc = baseSpc;
        this.baseSpe = baseSpe;
        this.id = id;
    }

    public int getId() { return id; }
    public int getDexNum() { return dexNum; }
    public String getName() { return name; }
    public byte[] getSprite1() { return sprite1; }
    public byte[] getSprite2() { return sprite2; }
    public String getNameJp() { return nameJp; }
    public String getNameJpEng() { return nameJpEng; }
    public String getNameFr() { return nameFr; }
    public String getNameGer() { return nameGer; }
    public String getNameKor() { return nameKor; }
    public Type getType1() { return type1; }
    public Type getType2() { return type2; }
    public String getClassification() { return classification; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public int getCapRate() { return capRate; }
    public XpGrowth getXpRate() { return xpRate; }
    public int getBaseHp() { return baseHp; }
    public int getBaseAtk() { return baseAtk; }
    public int getBaseDef() { return baseDef; }
    public int getBaseSpc() { return baseSpc; }
    public int getBaseSpe() { return baseSpe; }

    public void setDexNum(int dexNum) { this.dexNum = dexNum; }
    public void setName(String name) { this.name = name; }
    public void setSprite1(byte[] sprite1) { this.sprite1 = sprite1; }
    public void setSprite2(byte[] sprite2) { this.sprite2 = sprite2; }
    public void setNameJp(String nameJp) { this.nameJp = nameJp; }
    public void setNameJpEng(String nameJpEng) { this.nameJpEng = nameJpEng; }
    public void setNameFr(String nameFr) { this.nameFr = nameFr; }
    public void setNameGer(String nameGer) { this.nameGer = nameGer; }
    public void setNameKor(String nameKor) { this.nameKor = nameKor; }
    public void setType1(Type type1) { this.type1 = type1; }
    public void setType2(Type type2) { this.type2 = type2; }
    public void setClassification(String classification) { this.classification = classification; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setCapRate(int capRate) { this.capRate = capRate; }
    public void setXpRate(XpGrowth xpRate) { this.xpRate = xpRate; }
    public void setBaseHp(int baseHp) { this.baseHp = baseHp; }
    public void setBaseAtk(int baseAtk) { this.baseAtk = baseAtk; }
    public void setBaseDef(int baseDef) { this.baseDef = baseDef; }
    public void setBaseSpc(int baseSpc) { this.baseSpc = baseSpc; }
    public void setBaseSpe(int baseSpe) { this.baseSpe = baseSpe; }

    @Override
    public boolean equals (Object otherEntry) {
        if (otherEntry instanceof PokemonEntryG1)
            return this.id == ((PokemonEntryG1) otherEntry).id;
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return id + ": " + dexNum + " - " + name + " - " + nameJp + " - " + nameJpEng + " - " +
                nameFr + " - " + nameGer + " - " + nameKor + " - " + type1.getName() + " - " +
                type2.getName() + " - " + classification + " - " + height + " - " + weight + " - " +
                capRate + " - " + xpRate + " - " + baseHp + " - " + baseAtk + " - " + baseDef +
                " - " + baseSpc + " - " + baseSpe;
    }
}