package com.example.pokemondbappv2.pokedex.databaseclasses.g1;

import static com.example.pokemondbappv2.pokedex.databaseclasses.g1.MovesetG1Helper.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MovesetG1Source {

    private final MovesetG1Helper movesetG1Helper;
    private static SQLiteDatabase db;
    private final String[] columns = new String[] {
            DEX_NUM,
            NAME,
            POS,
            METHOD,
            IS_YELLOW,
            LEVEL,
            TM_NUM,
            PREVO,
            SOURCE
    };

    public MovesetG1Source(Context context) {
        movesetG1Helper = new MovesetG1Helper(context);
        db = movesetG1Helper.getWritableDatabase();
    }

    public void close() {
        db.close();
        movesetG1Helper.close();
    }

    public ArrayList<MovesetG1Entry> getMovesetList (int dexNum) {
        String sqlQuery = "SELECT * FROM "+TABLE_NAME+" WHERE "+DEX_NUM+" = ?";
        Cursor cur = db.rawQuery(sqlQuery, new String[]{String.valueOf(dexNum)});

        ArrayList<MovesetG1Entry> list = new ArrayList<>();
        MovesetG1Entry tempEntry;
        boolean done = false;

        if (cur.moveToFirst()) {
            while (!done) {
                tempEntry = new MovesetG1Entry(
                        dexNum,
                        cur.getString(1),
                        cur.getInt(2),
                        cur.getInt(3),
                        1 == cur.getInt(4),
                        cur.getInt(5),
                        cur.getInt(6),
                        cur.getInt(7),
                        cur.getString(8)
                );
                list.add(tempEntry);

                if (!cur.moveToNext())
                    done = true;
            }
        }
        return list;
    }

    public boolean addMoveset (MovesetG1Entry move) {
        ContentValues movesetValues = new ContentValues();

        Object[] movesetObjects = new Object[] {
                move.getDexNum(),
                move.getName(),
                move.getPos(),
                move.getMethod(),
                move.isYellow(),
                move.getLevel(),
                move.getTmNum(),
                move.getPrEvo(),
                move.getSource()
        };

        for (int i = 0; i < columns.length; i++) {
            movesetValues.put(columns[i], String.valueOf(movesetObjects[i]));
        }
        db.insert(TABLE_NAME, null, movesetValues);
        return true;
    }
}
