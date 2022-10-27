package com.example.pokemondbappv2;

public class ModelPokemonG1 {

    private int dexNum, captureRate, hp, atk, def, spc, spe, evo, evoLvl, prEvo;
    private String name, classification, redLoc, blueLoc, greenLoc, yellowLoc;
    private double height, weight;
    private Type type1, type2;
    private Enum_XpGrowth xpGrowth;

    // getters
    public int getDexNum() { return this.dexNum; }
    public int getCaptureRate() { return this.captureRate; }
    public int getHp() { return  this.hp; }
    public int getAtk() { return this.atk; }
    public int getDef() { return this.def; }
    public int getSpc() { return this.spc; }
    public int getSpe() { return this.spe; }
    public int getEvo() { return this.evo; }
    public int getEvoLvl() { return this.evoLvl; }
    public int getPrEvo() { return this.prEvo; }
    public String getName() { return name; }
    public String getClassification() { return classification; }
    public String getRedLoc() { return redLoc; }
    public String getBlueLoc() { return blueLoc; }
    public String getGreenLoc() { return greenLoc; }
    public String getYellowLoc() { return yellowLoc; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public Type getType1() { return type1; }
    public Type getType2() { return type2; }
    public Enum_XpGrowth getXpGrowth() { return xpGrowth; }

    // Constructor
    public ModelPokemonG1(int dexNum, String name, Type type1, Type type2, String classification,
                          double height, double weight, int captureRate, Enum_XpGrowth xpGrowth,
                          String redLoc, String blueLoc, String greenLoc, String yellowLoc, int hp,
                          int atk, int def, int spc, int spe, int evo, int evoLvl, int prEvo) {

        this.dexNum = dexNum;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.captureRate = captureRate;
        this.xpGrowth = xpGrowth;
        this.redLoc = redLoc;
        this.blueLoc = blueLoc;
        this.greenLoc = greenLoc;
        this.yellowLoc = yellowLoc;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spc = spc;
        this.spe = spe;
        this.evo = evo;
        this.evoLvl = evoLvl;
        this.prEvo = prEvo;
    }

    public String toString() {
        String str = "";
        String div = " | ";
        str += dexNum + div + name + div + type1.getValue() + div + type2.getValue() + div
            + classification + " Pokemon" + div + height + div + weight + div + captureRate + div
            + xpGrowth.getRate() + div + redLoc + div + blueLoc + div + greenLoc + div + yellowLoc
            + div + hp + div + atk + div + def + div + spc + div + spe + div + evo + div + evoLvl
            + div + prEvo + div;

        return str;
    }

}
