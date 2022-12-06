package com.example.pokemondbappv2.pokeEnums;

public enum Type {
    NOR("normal"),
    FIR ("fire"),
    WAT ("water"),
    GRA ("grass"),
    ELE ("electric"),
    ICE ("ice"),
    FIG ("fighting"),
    POI ("poison"),
    GRO ("ground"),
    FLY ("flying"),
    PSY ("psychic"),
    BUG ("bug"),
    ROC ("rock"),
    GHO ("ghost"),
    DRA ("dragon"),
    DAR ("dark"),
    STE ("steel"),
    FAI ("fairy"),
    SHA ("shadow"),
    UNK ("unknown");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public static Type checkType(String type) {
        if (type.contentEquals(NOR.name))
            return NOR;
        if (type.contentEquals(FIR.name))
            return FIR;
        else if (type.contentEquals(WAT.name))
            return WAT;
        else if (type.contentEquals(GRA.name))
            return GRA;
        else if (type.contentEquals(ELE.name))
            return ELE;
        else if (type.contentEquals(ICE.name))
            return ICE;
        else if (type.contentEquals(FIG.name))
            return FIG;
        else if (type.contentEquals(POI.name))
            return POI;
        else if (type.contentEquals(GRO.name))
            return GRO;
        else if (type.contentEquals(FLY.name))
            return FLY;
        else if (type.contentEquals(PSY.name))
            return PSY;
        else if (type.contentEquals(BUG.name))
            return BUG;
        else if (type.contentEquals(ROC.name))
            return ROC;
        else if (type.contentEquals(GHO.name))
            return GHO;
        else if (type.contentEquals(DRA.name))
            return DRA;
        else if (type.contentEquals(DAR.name))
            return DAR;
        else if (type.contentEquals(STE.name))
            return STE;
        else if (type.contentEquals(FAI.name))
            return FAI;
        else if (type.contentEquals(SHA.name))
            return SHA;
        else
            return UNK;
    }

}
