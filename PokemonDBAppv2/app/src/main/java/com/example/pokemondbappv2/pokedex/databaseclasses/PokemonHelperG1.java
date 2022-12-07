package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PokemonHelperG1 extends SQLiteOpenHelper {

    public static final String TAG = PokemonHelperG1.class.getSimpleName();

    // Database Name
    public static final String DATABASE_NAME = "PokemonDB.db";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_SQL = "PokemonDB.Create.sql";

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

    public PokemonHelperG1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
}
