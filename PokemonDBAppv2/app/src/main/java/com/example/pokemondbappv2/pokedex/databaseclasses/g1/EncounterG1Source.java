package com.example.pokemondbappv2.pokedex.databaseclasses.g1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.pokemondbappv2.pokedex.databaseclasses.g1.EncounterG1Helper.*;

import java.util.ArrayList;

public class EncounterG1Source {

    private final EncounterG1Helper encounterHelperG1;
    private static SQLiteDatabase db;
    private final String[] columns = new String[] {
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
                        dexNum,
                        cur.getString(1),   // Location Name
                        cur.getString(2),   // Sub-Location Name
                        cur.getString(3),   // Method
                        cur.getInt(4),      // Chance
                        cur.getInt(5),      // Min Level
                        cur.getInt(6),      // Max Level
                        cur.getString(7)    // Version
                );
                encounterG1Entries.add(tempEntry);

                if (!cur.moveToNext())
                    done = true;
            }
        }
        return encounterG1Entries;
    }

    public boolean addEncounter (EncounterG1Entry encounter) {
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

        for (int i = 0; i < columns.length; i++) {
            encounterValues.put(columns[i], String.valueOf(encounterObjects[i]));
        }
        db.insert(TABLE_NAME, null, encounterValues);
        return true;
    }
}
