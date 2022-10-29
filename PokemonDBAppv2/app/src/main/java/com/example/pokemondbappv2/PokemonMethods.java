package com.example.pokemondbappv2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.text.DecimalFormat;

abstract class PokemonMethods {

    /**
     * FIXME
     * @param type
     * @return
     */
    public static Drawable getTypeImg(Resources res, Type type) {
        switch (type) {
            case NOR:
                return ResourcesCompat.getDrawable(res, R.drawable.type_normal, null);
            case FIR:
                return ResourcesCompat.getDrawable(res, R.drawable.type_fire, null);
            case WAT:
                return ResourcesCompat.getDrawable(res, R.drawable.type_water, null);
            case GRA:
                return ResourcesCompat.getDrawable(res, R.drawable.type_grass, null);
            case ELE:
                return ResourcesCompat.getDrawable(res, R.drawable.type_electric, null);
            case ICE:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ice, null);
            case FIG:
                return ResourcesCompat.getDrawable(res, R.drawable.type_fighting, null);
            case POI:
                return ResourcesCompat.getDrawable(res, R.drawable.type_poison, null);
            case GRO:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ground, null);
            case FLY:
                return ResourcesCompat.getDrawable(res, R.drawable.type_flying, null);
            case PSY:
                return ResourcesCompat.getDrawable(res, R.drawable.type_psychic, null);
            case BUG:
                return ResourcesCompat.getDrawable(res, R.drawable.type_bug, null);
            case ROC:
                return ResourcesCompat.getDrawable(res, R.drawable.type_rock, null);
            case GHO:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ghost, null);
            case DRA:
                return ResourcesCompat.getDrawable(res, R.drawable.type_dragon, null);
            default:
                return null;
        }
    }

    /**
     * FIXME
     * @param evoLvl
     * @return
     */
    public static Drawable getEvoArrow(Resources res, int evoLvl) {
        switch (evoLvl) {
            case 7:
                return ResourcesCompat.getDrawable(res, R.drawable.l_07, null);
            case 10:
                return ResourcesCompat.getDrawable(res, R.drawable.l_10, null);
            case 16:
                return ResourcesCompat.getDrawable(res, R.drawable.l_16, null);
            case 18:
                return ResourcesCompat.getDrawable(res, R.drawable.l_18, null);
            case 20:
                return ResourcesCompat.getDrawable(res, R.drawable.l_20, null);
            case 21:
                return ResourcesCompat.getDrawable(res, R.drawable.l_21, null);
            case 22:
                return ResourcesCompat.getDrawable(res, R.drawable.l_22, null);
            case 24:
                return ResourcesCompat.getDrawable(res, R.drawable.l_24, null);
            case 25:
                return ResourcesCompat.getDrawable(res, R.drawable.l_25, null);
            case 26:
                return ResourcesCompat.getDrawable(res, R.drawable.l_26, null);
            case 28:
                return ResourcesCompat.getDrawable(res, R.drawable.l_28, null);
            case 30:
                return ResourcesCompat.getDrawable(res, R.drawable.l_30, null);
            case 31:
                return ResourcesCompat.getDrawable(res, R.drawable.l_31, null);
            case 32:
                return ResourcesCompat.getDrawable(res, R.drawable.l_32, null);
            case 33:
                return ResourcesCompat.getDrawable(res, R.drawable.l_33, null);
            case 34:
                return ResourcesCompat.getDrawable(res, R.drawable.l_34, null);
            case 35:
                return ResourcesCompat.getDrawable(res, R.drawable.l_35, null);
            case 36:
                return ResourcesCompat.getDrawable(res, R.drawable.l_36, null);
            case 37:
                return ResourcesCompat.getDrawable(res, R.drawable.l_37, null);
            case 38:
                return ResourcesCompat.getDrawable(res, R.drawable.l_38, null);
            case 40:
                return ResourcesCompat.getDrawable(res, R.drawable.l_40, null);
            case 42:
                return ResourcesCompat.getDrawable(res, R.drawable.l_42, null);
            case 55:
                return ResourcesCompat.getDrawable(res, R.drawable.l_55, null);
            case 101:
                return ResourcesCompat.getDrawable(res, R.drawable.l_trade, null);
            case 102:
                return ResourcesCompat.getDrawable(res, R.drawable.l_thunderstone, null);
            case 103:
                return ResourcesCompat.getDrawable(res, R.drawable.l_moonstone, null);
            case 104:
                return ResourcesCompat.getDrawable(res, R.drawable.l_firestone, null);
            case 105:
                return ResourcesCompat.getDrawable(res, R.drawable.l_leafstone, null);
            case 106:
                return ResourcesCompat.getDrawable(res, R.drawable.l_waterstone, null);
            default:
                return null;
        }
    }

    /**
     * FIXME
     * @param tmNum
     * @return
     */
    public static String getTmString(int tmNum) {
        DecimalFormat format;

        if (tmNum == 0) {
            return "None";
        }
        else if (tmNum > 0) {
            format = new DecimalFormat("00");
            return "TM" + format.format(tmNum);
        }
        else {
            return "HM0" + tmNum;
        }

    }

}
