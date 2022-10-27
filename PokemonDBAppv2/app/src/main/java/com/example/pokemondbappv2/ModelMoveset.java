package com.example.pokemondbappv2;

public class ModelMoveset {

    private int dexNum, pos, level, prEvo;
    private String moveName, source;
    private boolean isYellow;

    public ModelMoveset(int dexNum, int pos, int level, String moveName, boolean isYellow,
                        int prEvo, String source) {
        this.dexNum = dexNum;
        this.pos = pos;
        this.level = level;
        this.moveName = moveName;
        this.isYellow = isYellow;
        this.prEvo = prEvo;
        this.source = source;
    }

    public int getDexNum() { return dexNum; }
    public int getPos() { return pos; }
    public int getLevel() { return level; }
    public String getMoveName() { return moveName; }
    public boolean isYellow() { return isYellow; }
    public int getPrEvo() { return prEvo; }
    public String getSource() { return source; }
}
