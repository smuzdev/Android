package com.smuzdev.lab_08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GoodsFinder.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Things";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "thing_title";
    public static final String COLUMN_DESCRIPTION = "thing_description";
    public static final String COLUMN_DISCOVERED_PLACE = "thing_discovered_place";
    public static final String COLUMN_IMAGE = "thing_image";
    public Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DISCOVERED_PLACE + " TEXT, " +
                COLUMN_IMAGE + " BLOB);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addThing(String title, String description, String discoveredPlace, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DISCOVERED_PLACE, discoveredPlace);
        cv.put(COLUMN_IMAGE, image);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed :(", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully added!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String description, String discoveredPlace, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DISCOVERED_PLACE, discoveredPlace);
        cv.put(COLUMN_IMAGE, image);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update :(", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRaw(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete:(", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }

    ArrayList<byte[]> selectImageById(String id) {
        String query = "SELECT " + COLUMN_IMAGE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<byte[]> thing_image = new ArrayList<>();

        Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                thing_image.add(cursor.getBlob(0));
            }
            cursor.close();
        }
        return thing_image;
    }
}
