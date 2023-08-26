package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.*;

import com.example.pokemondbappv2.pokeEnums.Type;
import com.example.pokemondbappv2.pokeEnums.XpGrowth;

public class PokemonSourceG1 {

    private final PokemonHelperG1 pokemonHelperG1;
    private final SQLiteDatabase db;
    private final String[] columns = new String[] {
            DEX_NUM,
            NAME,

            SPRITE1,
            SPRITE2,

            NAME_JP,
            NAME_JP_ENG,
            NAME_FR,
            NAME_GER,
            NAME_KOR,

            TYPE_1,
            TYPE_2,

            CLASS,
            HEIGHT,
            WEIGHT,
            CAP_RATE,
            XP_RATE,

            BASE_HP,
            MIN_HP_50,
            MAX_HP_50,
            MIN_HP_100,
            MAX_HP_100,

            BASE_ATK,
            MIN_ATK_50,
            MAX_ATK_50,
            MIN_ATK_100,
            MAX_ATK_100,

            BASE_DEF,
            MIN_DEF_50,
            MAX_DEF_50,
            MIN_DEF_100,
            MAX_DEF_100,

            BASE_SPC,
            MIN_SPC_50,
            MAX_SPC_50,
            MIN_SPC_100,
            MAX_SPC_100,

            BASE_SPE,
            MIN_SPE_50,
            MAX_SPE_50,
            MIN_SPE_100,
            MAX_SPE_100,

            ID
    };

    public PokemonSourceG1(Context context) {
        pokemonHelperG1 = new PokemonHelperG1(context);
        db = pokemonHelperG1.getWritableDatabase();
    }

    public void close() {
        db.close();
        pokemonHelperG1.close();
    }

    public PokemonEntryG1 getPokemonEntry (int dexNum) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
        Cursor cur = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        PokemonEntryG1 pokemon;

        if (cur.moveToFirst()) {
            pokemon = new PokemonEntryG1(
                    dexNum,
                    cur.getString(1), // Name
                    cur.getString(2), // Sprite 1
                    cur.getString(3), // Sprite 2
                    cur.getString(4), // Japanese Name
                    cur.getString(5), // Japanese-English Name
                    cur.getString(6), // French Name
                    cur.getString(7), // German Name
                    cur.getString(8), // Korean Name
                    Type.checkType(cur.getString(9)), // Type 1
                    Type.checkType(cur.getString(10)), // Type 2
                    cur.getString(11), // Classification
                    cur.getInt(12), // Height
                    cur.getInt(13), // Weight
                    cur.getInt(14), // Capture Rate
                    XpGrowth.checkName(cur.getString(15)), // XP Growth
                    cur.getInt(16), // Base HP
                    cur.getInt(17),
                    cur.getInt(18),
                    cur.getInt(19),
                    cur.getInt(20),
                    cur.getInt(21),
                    cur.getInt(22),
                    cur.getInt(23),
                    cur.getInt(24),
                    cur.getInt(25),
                    cur.getInt(26),
                    cur.getInt(27),
                    cur.getInt(28),
                    cur.getInt(29),
                    cur.getInt(30),
                    cur.getInt(31),
                    cur.getInt(32),
                    cur.getInt(33),
                    cur.getInt(34),
                    cur.getInt(35),
                    cur.getInt(36),
                    cur.getInt(37),
                    cur.getInt(38),
                    cur.getInt(39),
                    cur.getInt(40),
                    cur.getInt(41)
            );
            cur.close();
            return pokemon;
        }

        return null;
    }

    public boolean addPokemon (PokemonEntryG1 pokemon) {
        if (!isCached(pokemon.getDexNum())) {
            ContentValues pokemonValues = new ContentValues();

            Object[] pokemonObjects = new Object[]{
                    pokemon.getDexNum(),
                    pokemon.getName(),
                    pokemon.getSprite1(),
                    pokemon.getSprite2(),

                    pokemon.getNameJp(),
                    pokemon.getNameJpEng(),
                    pokemon.getNameFr(),
                    pokemon.getNameGer(),
                    pokemon.getNameKor(),

                    pokemon.getType1().getName(),
                    pokemon.getType2().getName(),

                    pokemon.getClassification(),
                    pokemon.getHeight(),
                    pokemon.getWeight(),
                    pokemon.getCapRate(),
                    pokemon.getXpRate().getName(),

                    pokemon.getBaseHp(),
                    pokemon.getMinHp50(),
                    pokemon.getMaxHp50(),
                    pokemon.getMinHp100(),
                    pokemon.getMaxHp100(),

                    pokemon.getBaseAtk(),
                    pokemon.getMinAtk50(),
                    pokemon.getMaxAtk50(),
                    pokemon.getMinAtk100(),
                    pokemon.getMaxAtk100(),

                    pokemon.getBaseDef(),
                    pokemon.getMinDef50(),
                    pokemon.getMaxDef50(),
                    pokemon.getMinDef100(),
                    pokemon.getMaxDef100(),

                    pokemon.getBaseSpc(),
                    pokemon.getMinSpc50(),
                    pokemon.getMaxSpc50(),
                    pokemon.getMinSpc100(),
                    pokemon.getMaxSpc100(),

                    pokemon.getBaseSpe(),
                    pokemon.getMinSpe50(),
                    pokemon.getMaxSpe50(),
                    pokemon.getMinSpe100(),
                    pokemon.getMaxSpe100()
            };
            for (int i = 0; i < columns.length - 1; i++) {
                pokemonValues.put(columns[i], String.valueOf(pokemonObjects[i]));
            }
            db.insert(TABLE_NAME, null, pokemonValues);

            //TODO insert encounter locations into encountersG1 db table
            return true;
        }
        return false;
    }

    public boolean isCached(int dexNum) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        return cursor.moveToFirst();
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }
}
