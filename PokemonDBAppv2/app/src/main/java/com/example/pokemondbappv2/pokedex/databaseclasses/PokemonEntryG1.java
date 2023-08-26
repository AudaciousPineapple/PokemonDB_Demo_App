package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.example.pokemondbappv2.pokeEnums.*;

import java.io.ByteArrayInputStream;

public class PokemonEntryG1 {

    private int dexNum, height, weight, capRate, id,
            baseHp, minHp50, maxHp50, minHp100, maxHp100,
            baseAtk, minAtk50, maxAtk50, minAtk100, maxAtk100,
            baseDef, minDef50, maxDef50, minDef100, maxDef100,
            baseSpc, minSpc50, maxSpc50, minSpc100, maxSpc100,
            baseSpe, minSpe50, maxSpe50, minSpe100, maxSpe100;
    private String name, nameJp, nameJpEng, nameFr, nameGer, nameKor, classification, sprite1, sprite2;
    private Type type1;
    private Type type2;
    private XpGrowth xpRate;

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
        this.minHp50 = 0;
        this.maxHp50 = 0;
        this.minHp100 = 0;
        this.maxHp100 = 0;

        this.baseAtk = 0;
        this.minAtk50 = 0;
        this.maxAtk50 = 0;
        this.minAtk100 = 0;
        this.maxAtk100 = 0;

        this.baseDef = 0;
        this.minDef50 = 0;
        this.maxDef50 = 0;
        this.minDef100 = 0;
        this.maxDef100 = 0;

        this.baseSpc = 0;
        this.minSpc50 = 0;
        this.maxSpc50 = 0;
        this.minSpc100 = 0;
        this.maxSpc100 = 0;

        this.baseSpe = 0;
        this.minSpe50 = 0;
        this.maxSpe50 = 0;
        this.minSpe100 = 0;
        this.maxSpe100 = 0;
    }

    public PokemonEntryG1 (int dexNum, String name, String sprite1, String sprite2, String nameJp,
                           String nameJpEng, String nameFr, String nameGer, String nameKor,
                           Type type1, Type type2, String classification, int height, int weight,
                           int capRate, XpGrowth xpRate,
                           int baseHp, int minHp50, int maxHp50, int minHp100, int maxHp100,
                           int baseAtk, int minAtk50, int maxAtk50, int minAtk100, int maxAtk100,
                           int baseDef, int minDef50, int maxDef50, int minDef100, int maxDef100,
                           int baseSpc, int minSpc50, int maxSpc50, int minSpc100, int maxSpc100,
                           int baseSpe, int minSpe50, int maxSpe50, int minSpe100, int maxSpe100,
                           int id) {
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
        this.minHp50 = minHp50;
        this.maxHp50 = maxHp50;
        this.minHp100 = minHp100;
        this.maxHp100 = maxHp100;

        this.baseAtk = baseAtk;
        this.minAtk50 = minAtk50;
        this.maxAtk50 = maxAtk50;
        this.minAtk100 = minAtk100;
        this.maxAtk100 = maxAtk100;

        this.baseDef = baseDef;
        this.minDef50 = minDef50;
        this.maxDef50 = maxDef50;
        this.minDef100 = minDef100;
        this.maxDef100 = maxDef100;

        this.baseSpc = baseSpc;
        this.minSpc50 = minSpc50;
        this.maxSpc50 = maxSpc50;
        this.minSpc100 = minSpc100;
        this.maxSpc100 = maxSpc100;

        this.baseSpe = baseSpe;
        this.minSpe50 = minSpe50;
        this.maxSpe50 = maxSpe50;
        this.minSpe100 = minSpe100;
        this.maxSpe100 = maxSpe100;

        this.id = id;
    }

    public int getId() { return id; }
    public int getDexNum() { return dexNum; }
    public String getName() { return name; }
    public String getSprite1() { return sprite1; }
    public String getSprite2() { return sprite2; }
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
    public int getMinHp50() { return minHp50; }
    public int getMaxHp50() { return maxHp50; }
    public int getMinHp100() { return minHp100; }
    public int getMaxHp100() { return maxHp100; }
    public int getMinAtk50() { return minAtk50; }
    public int getMaxAtk50() { return maxAtk50; }
    public int getMinAtk100() { return minAtk100; }
    public int getMaxAtk100() { return maxAtk100; }
    public int getMinDef50() { return minDef50; }
    public int getMaxDef50() { return maxDef50; }
    public int getMinDef100() { return minDef100; }
    public int getMaxDef100() { return maxDef100; }
    public int getMinSpc50() { return minSpc50; }
    public int getMaxSpc50() { return maxSpc50; }
    public int getMinSpc100() { return minSpc100; }
    public int getMaxSpc100() { return maxSpc100; }
    public int getMinSpe50() { return minSpe50; }
    public int getMaxSpe50() { return maxSpe50; }
    public int getMinSpe100() { return minSpe100; }
    public int getMaxSpe100() { return maxSpe100; }

    public void setDexNum(int dexNum) { this.dexNum = dexNum; }
    public void setName(String name) { this.name = name; }
    public void setSprite1(String sprite1) { this.sprite1 = sprite1; }
    public void setSprite2(String sprite2) { this.sprite2 = sprite2; }
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
    public void setMinHp50(int minHp50) { this.minHp50 = minHp50; }
    public void setMaxHp50(int maxHp50) { this.maxHp50 = maxHp50; }
    public void setMinHp100(int minHp100) { this.minHp100 = minHp100; }
    public void setMaxHp100(int maxHp100) { this.maxHp100 = maxHp100; }
    public void setMinAtk50(int minAtk50) { this.minAtk50 = minAtk50; }
    public void setMaxAtk50(int maxAtk50) { this.maxAtk50 = maxAtk50; }
    public void setMinAtk100(int minAtk100) { this.minAtk100 = minAtk100; }
    public void setMaxAtk100(int maxAtk100) { this.maxAtk100 = maxAtk100; }
    public void setMinDef50(int minDef50) { this.minDef50 = minDef50; }
    public void setMaxDef50(int maxDef50) { this.maxDef50 = maxDef50;}
    public void setMinDef100(int minDef100) { this.minDef100 = minDef100; }
    public void setMaxDef100(int maxDef100) { this.maxDef100 = maxDef100; }
    public void setMinSpc50(int minSpc50) { this.minSpc50 = minSpc50; }
    public void setMaxSpc50(int maxSpc50) { this.maxSpc50 = maxSpc50; }
    public void setMinSpc100(int minSpc100) { this.minSpc100 = minSpc100; }
    public void setMaxSpc100(int maxSpc100) { this.maxSpc100 = maxSpc100; }
    public void setMinSpe50(int minSpe50) { this.minSpe50 = minSpe50; }
    public void setMaxSpe50(int maxSpe50) { this.maxSpe50 = maxSpe50; }
    public void setMinSpe100(int minSpe100) { this.minSpe100 = minSpe100; }
    public void setMaxSpe100(int maxSpe100) { this.maxSpe100 = maxSpe100; }

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