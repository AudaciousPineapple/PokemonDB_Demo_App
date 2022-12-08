package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.*;

import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.pokeEnums.Type;
import com.example.pokemondbappv2.pokeEnums.XpGrowth;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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
            BASE_ATK,
            BASE_DEF,
            BASE_SPC,
            BASE_SPE,
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
                    cur.getString(2),
                    cur.getBlob(3),
                    cur.getBlob(4),
                    cur.getString(5),
                    cur.getString(6),
                    cur.getString(7),
                    cur.getString(8),
                    cur.getString(9),
                    Type.checkType(cur.getString(10)),
                    Type.checkType(cur.getString(11)),
                    cur.getString(12),
                    cur.getInt(13),
                    cur.getInt(14),
                    cur.getInt(15),
                    XpGrowth.checkName(cur.getString(16)),
                    cur.getInt(17),
                    cur.getInt(18),
                    cur.getInt(19),
                    cur.getInt(20),
                    cur.getInt(21),
                    cur.getInt(0)
            );
            cur.close();
            return pokemon;
        }

        return null;
    }

    public boolean addPokemon (PokemonEntryG1 pokemon) {
        if (!isCached(pokemon.getDexNum())) {
            ContentValues pokemonValues = new ContentValues();
            ByteArrayOutputStream spr1 = new ByteArrayOutputStream();
            ByteArrayOutputStream spr2 = new ByteArrayOutputStream();


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
                    pokemon.getBaseAtk(),
                    pokemon.getBaseDef(),
                    pokemon.getBaseSpc(),
                    pokemon.getBaseSpe()
            };
            for (int i = 0; i < columns.length - 1; i++) {
                if (i == 2 || i == 3) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bmp = (Bitmap) pokemonObjects[i];
                    Log.d("**TESTING**", stream.toString());
                    pokemonValues.put(columns[i], stream.toByteArray());
                }
                else
                    pokemonValues.put(columns[i], String.valueOf(pokemonObjects[i]));
            }
            db.insert(TABLE_NAME, null, pokemonValues);
            return true;
        }
        return false;
    }

    public boolean isCached(int dexNum) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        if (cursor.moveToFirst())
            return true;

        return false;

    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }
}
