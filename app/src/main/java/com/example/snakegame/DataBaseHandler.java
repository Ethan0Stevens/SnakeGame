package com.example.snakegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {
    // Déclaration des variables
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "leaderboard.db";
    private final String tableName;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TOP = "top";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";
    SQLiteDatabase database;

    /**
     * Constructeur de la class DataBaseHandler
     */
    public DataBaseHandler(@Nullable Context context, String tableName) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
        this.tableName = tableName;
        createTable(database);
        // resetTable();
    }

    /**
     * Creation de la table
     */
    public void createTable(SQLiteDatabase db) {
        db.execSQL("Create Table IF NOT EXISTS " + tableName + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TOP + " NUMBER, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SCORE + " TEXT)");

    }

    /**
     * Réinitialise la table
     */
    public void resetTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Drop Table IF EXISTS " + tableName);
        createTable(db);
    }

    /**
     * Insertion du score dans la table
     *
     * @param name  nom du joueur
     * @param score score du joueur
     */
    public void insertScore(String name, String score) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("top", getTop(db, score));
        contentValues.put("name", name);
        contentValues.put("score", score);

        db.insert(tableName, null, contentValues);
    }

    /**
     * Récupere le classement du joueur en fonction de son score, et mets a jour tous les autres top
     * @param db la base de données
     * @param score le score du joueur
     * @return le top du joueur
     */
    private int getTop(SQLiteDatabase db, String score) {
        int top = 1;
        int count = 1;
        boolean inserted = false;

        // Parcour chaque données de la table
        for (ArrayList<String> data: getDatas()) {
            count++;
            // Si il y a eu un ajout en haut du top, alors mettre a jour tous les autres top
            if (inserted) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("top", Integer.parseInt(data.get(1)) + 1);
                db.update(tableName, contentValues, "ID= ?", new String[] {data.get(0)});
            } else if (Integer.parseInt(score) > Integer.parseInt(data.get(3))) {
                inserted = true;
                ContentValues contentValues = new ContentValues();

                top = Integer.parseInt(data.get(1));

                contentValues.put("top", top + 1);
                db.update(tableName, contentValues, "ID= ?", new String[] {data.get(0)});
            }
        }

        // Si le score n'est au dessus d'aucun autre score,
        // alors le placer en derniere position
        if (!inserted)
            top = count;

        return top;
    }

    /**
     * Retournes les données de la tables trié par top
     */
    public ArrayList<ArrayList<String>> getDatas() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY top", null);

        // Parcourir le curseur
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> score = new ArrayList<>();
                score.add(cursor.getString(0));
                score.add(String.valueOf(cursor.getInt(1)));
                score.add(cursor.getString(2));
                score.add(cursor.getString(3));
                arrayList.add(score);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return arrayList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
