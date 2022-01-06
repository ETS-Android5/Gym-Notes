package com.dm4nk.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GymResults.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_gym_results";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SETS = "sets";
    private static final String COLUMN_REPS = "reps";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_DATE = "date";
    private final Context context;

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SETS + " INTEGER, " +
                COLUMN_REPS + " INTEGER, " +
                COLUMN_WEIGHT + " TEXT, " +
                COLUMN_DATE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addExercise(String name, int times, int reps, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name.toLowerCase(Locale.ROOT));
        cv.put(COLUMN_SETS, times);
        cv.put(COLUMN_REPS, reps);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis());

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY "
                + COLUMN_NAME + " ASC, "
                + COLUMN_SETS + " ASC, "
                + COLUMN_REPS + " ASC, "
                + COLUMN_WEIGHT + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, int times, int reps, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name.toLowerCase(Locale.ROOT));
        cv.put(COLUMN_SETS, times);
        cv.put(COLUMN_REPS, reps);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis());

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor findByTemplateName(String name){
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NAME + " LIKE " + "%" + name + "%"
                + " ORDER BY "
                + COLUMN_NAME + " ASC, "
                + COLUMN_SETS + " ASC, "
                + COLUMN_REPS + " ASC, "
                + COLUMN_WEIGHT + " ASC;";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}