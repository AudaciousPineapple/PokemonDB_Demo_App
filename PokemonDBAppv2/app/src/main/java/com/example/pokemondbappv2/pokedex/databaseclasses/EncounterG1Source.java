package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.pokemondbappv2.pokedex.databaseclasses.EncounterG1Helper.*;
import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.DEX_NUM;
import static com.example.pokemondbappv2.pokedex.databaseclasses.PokemonHelperG1.TABLE_NAME;

import java.util.ArrayList;

public class EncounterG1Source {

    private final EncounterG1Helper encounterHelperG1;
    private static SQLiteDatabase db;
    private final String[] columns = new String[] {
            ID,
            DEX_NUM,
            LOC_NAME,
            SUB_LOC_NAME,
            METHOD,
            CHANCE,
            MIN_LEVEL,
            MAX_LEVEL,
            VERSION
    };

    public EncounterG1Source(Context context) {
        encounterHelperG1 = new EncounterG1Helper(context);
        db = encounterHelperG1.getWritableDatabase();
    }

    public void close() {
        db.close();
        encounterHelperG1.close();
    }

    public ArrayList<EncounterG1Entry> getEncounterG1Entries (int dexNum) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
        Cursor cur = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        ArrayList<EncounterG1Entry> encounterG1Entries = new ArrayList<EncounterG1Entry>();
        EncounterG1Entry tempEntry;
        boolean done = false;

        if (cur.moveToFirst()) {
            while (!done) {
                tempEntry = new EncounterG1Entry(
                        cur.getInt(0),      // ID
                        dexNum,
                        cur.getString(2),   // Location Name
                        cur.getString(3),   // Sub-Location Name
                        cur.getString(4),   // Method
                        cur.getInt(5),      // Chance
                        cur.getInt(6),      // Min Level
                        cur.getInt(7),      // Max Level
                        cur.getString(8)    // Version
                );
                encounterG1Entries.add(tempEntry);

                if (!cur.moveToNext())
                    done = true;
            }
        }
        return encounterG1Entries;
    }

    public boolean addEncounter (EncounterG1Entry encounter) {
        if (!isCached(encounter.getDexNum())) {
            ContentValues encounterValues = new ContentValues();

            Object[] encounterObjects = new Object[]{
                    encounter.getDexNum(),
                    encounter.getLocName(),
                    encounter.getSubLocName(),
                    encounter.getMethod(),
                    encounter.getChance(),
                    encounter.getMinLevel(),
                    encounter.getMaxLevel(),
                    encounter.getVersion()
            };

            for (int i = 1; i < columns.length; i++) {
                encounterValues.put(columns[i], String.valueOf(encounterObjects[i]));
            }
            db.insert(TABLE_NAME, null, encounterValues);
            return true;
        }
        return false;
    }

    public boolean isCached(int dexNum) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEX_NUM + " = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        return cursor.moveToFirst();
    }
}
