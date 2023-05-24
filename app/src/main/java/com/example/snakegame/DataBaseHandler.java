package com.example.snakegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "leaderboard.db";
    private String tableName = "easy";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TOP = "top";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";


    SQLiteDatabase database;

    public DataBaseHandler(@Nullable Context context, String tableName) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL("Create Table " + tableName + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TOP + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SCORE + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    public void resetTable() {
        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 0, 0);
        createTable(db);
    }

    public Boolean insertScore(String name, String score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("top", "1");
        contentValues.put("name", name);
        contentValues.put("score", score);

        long result = db.insert(tableName, null, contentValues);

        return result != -1;
    }

    public ArrayList<ArrayList<String>> getDatas() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> score = new ArrayList<>();
                score.add(cursor.getString(1));
                score.add(cursor.getString(2));
                score.add(cursor.getString(3));
                arrayList.add(score);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
}
