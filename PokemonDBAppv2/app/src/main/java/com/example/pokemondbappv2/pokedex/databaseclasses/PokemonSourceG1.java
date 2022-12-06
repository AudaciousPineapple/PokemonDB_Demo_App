package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.TABLE_NAME;
import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.DEX_NUM;

import androidx.annotation.NonNull;

import com.example.pokemondbappv2.pokeEnums.Type;

public class PokemonSourceG1 {

    private final PokemonHelperG1 pokemonHelperG1;
    private final SQLiteDatabase db;

    public PokemonSourceG1(Context context) {
        pokemonHelperG1 = new PokemonHelperG1(context);
        db = pokemonHelperG1.getWritableDatabase();
    }

    public void close() {
        db.close();
        pokemonHelperG1.close();
    }

    public boolean insertPokemon (int dexNum, String name, String nameJp, String nameJpEng,
                                  String nameFr, String nameGer, String nameKor, Type type1,
                                  Type type2, String classification, int height, int weight,
                                  int capRate, String xpRate, int baseHp, int baseAtk, int baseDef,
                                  int baseSpc, int baseSpe) {

        ContentValues values = new ContentValues();

        values.put(PokemonHelperG1.DEX_NUM, dexNum);
        values.put(PokemonHelperG1.NAME, name);
        values.put(PokemonHelperG1.NAME_JP, nameJp);
        values.put(PokemonHelperG1.NAME_JP_ENG, nameJpEng);
        values.put(PokemonHelperG1.NAME_FR, nameFr);
        values.put(PokemonHelperG1.NAME_GER, nameGer);
        values.put(PokemonHelperG1.NAME_KOR, nameKor);
        values.put(PokemonHelperG1.CLASS, classification);
        values.put(PokemonHelperG1.TYPE_1, String.valueOf(type1));
        values.put(PokemonHelperG1.TYPE_2, String.valueOf(type2));
        values.put(PokemonHelperG1.HEIGHT, height);
        values.put(PokemonHelperG1.WEIGHT, weight);
        values.put(PokemonHelperG1.CAP_RATE, capRate);
        values.put(PokemonHelperG1.XP_RATE, xpRate);
        values.put(PokemonHelperG1.BASE_HP, baseHp);
        values.put(PokemonHelperG1.BASE_ATK, baseAtk);
        values.put(PokemonHelperG1.BASE_DEF, baseDef);
        values.put(PokemonHelperG1.BASE_SPC, baseSpc);
        values.put(PokemonHelperG1.BASE_SPE, baseSpe);

        return db.insert(PokemonHelperG1.TABLE_NAME, null, values) != -1;
    }

    public PokemonEntryG1 getPokemonEntry (int dexNum) {
        if (db.isOpen()) {
            String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
            Cursor cur = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

            PokemonEntryG1 pokemon = new PokemonEntryG1(
                    dexNum,
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getString(6),
                    Type.checkType(cur.getString(7)),
                    Type.checkType(cur.getString(8)),
                    cur.getString(9),
                    cur.getInt(10),
                    cur.getInt(11),
                    cur.getInt(12),
                    cur.getString(13),
                    cur.getInt(14),
                    cur.getInt(15),
                    cur.getInt(16),
                    cur.getInt(17),
                    cur.getInt(18),
                    cur.getInt(0)
            );


        }
        return null; //FIXME
    }

    public int removeData() {
        return db.delete(TABLE_NAME, null, null);
    }

    public boolean isCached(int dexNum) {

        if (db.isOpen()) {
            String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
            Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

            if (cursor.moveToFirst())
                return true;

            return false;
        }
        else
            throw new RuntimeException("Can't query a closed database.");
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }
}
