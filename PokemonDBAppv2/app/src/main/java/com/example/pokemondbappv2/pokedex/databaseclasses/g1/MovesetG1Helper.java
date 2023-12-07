package com.example.pokemondbappv2.pokedex.databaseclasses.g1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.Scanner;

public class MovesetG1Helper extends SQLiteOpenHelper {

    // Table Name
    public static final String DATABASE_NAME = "PokemonDB.db";
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE_SQL = "PokemonDB.Create.sql";
    private static final String DATABASE_DESTROY_SQL = "PokemonDB.Destroy.sql";
    private Context mContext;

    // Table Columns
    public static final String TABLE_NAME = "movesetsG1";
    public static final String DEX_NUM = "dexNum";
    public static final String NAME = "name";
    public static final String POS = "pos";
    public static final String METHOD = "method";
    public static final String IS_YELLOW = "isYellow";
    public static final String LEVEL = "level";
    public static final String TM_NUM = "tmNum";
    public static final String PREVO = "prEvo";
    public static final String SOURCE = "source";
    public static final String ID = "_id";

    public MovesetG1Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        executeSqlFile(db, DATABASE_CREATE_SQL, DATABASE_NAME + " creation failed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        executeSqlFile(db, DATABASE_DESTROY_SQL, DATABASE_NAME + " destruction failed");
    }

    private void executeSqlFile(SQLiteDatabase db, String databaseCreateSql, String s) {
        db.beginTransaction();

        try (Scanner scan = new Scanner(mContext.getAssets().open(databaseCreateSql))) {

            for (scan.useDelimiter(":"); scan.hasNext();) {
                String sql = scan.next().trim();

                if (!sql.isEmpty())
                    db.execSQL(sql);
            }
            db.setTransactionSuccessful();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
        }
    }
}
