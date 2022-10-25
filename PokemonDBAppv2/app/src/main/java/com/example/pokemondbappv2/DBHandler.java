package com.example.pokemondbappv2;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper{


    private SQLiteDatabase db;
    private static final String DB_NAME = "PokemonDBG1";
    private static final int DB_VERSION = 2;

    // Used for the Enum_Target table
    private static final String TARGET_TABLE_NAME = "Enum_Target";
    private static final String TARGET_ID_COL = "targetId";
    private static final String TARGET_VALUE_COL = "targetValue";

    // Used for the Enum_Type table
    private static final String TYPE_TABLE_NAME = "Enum_Type";
    private static final String TYPE_ID_COL = "typeId";
    private static final String TYPE_NAME_COL = "typeName";

    // Used for the Enum_XP_Growth table
    private static final String XP_TABLE_NAME = "Enum_XP_Growth";
    private static final String GROWTH_ID_COL = "growthId";
    private static final String GROWTH_MAX_COL = "growthMax";
    private static final String GROWTH_NAME_COL = "growthName";

    // Used for the Pokemon Table
    private static final String POKEMON_TABLE_NAME = "'Pokemon'";
    private static final String ID_COL = "dexNum";
    private static final String NAME_COL = "name";
    private static final String TYPE1_COL = "type1";
    private static final String TYPE2_COL = "type2";
    private static final String CLASS_COL = "class";
    private static final String HEIGHT_COL = "height";
    private static final String WEIGHT_COL = "weight";
    private static final String CAP_RATE_COL = "captureRate";
    private static final String XP_GROWTH_COL = "xpGrowth";
    private static final String RED_LOC_COL = "redLocation";
    private static final String BLUE_LOC_COL = "blueLocation";
    private static final String BLUE_JP_LOC_COL = "blueJpLocation";
    private static final String YELLOW_LOC_COL = "yellowLocation";
    private static final String HP_COL = "statHP";
    private static final String ATK_COL = "statATK";
    private static final String DEF_COL = "statDEF";
    private static final String SPC_COL = "statSPC";
    private static final String SPE_COL = "statSPE";
    private static final String EVO_COL = "evo";
    private static final String EVO_LVL_COL = "evoLvl";
    private static final String PREVO_COL = "prEvo";

    // Used for the Moves Table
    private static final String MOVES_TABLE_NAME = "'Moves'";
    private static final String MOVE_NAME_COL = "moveName";
    private static final String MOVE_TYPE_COL = "moveType";
    private static final String PP_COL = "powerPoints";
    private static final String POWER_COL = "basePower";
    private static final String ACC_COL = "accuracy";
    private static final String EFFECT_COL = "battleEffect";
    private static final String DETAIL_EFFECT_COL = "detailEffect";
    private static final String EFFECT_RATE_COL = "effectRate";
    private static final String TM_COL = "tmNum";
    private static final String PRIORITY_COL = "priority";
    private static final String TARGET_COL = "target";

    // Used for the Movesets table
    private static final String MOVESET_TABLE_NAME = "'Movesets'";
    private static final String MOVESET_ID_COL = "id";
    private static final String MOVESET_DEX_COL = "dexNum";
    private static final String MOVESET_POS_COL = "pos";
    private static final String MOVESET_LEVEL_COL = "level";
    private static final String MOVESET_MOVE_COL = "move";
    private static final String MOVESET_YELLOW_COL = "yellow";
    private static final String MOVESET_PREVO_COL = "prEvo";
    private static final String MOVESET_SOURCE_COL = "source";


    public DBHandler (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TARGET_TABLE_NAME + " ("
                + TARGET_ID_COL + " INT PRIMARY KEY, "
                + TARGET_VALUE_COL + " VARCHAR)";

        db.execSQL(query);

        query = "CREATE TABLE " + TYPE_TABLE_NAME + " ("
                + TYPE_ID_COL + " CHAR (3) PRIMARY KEY, "
                + TYPE_NAME_COL + " VARCHAR (10))";

        db.execSQL(query);

        query = "CREATE TABLE " + XP_TABLE_NAME + " ("
                + GROWTH_ID_COL + " VARCHAR (2) PRIMARY KEY, "
                + GROWTH_MAX_COL + " INT, "
                + GROWTH_NAME_COL + " VARCHAR)";

        db.execSQL(query);

        query = "CREATE TABLE " + POKEMON_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY NOT NULL, "
                + NAME_COL + " VARCHAR (10) NOT NULL, "
                + TYPE1_COL + " CHAR (3) NOT NULL REFERENCES Enum_Type (typeId), "
                + TYPE2_COL + " CHAR (3) NOT NULL REFERENCES Enum_Type (typeId), "
                + CLASS_COL + " VARCHAR NOT NULL, "
                + HEIGHT_COL + " DECIMAL (3, 1) NOT NULL, "
                + WEIGHT_COL + " DECIMAL (4, 1) NOT NULL, "
                + CAP_RATE_COL + " INT NOT NULL, "
                + XP_GROWTH_COL + " VARCHAR (2) NOT NULL REFERENCES Enum_XP_Growth (growthId), "
                + RED_LOC_COL + " VARCHAR NOT NULL, "
                + BLUE_LOC_COL + " VARCHAR NOT NULL, "
                + BLUE_JP_LOC_COL + " VARCHAR NOT NULL, "
                + YELLOW_LOC_COL + " VARCHAR NOT NULL, "
                + HP_COL + " INT NOT NULL, "
                + ATK_COL + " INT NOT NULL, "
                + DEF_COL + " INT NOT NULL, "
                + SPC_COL + " INT NOT NULL, "
                + SPE_COL + " INT NOT NULL, "
                + EVO_COL + " INT, "
                + EVO_LVL_COL + " INT, "
                + PREVO_COL + " INT)";

        db.execSQL(query);

        query = "CREATE TABLE " + MOVES_TABLE_NAME + " ("
                + MOVE_NAME_COL + " STRING NOT NULL PRIMARY KEY, "
                + MOVE_TYPE_COL + " CHAR (3) NOT NULL REFERENCES Enum_Type (typeId), "
                + PP_COL + " INT NOT NULL, "
                + POWER_COL + " INT NOT NULL, "
                + ACC_COL + " DECIMAL (4, 1) NOT NULL, "
                + EFFECT_COL + " CHAR NOT NULL, "
                + DETAIL_EFFECT_COL + " INT, "
                + EFFECT_RATE_COL + " INT, "
                + TM_COL + " INT NOT NULL, "
                + PRIORITY_COL + " INT NOT NULL, "
                + TARGET_COL + " INT NOT NULL REFERENCES Enum_Target (targetId))";

        db.execSQL(query);

        query = "CREATE TABLE " + MOVESET_TABLE_NAME + " ("
                + MOVESET_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVESET_DEX_COL + " INT REFERENCES Pokemon (dexNum), "
                + MOVESET_POS_COL + " INT NOT NULL, "
                + MOVESET_LEVEL_COL + " INT NOT NULL, "
                + MOVESET_MOVE_COL + " VARCHAR REFERENCES Moves (moveName) NOT NULL, "
                + MOVESET_YELLOW_COL + " BOOLEAN NOT NULL, "
                + MOVESET_PREVO_COL + " INT REFERENCES Pokemon (dexNum), "
                + MOVESET_SOURCE_COL + " VARCHAR)";

                db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TARGET_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + XP_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVESET_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addTargetEnum(int id, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(TARGET_ID_COL, id);
        values.put(TARGET_VALUE_COL, value);

        db.insert(TARGET_TABLE_NAME, null, values);
        db.close();
    }

    public void addTypeEnum(String id, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(TYPE_ID_COL, id);
        values.put(TYPE_NAME_COL, value);

        db.insert(TYPE_TABLE_NAME, null, values);
        db.close();
    }

    public void addXPEnum(String id, int max, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(GROWTH_ID_COL, id);
        values.put(GROWTH_MAX_COL, max);
        values.put(GROWTH_NAME_COL, name);

        db.insert(XP_TABLE_NAME, null, values);
        db.close();
    }

    public void addPokemon(int dexNum, String name, String type1, String type2, String cls,
                           double height, double weight, int capRate, String xpGrowth,
                           String redLoc, String blueLoc, String blueJpLoc, String yellowLoc,
                           int hp, int atk, int def, int spc, int spe, int evo, int evoLvl,
                           int prEvo) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, dexNum);
        values.put(NAME_COL, name);
        values.put(TYPE1_COL, type1);
        values.put(TYPE2_COL, type2);
        values.put(CLASS_COL, cls);
        values.put(HEIGHT_COL, height);
        values.put(WEIGHT_COL, weight);
        values.put(CAP_RATE_COL, capRate);
        values.put(XP_GROWTH_COL, xpGrowth);
        values.put(RED_LOC_COL, redLoc);
        values.put(BLUE_LOC_COL, blueLoc);
        values.put(BLUE_JP_LOC_COL, blueJpLoc);
        values.put(YELLOW_LOC_COL, yellowLoc);
        values.put(HP_COL, hp);
        values.put(ATK_COL, atk);
        values.put(DEF_COL, def);
        values.put(SPC_COL, spc);
        values.put(SPE_COL, spe);
        values.put(EVO_COL, evo);
        values.put(EVO_LVL_COL, evoLvl);
        values.put(PREVO_COL, prEvo);

        db.insert(POKEMON_TABLE_NAME, null, values);

        db.close();
    }

    public void addMove(String name, String type, int pp, int power, double accuracy, String effect,
                        int detailed, int effectRate, int tmNum, int priority, int target) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(MOVE_NAME_COL, name);
        values.put(MOVE_TYPE_COL, type);
        values.put(PP_COL, pp);
        values.put(POWER_COL, power);
        values.put(ACC_COL, accuracy);
        values.put(EFFECT_COL, effect);
        values.put(DETAIL_EFFECT_COL, detailed);
        values.put(EFFECT_RATE_COL, effectRate);
        values.put(TM_COL, tmNum);
        values.put(PRIORITY_COL, priority);
        values.put(TARGET_COL, target);

        db.insert(MOVES_TABLE_NAME, null, values);
        db.close();
    }

    public void addMovesetEntry(int dexNum, int pos, int level, String move, boolean yellow,
                                int prEvo, String source) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(MOVESET_DEX_COL, dexNum);
        values.put(MOVESET_POS_COL, pos);
        values.put(MOVESET_LEVEL_COL, level);
        values.put(MOVESET_MOVE_COL, move);
        values.put(MOVESET_YELLOW_COL, yellow);
        values.put(MOVESET_PREVO_COL, prEvo);
        values.put(MOVESET_SOURCE_COL, source);

        db.insert(MOVESET_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * FIXME
     * @param dexNum
     * @return
     */
    public PokemonG1Model getPokemon(int dexNum) {

        SQLiteDatabase pokemonDb = this.getReadableDatabase();

        Cursor curPok = pokemonDb.rawQuery("SELECT * FROM " + POKEMON_TABLE_NAME
                + " WHERE " + ID_COL + " = ?", new String[]{ Integer.toString(dexNum) });

        PokemonG1Model returnPokemon = new PokemonG1Model(dexNum,
                curPok.getString(1),
                Type.valueOf(curPok.getString(2)),
                Type.valueOf(curPok.getString(3)),
                curPok.getString(4),
                curPok.getDouble(5),
                curPok.getDouble(6),
                curPok.getInt(7),
                XpGrowth.valueOf(curPok.getString(8)),
                curPok.getString(9),
                curPok.getString(10),
                curPok.getString(11),
                curPok.getString(12),
                curPok.getInt(13),
                curPok.getInt(14),
                curPok.getInt(15),
                curPok.getInt(16),
                curPok.getInt(17),
                curPok.getInt(18),
                curPok.getInt(19),
                curPok.getInt(20));

        pokemonDb.close();
        return returnPokemon;
    }

    public MoveModel getMove(String name) {

        SQLiteDatabase moveDB = this.getReadableDatabase();

        Cursor moveCursor = moveDB.rawQuery("SELECT * FROM " + MOVES_TABLE_NAME, null);

        // moveCursor = moveDB.rawQuery("SELECT * FROM " + MOVES_TABLE_NAME + " WHERE "
        // + MOVE_NAME_COL + " = ?", new String[]{name});

        moveCursor.moveToFirst();

        MoveModel returnMove = new MoveModel(moveCursor.getString(1),
                Type.valueOf(moveCursor.getString(2)),
                moveCursor.getInt(3),
                moveCursor.getInt(4),
                moveCursor.getDouble(5),
                moveCursor.getString(6),
                moveCursor.getInt(7),
                moveCursor.getInt(8),
                moveCursor.getInt(9),
                moveCursor.getInt(10),
                moveCursor.getInt(11));

        moveCursor.close();
        return returnMove;
    }

    public boolean getMoves() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor moveCursor = db.rawQuery("SELECT * FROM " + MOVES_TABLE_NAME, null);

        ArrayList<MoveModel> moves = new ArrayList<>();

        if (moveCursor.moveToFirst()) {
            do {
                moves.add(new MoveModel(moveCursor.getString(1),
                        Type.valueOf(moveCursor.getString(2)),
                        moveCursor.getInt(3),
                        moveCursor.getInt(4),
                        moveCursor.getDouble(5),
                        moveCursor.getString(6),
                        moveCursor.getInt(7),
                        moveCursor.getInt(8),
                        moveCursor.getInt(9),
                        moveCursor.getInt(10),
                        moveCursor.getInt(11)));
            } while (moveCursor.moveToNext());
        }

        moveCursor.close();

        for(int i = 0; i < moves.size(); i++) {
            Log.d("**TESTING**", moves.get(0).getName());
        }
        return true;
    }
}
