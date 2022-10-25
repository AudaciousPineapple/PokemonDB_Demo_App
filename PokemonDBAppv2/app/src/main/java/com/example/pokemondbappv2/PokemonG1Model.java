package com.example.pokemondbappv2;

public class PokemonG1Model {

    private int dexNum, captureRate, hp, atk, def, spc, spe, evo, evoLvl, prEvo;
    private String name, classification, redLoc, blueLoc, greenLoc, yellowLoc;
    private double height, weight;
    private Type type1, type2;
    private XpGrowth xpGrowth;

    public int getDexNum() { return this.dexNum; }
    public void setDexNum(int dexNum) { this.dexNum = dexNum; }

    public int getCaptureRate() { return this.captureRate; }
    public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }

    public int getHp() { return  this.hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getAtk() { return this.atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public int getDef() { return this.def; }
    public void setDef(int def) { this.def = def; }

    public int getSpc() { return this.spc; }
    public void setSpc(int spc) { this.spc = spc; }

    public int getSpe() { return this.spe; }
    public void setSpe(int spe) { this.spe = spe; }

    public int getEvo() { return this.evo; }
    public void setEvo(int evo) { this.evo = evo; }

    public int getEvoLvl() { return this.evoLvl; }
    public void setEvoLvl(int evoLvl) { this.evoLvl = evoLvl; }

    public int getPrEvo() { return this.prEvo; }
    public void setPrEvo(int prEvo) { this.prEvo = prEvo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public String getRedLoc() { return redLoc; }
    public void setRedLoc(String redLoc) { this.redLoc = redLoc; }

    public String getBlueLoc() { return blueLoc; }
    public void setBlueLoc(String blueLoc) { this.blueLoc = blueLoc; }

    public String getGreenLoc() { return greenLoc; }
    public void setGreenLoc(String greenLoc) { this.greenLoc = greenLoc; }

    public String getYellowLoc() { return yellowLoc; }
    public void setYellowLoc(String yellowLoc) { this.yellowLoc = yellowLoc; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public Type getType1() { return type1; }
    public void setType1(Type type1) { this.type1 = type1; }

    public Type getType2() { return type2; }
    public void setType2(Type type2) { this.type2 = type2; }

    public XpGrowth getXpGrowth() { return xpGrowth; }
    public void setXpGrowth(XpGrowth xpGrowth) { this.xpGrowth = xpGrowth; }


    public PokemonG1Model () {
        this.dexNum = 0;
        this.name = "";
        this.type1 = Type.NON;
        this.type2 = Type.NON;
        this.classification = "";
        this.height = 0;
        this.weight = 0;
        this.captureRate = 0;
        this.xpGrowth = XpGrowth.ER;
        this.redLoc = "";
        this.blueLoc = "";
        this.greenLoc = "";
        this.yellowLoc = "";
        this.hp = 0;
        this.atk = 0;
        this.def = 0;
        this.spc = 0;
        this.spe = 0;
        this.evo = 0;
        this.evoLvl = 0;
        this.prEvo = 0;
    }
    public PokemonG1Model(int dexNum, String name, Type type1, Type type2, String classification,
                          double height, double weight, int captureRate, XpGrowth xpGrowth,
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

}
