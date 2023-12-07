package com.example.pokemondbappv2.pokedex.databaseclasses.g1;

public class MovesetG1Entry {

    private int dexNum, pos, method, level, tmNum, prEvo;
    private String name, source;
    private boolean isYellow;

    public MovesetG1Entry() {
        dexNum = 0;
        name = null;
        pos = 0;
        method = 0;
        isYellow = false;
        level = 0;
        tmNum = 0;
        prEvo = 0;
        source = null;
    }

    public MovesetG1Entry (int dexNum, String name, int pos, int method, boolean isYellow,
                           int level, int tmNum, int prEvo, String source) {
        this.dexNum = dexNum;
        this.name = name;
        this.pos = pos;
        this.method = method;
        this.isYellow = isYellow;
        this.level = level;
        this.tmNum = tmNum;
        this.prEvo = prEvo;
        this.source = source;
    }

    public int getDexNum() {return dexNum;}
    public void setDexNum(int dexNum) {this.dexNum = dexNum;}

    public int getPos() {return pos;}
    public void setPos(int pos) {this.pos = pos;}

    public int getMethod() {return method;}
    public void setMethod(int method) {this.method = method;}

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getTmNum() {return tmNum;}
    public void setTmNum(int tmNum) {this.tmNum = tmNum;}

    public int getPrEvo() {return prEvo;}
    public void setPrEvo(int prEvo) {this.prEvo = prEvo;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSource() {return source;}
    public void setSource(String source) {this.source = source;}

    public boolean isYellow() {return isYellow;}
    public void setYellow(boolean yellow) {isYellow = yellow;}

    public String toString() {
        return dexNum+" - "+name+" - Position: "+pos+" - Method: "+method+" - is yellow?"+isYellow
                +" - Lv: "+level+" - TM: "+tmNum+" - prEvo: "+prEvo+" - Source: "+source;
    }
}
