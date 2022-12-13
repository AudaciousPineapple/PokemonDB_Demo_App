package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.Scanner;

public class PokemonHelperG1 extends SQLiteOpenHelper {

    public static final String TAG = PokemonHelperG1.class.getSimpleName();

    // Database Name
    public static final String DATABASE_NAME = "PokemonDB.db";
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE_SQL = "PokemonDB.Create.sql";
    private static final String DATABASE_DESTROY_SQL = "PokemonDB.Destroy.sql";
    private Context mContext;

    // Table name
    public static final String TABLE_NAME = "pokemonG1";

    // table columns
    public static final String ID = "_id";
    public static final String DEX_NUM = "dexNum";
    public static final String NAME = "name";
    public static final String SPRITE1 = "sprite1";
    public static final String SPRITE2 = "sprite2";
    public static final String NAME_JP = "nameJp";
    public static final String NAME_JP_ENG = "nameJpEng";
    public static final String NAME_FR = "nameFr";
    public static final String NAME_GER = "nameGer";
    public static final String NAME_KOR = "nameKor";
    public static final String TYPE_1 = "type1";
    public static final String TYPE_2 = "type2";
    public static final String CLASS = "class";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String CAP_RATE = "capRate";
    public static final String XP_RATE = "xpRate";
    public static final String BASE_HP = "baseHP";
    public static final String BASE_ATK = "baseAtk";
    public static final String BASE_DEF = "baseDef";
    public static final String BASE_SPC = "baseSpc";
    public static final String BASE_SPE  = "baseSpe";

    public static final String MIN_HP_50 = "minHp50";
    public static final String MAX_HP_50 = "maxHp50";
    public static final String MIN_HP_100 = "minHp100";
    public static final String MAX_HP_100 = "maxHp100";
    public static final String MIN_ATK_50 = "minAtk50";
    public static final String MAX_ATK_50 = "maxAtk50";
    public static final String MIN_ATK_100 = "minAtk100";
    public static final String MAX_ATK_100 = "maxAtk100";
    public static final String MIN_DEF_50 = "minDef50";
    public static final String MAX_DEF_50 = "maxDef50";
    public static final String MIN_DEF_100 = "minDef100";
    public static final String MAX_DEF_100 = "maxDef100";
    public static final String MIN_SPC_50 = "minSpc50";
    public static final String MAX_SPC_50 = "maxSpc50";
    public static final String MIN_SPC_100 = "minSpc100";
    public static final String MAX_SPC_100 = "maxSpc100";
    public static final String MIN_SPE_50 = "minSpe50";
    public static final String MAX_SPE_50 = "maxSpe50";
    public static final String MIN_SPE_100 = "minSpe100";
    public static final String MAX_SPE_100 = "maxSpe100";

    public PokemonHelperG1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        executeSqlFile(db, DATABASE_CREATE_SQL, DATABASE_NAME + " creation failed");

        /*
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                DEX_NUM + " INTEGER NOT NULL UNIQUE, " +
                NAME + " TEXT NOT NULL, " +
                SPRITE1 + " BLOB NOT NULL, " +
                SPRITE2 + " BLOB NOT NULL, " +
                NAME_JP + " TEXT NOT NULL, " +
                NAME_JP_ENG + " TEXT NOT NULL, " +
                NAME_FR + " TEXT NOT NULL, " +
                NAME_GER + " TEXT NOT NULL, " +
                NAME_KOR + " TEXT NOT NULL, " +
                TYPE_1 + " TEXT NOT NULL, " +
                TYPE_2 + " TEXT NOT NULL, " +
                CLASS + " TEXT NOT NULL, " +
                HEIGHT + " INTEGER NOT NULL, " +
                WEIGHT + " INTEGER NOT NULL, " +
                CAP_RATE + " INTEGER NOT NULL, " +
                XP_RATE + " TEXT NOT NULL, " +
                BASE_HP + " INTEGER NOT NULL, " +
                BASE_ATK + " INTEGER NOT NULL, " +
                BASE_DEF + " INTEGER NOT NULL, " +
                BASE_SPC + " INTEGER NOT NULL, " +
                BASE_SPE + " INTEGER NOT NULL);");

         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        executeSqlFile(db, DATABASE_DESTROY_SQL, DATABASE_NAME + " destruction failed");

        onCreate(db);
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
