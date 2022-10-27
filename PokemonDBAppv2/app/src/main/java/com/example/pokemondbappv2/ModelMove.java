package com.example.pokemondbappv2;

public class ModelMove {
    private String name, effect;
    private int pp, power, details, effectRate, tmNum, priority, target;
    private double accuracy;
    private Type type;

    public String getName() { return name; }
    public void setName(String name) {this.name = name;}

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

    public String getEffect() {return effect;}
    public void setEffect(String effect) {this.effect = effect;}

    public int getPp() {return pp;}
    public void setPp(int pp) {this.pp = pp;}

    public int getPower() {return power;}
    public void setPower(int power) {this.power = power;}

    public int getDetails() {return details;}
    public void setDetails(int details) {this.details = details;}

    public int getEffectRate() {return effectRate;}
    public void setEffectRate(int effectRate) {this.effectRate = effectRate;}

    public int getTmNum() {return tmNum;}
    public void setTmNum(int tmNum) {this.tmNum = tmNum;}

    public int getPriority() {return priority;}
    public void setPriority(int priority) {this.priority = priority;}

    public int getTarget() {return target;}
    public void setTarget(int target) {this.target = target;}

    public double getAccuracy() {return accuracy;}
    public void setAccuracy(double accuracy) {this.accuracy = accuracy;}

    public ModelMove() {
        name = "";
        type = Type.NON;
        pp = 0;
        power = 0;
        accuracy = 0;
        effect = "";
        tmNum = 0;
        priority = 0;
        target = 0;
    }

    public ModelMove(String name, Type type, int pp, int pwr, double acc, String effect, int details,
                     int rate, int tm, int prior, int target) {
        this.name = name;
        this.type = type;
        this.pp = pp;
        this.power = pwr;
        this.accuracy = acc;
        this.effect = effect;
        this.details = details;
        this.effectRate = rate;
        this.tmNum = tm;
        this.priority = prior;
        this.target = target;
    }
}
