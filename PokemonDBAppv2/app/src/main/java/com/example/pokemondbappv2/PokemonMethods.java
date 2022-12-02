package com.example.pokemondbappv2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.text.DecimalFormat;

public abstract class PokemonMethods {

    /**
     * fixPokemonName method - Takes the pokemon name returned from the API query and gives it proper capitalization
     * @param name - the name String returned by the API query
     * @return - The corrected String
     */
    public static String fixPokemonName(String name) {
        String pokemonName = name.substring(0, 1).toUpperCase() + name.substring(1);
        if (pokemonName.indexOf('-') != -1) {
            int idx = pokemonName.indexOf('-');
            while (idx != -1) {
                pokemonName = pokemonName.substring(0, idx) + ' '
                        + pokemonName.substring(idx+1, idx+2).toUpperCase()
                        + pokemonName.substring(idx + 2);
                idx = pokemonName.indexOf('-', idx+2);
            }
        }
        return pokemonName;
    }

    /**
     * getTypeImg method - Sets the image for type based on the provided type
     * @param res - Resources
     * @param type - The type you wish to set the image for
     * @param img - The ImageView you wish to update
     */
    public static void getTypeImg(Resources res, Type type, ImageView img) {
        Drawable typeImg;
        switch (type) {
            case NOR:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_normal, null);
                break;
            case FIR:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_fire, null);
                break;
            case WAT:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_water, null);
                break;
            case GRA:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_grass, null);
                break;
            case ELE:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_electric, null);
                break;
            case ICE:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_ice, null);
                break;
            case FIG:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_fighting, null);
                break;
            case POI:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_poison, null);
                break;
            case GRO:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_ground, null);
                break;
            case FLY:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_flying, null);
                break;
            case PSY:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_psychic, null);
                break;
            case BUG:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_bug, null);
                break;
            case ROC:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_rock, null);
                break;
            case GHO:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_ghost, null);
                break;
            case DRA:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_dragon, null);
                break;
            default:
                return;
        }
        img.setImageDrawable(typeImg);
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
