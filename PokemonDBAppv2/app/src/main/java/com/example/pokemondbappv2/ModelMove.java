package com.example.pokemondbappv2;

public class ModelMove {
    private String name, effect;
    private int pp, power, details, effectRate, tmNum, priority;
    private double accuracy;
    private Type type;
    private Target target;

    public String getName() { return name; }

    public Type getType() {return type;}

    public String getEffect() {return effect;}

    public int getPp() {return pp;}

    public int getPower() {return power;}

    public int getDetails() {return details;}

    public int getEffectRate() {return effectRate;}

    public int getTmNum() {return tmNum;}

    public int getPriority() {return priority;}

    public Target getTarget() {return target;}

    public double getAccuracy() {return accuracy;}

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
        this.target = Target.indexToTarget(target);
    }
}
