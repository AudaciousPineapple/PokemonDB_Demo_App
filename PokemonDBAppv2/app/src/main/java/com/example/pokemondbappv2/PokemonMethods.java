package com.example.pokemondbappv2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.example.pokemondbappv2.pokeEnums.Type;
import com.example.pokemondbappv2.pokeEnums.XpGrowth;
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonEntryG1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public abstract class PokemonMethods {

    public static int[] specialStats = new int[] {
            65, 80, 100,    // 1-3
            50, 65, 85,     // 4-6
            50, 65, 85,     // 7-9
            20, 25, 80,     // 10-12
            20, 25, 45,     // 13-15
            35, 50, 70,     // 16-18
            25, 50,         // 19-20
            31, 61,         // 21-22
            40, 65,         // 23-24
            50, 90,         // 25-26
            30, 55,         // 27-28
            40, 55, 75,     // 29-31
            40, 55, 75,     // 32-34
            60, 85,         // 35-36
            65, 100,        // 37-38
            25, 50,         // 39-40
            40, 75,         // 41-42
            75, 85, 100,    // 43-45
            55, 80,         // 46-47
            40, 90,         // 48-49
            45, 70,         // 50-51
            40, 65,         // 52-53
            50, 80,         // 54-55
            35, 60,         // 56-57
            50, 80,         // 58-59
            40, 50, 70,     // 60-62
            105, 120, 135,  // 63-65
            35, 50, 65,     // 66-68
            70, 85, 100,    // 69-71
            100, 120,       // 72-73
            30, 45, 55,     // 74-76
            65, 80,         // 77-78
            40, 80,         // 79-80
            95, 120,        // 81-82
            58,             // 83
            35, 60,         // 84-85
            70, 95,         // 86-87
            40, 65,         // 88-89
            45, 85,         // 90-91
            100, 115, 130,  // 92-94
            30,             // 95
            90, 115,        // 96-97
            25, 50,         // 98-99
            55, 80,         // 100-101
            60, 125,        // 102-103
            40, 50,         // 104-105
            35,             // 106
            35,             // 107
            60,             // 108
            60, 85,         // 109-110
            30, 45,         // 111-112
            105,            // 113
            100,            // 114
            40,             // 115
            70, 95,         // 116-117
            50, 80,         // 118-119
            70, 100,        // 120-121
            100,            // 122
            55,             // 123
            95,             // 124
            85,             // 125
            85,             // 126
            55,             // 127
            70,             // 128
            20, 100,        // 129-130
            95,             // 131
            48,             // 132
            65, 110, 110, 110, // 133-136
            75,             // 137
            90, 115,        // 138-139
            45, 70,         // 140-141
            60,             // 142
            65,             // 143
            125,            // 144
            125,            // 145
            125,            // 146
            50, 70, 100,    // 147-149
            154,            // 150
            100             // 151
    };

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
     * FIXME
     * @param name
     * @return
     */
    public static String fixLocationNameG1(String name) {
        String locName = name.substring(0, 1).toUpperCase() + name.substring(1);
        int idx = 0;
        while (locName.indexOf('-', idx) != -1) {
            idx = locName.indexOf('-');
            locName = locName.substring(0, idx) + ' '
                    + locName.substring(idx+1, idx+2).toUpperCase()
                    + locName.substring(idx+2);
        }
        locName = locName.replace("Kanto ", "");
        return locName;
    }

    /**
     * setTypeImage method - Sets the image for type based on the provided type
     * @param res - Resources
     * @param type - The type you wish to set the image for
     * @param img - The ImageView you wish to update
     */
    public static boolean setTypeImage(Resources res, Type type, ImageView img) {
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
            case DAR:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_dark, null);
                break;
            case STE:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_steel, null);
                break;
            case FAI:
                typeImg = ResourcesCompat.getDrawable(res, R.drawable.type_fairy, null);
                break;
            default:
                return false;
        }
        img.setImageDrawable(typeImg);
        return true;
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

    public static String formatXpString(XpGrowth xpGrowth) {
        return xpGrowth.getMax() + " | " + xpGrowth.getName();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bArray = stream.toByteArray();
            stream.close();
            return bArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getStatRangesG1G2(PokemonEntryG1 pokemon, int level) {
        int minHp = getHpMaxG1G2(pokemon.getBaseHp(), level);
        int maxHp = getHpMinG1G2(pokemon.getBaseHp(), level);
        int minAtk = getNotHpMinG1G2(pokemon.getBaseAtk(), level);
        int maxAtk = getNotHpMaxG1G2(pokemon.getBaseAtk(), level);
        int minDef = getNotHpMinG1G2(pokemon.getBaseDef(), level);
        int maxDef = getNotHpMaxG1G2(pokemon.getBaseDef(), level);
        int minSpc = getNotHpMinG1G2(pokemon.getBaseSpc(), level);
        int maxSpc = getNotHpMaxG1G2(pokemon.getBaseSpc(), level);
        int minSpe = getNotHpMinG1G2(pokemon.getBaseSpe(), level);
        int maxSpe = getNotHpMaxG1G2(pokemon.getBaseSpe(), level);

        return new int[]
                { minHp, maxHp, minAtk, maxAtk, minDef, maxDef, minSpc, maxSpc, minSpe, maxSpe };
    }

    public static int getHpMinG1G2(int baseHp, int level) {
        return (((baseHp * 2 * level) / 100)) + level + 10;
    }

    public static int getHpMaxG1G2(int baseHp, int level) {
        return ((((baseHp + 15) * 2 + 256 / 4) * level) / 100) + level + 10;
    }

    public static int getNotHpMinG1G2(int baseStat, int level) {
        return ((baseStat * 2 * level) / 100) + 5;
    }

    public static int getNotHpMaxG1G2(int baseStat, int level) {
        return ((((baseStat + 15) * 2 + 256 / 4) * level) / 100) + 5;
    }

}
