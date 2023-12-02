package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.Scanner;

public class EncounterG1Helper extends SQLiteOpenHelper {

    public static final String TAG = EncounterG1Helper.class.getSimpleName();

    // Database Name
    public static final String DATABASE_NAME = "PokemonDB.db";
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE_SQL = "PokemonDB.Create.sql";
    private static final String DATABASE_DESTROY_SQL = "PokemonDB.Destroy.sql";
    private Context mContext;

    // Table Name
    public static final String TABLE_NAME = "encountersG1";

    // table columns
    public static final String DEX_NUM = "dexNum";
    public static final String LOC_NAME = "locName";
    public static final String SUB_LOC_NAME = "subLocName";
    public static final String METHOD = "method";
    public static final String CHANCE = "chance";
    public static final String MIN_LEVEL = "minLevel";
    public static final String MAX_LEVEL = "maxLevel";
    public static final String VERSION = "version";

    public static final String ID = "_id";

    public EncounterG1Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        executeSqlFile(db, DATABASE_CREATE_SQL, DATABASE_NAME + " creation failed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        executeSqlFile(db, DATABASE_DESTROY_SQL, DATABASE_NAME + " destruction failed");
    }

    private void executeSqlFile(SQLiteDatabase db, String fileName, String errorMsg) {
        db.beginTransaction();

        try (Scanner scan = new Scanner(mContext.getAssets().open(fileName))) {

            for (scan.useDelimiter(";"); scan.hasNext();) {
                String sql = scan.next().trim();

                if(!sql.isEmpty())
                    db.execSQL(sql);
            }
            db.setTransactionSuccessful();

        } catch (IOException e) {
            throw new RuntimeException(errorMsg + ": " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
    }
}
