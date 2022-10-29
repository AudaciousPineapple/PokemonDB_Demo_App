package com.example.pokemondbappv2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DB_NAME = "PokemonDB.db";
    private static final String DB_PATH = "/app/src/main/assets/PokemonDB.db";
    private static final int DB_VERSION = 1;

    // Used for the Target table
    private static final String TARGET_TABLE_NAME = "Enum_Target";
    private static final String TARGET_ID_COL = "targetId";
    private static final String TARGET_VALUE_COL = "targetValue";

    // Used for the Enum_Type table
    private static final String TYPE_TABLE_NAME = "Enum_Type";
    private static final String TYPE_ID_COL = "typeId";
    private static final String TYPE_NAME_COL = "typeName";

    // Used for the Enum_XP_Growth table
    private static final String XP_TABLE_NAME = "Enum_XP_Growth";
    private static final String XP_ID_COL = "growthId";
    private static final String XP_MAX_COL = "growthMax";
    private static final String XP_NAME_COL = "growthName";

    // Used for the Pokemon Table
    private static final String POKEMON_TABLE_NAME = "Pokemon";
    private static final String POKEMON_ID_COL = "dexNum";
    private static final String POKEMON_NAME_COL = "name";
    private static final String POKEMON_TYPE1_COL = "type1";
    private static final String POKEMON_TYPE2_COL = "type2";
    private static final String POKEMON_CLASS_COL = "class";
    private static final String POKEMON_HEIGHT_COL = "height";
    private static final String POKEMON_WEIGHT_COL = "weight";
    private static final String POKEMON_CAP_RATE_COL = "captureRate";
    private static final String POKEMON_XP_GROWTH_COL = "xpGrowth";
    private static final String POKEMON_RED_LOC_COL = "redLocation";
    private static final String POKEMON_BLUE_LOC_COL = "blueLocation";
    private static final String POKEMON_BLUE_JP_LOC_COL = "blueJpLocation";
    private static final String POKEMON_YELLOW_LOC_COL = "yellowLocation";
    private static final String POKEMON_HP_COL = "statHP";
    private static final String POKEMON_ATK_COL = "statATK";
    private static final String POKEMON_DEF_COL = "statDEF";
    private static final String POKEMON_SPC_COL = "statSPC";
    private static final String POKEMON_SPE_COL = "statSPE";
    private static final String POKEMON_EVO_COL = "evo";
    private static final String POKEMON_EVO_LVL_COL = "evoLvl";
    private static final String POKEMON_PREVO_COL = "prEvo";

    // Used for the Moves Table
    private static final String MOVE_TABLE_NAME = "Moves";
    private static final String MOVE_NAME_COL = "moveName";
    private static final String MOVE_TYPE_COL = "moveType";
    private static final String MOVE_PP_COL = "powerPoints";
    private static final String MOVE_POWER_COL = "basePower";
    private static final String MOVE_ACC_COL = "accuracy";
    private static final String MOVE_EFFECT_COL = "battleEffect";
    private static final String MOVE_DETAIL_EFFECT_COL = "detailEffect";
    private static final String MOVE_EFFECT_RATE_COL = "effectRate";
    private static final String MOVE_TM_COL = "tmNum";
    private static final String MOVE_PRIORITY_COL = "priority";
    private static final String MOVE_TARGET_COL = "target";

    // Used for the Movesets table
    private static final String MOVESET_TABLE_NAME = "Movesets";
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

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TARGET_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + XP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MOVE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MOVESET_TABLE_NAME);

        onCreate(db);
    }

    /**
     * FIXME
     * @param dexNum
     * @return
     */
    public ModelPokemonG1 getPokemon(int dexNum) {

        SQLiteDatabase pokemonDb = this.getReadableDatabase();

        Cursor curPok = pokemonDb.query(
                POKEMON_TABLE_NAME,
                null,
                POKEMON_ID_COL + " = ?",
                new String[]{ Integer.toString(dexNum) },
                null,
                null,
                null
        );

        ModelPokemonG1 returnPokemon = null;

        while (curPok.moveToNext()) {
            returnPokemon = new ModelPokemonG1(dexNum,
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
        }

        // Log.d("**TESTING**", returnPokemon.toString());

        curPok.close();
        return returnPokemon;
    }

    // FIXME
    public ModelMove getMove(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor moveCursor = db.query(
                MOVE_TABLE_NAME,
                null,
                MOVE_NAME_COL + " = ?",
                new String[]{name},
                null,
                null,
                null
        );

        ModelMove returnMove = null;

        while (moveCursor.moveToNext()) {
            returnMove = new ModelMove(moveCursor.getString(0),
                    Type.valueOf(moveCursor.getString(1)),
                    moveCursor.getInt(2),
                    moveCursor.getInt(3),
                    moveCursor.getDouble(4),
                    moveCursor.getString(5),
                    moveCursor.getInt(6),
                    moveCursor.getInt(7),
                    moveCursor.getInt(8),
                    moveCursor.getInt(9),
                    moveCursor.getInt(10));
        }
        moveCursor.close();
        return returnMove;
    }

    /**
     * FIXME
     * @param dexNum
     * @return
     */
    public ArrayList<ModelMoveset> getMoveset(int dexNum) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                MOVESET_TABLE_NAME,
                null,
                MOVESET_DEX_COL + " = ?",
                new String[]{ Integer.toString(dexNum) },
                null,
                null,
                MOVESET_POS_COL
        );

        ArrayList<ModelMoveset> returnMoveset = new ArrayList<>();
        ModelMoveset tempMovesetItem = null;

        while (cursor.moveToNext()) {
            tempMovesetItem = new ModelMoveset(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5).contentEquals("true"),
                    cursor.getInt(6),
                    cursor.getString(7)
            );

            returnMoveset.add(tempMovesetItem);
        }
        cursor.close();
        return returnMoveset;

    }

    public ArrayList<CharSequence> getAllMoveNames() {
        ArrayList<CharSequence> returnArray = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                MOVE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            returnArray.add(cursor.getString(0));
        }

        return returnArray;
    }

    public ArrayList<ModelMoveset> getAllLearners(String moveName) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<ModelMoveset> list = new ArrayList<>();

        Cursor cursor = db.query(
          MOVESET_TABLE_NAME,
          null,
          MOVESET_MOVE_COL + " = ?",
          new String[]{moveName},
          null,
          null,
          MOVESET_ID_COL
        );

        while (cursor.moveToNext()) {
            list.add(new ModelMoveset(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5).contentEquals("true"),
                    cursor.getInt(6),
                    cursor.getString(7)));
        }

        cursor.close();
        return list;
    }
}
