package com.ibl.apps.myphotokeyboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

    public static final String DATABASE_NAME = "FancySticker.db";

    private static final int DATABASE_VERSION = 1;
    // Comma key
    public static final String IS_DELETED = "isDeleted";
    //FOR color wallpaper
    public static final String TABLE_COLOR_WALLPAPER = "table_color_wallpaper";
    public static final String KEY_COLOR_WALLPAPER_ID = "color_id";
    public static final String KEY_COLOR_WALLPAPER_TITLE = "color_title";
    public static final String KEY_COLOR_WALLPAPER_IMAGE = "color_image";
    public static final String KEY_COLOR_WALLPAPER_THUMB_IMAGE = "color_thumb_image";
    public static final String KEY_COLOR_WALLPAPER_IMAGE_BITMAP = "image_bitmap";
    public static final String KEY_COLOR_WALLPAPER_THUMB_BITMAP = "thumbnail_bitmap";
    public static final String KEY_COLOR_WALLPAPER_IS_PAID = "color_is_paid";

    private static final String CREATE_COLOR_WALLPAPER_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_COLOR_WALLPAPER + "( "
            + KEY_COLOR_WALLPAPER_ID + " TEXT, "
            + KEY_COLOR_WALLPAPER_TITLE + " TEXT, "
            + KEY_COLOR_WALLPAPER_IMAGE + " TEXT, "
            + KEY_COLOR_WALLPAPER_THUMB_IMAGE + " TEXT, "
            + KEY_COLOR_WALLPAPER_IMAGE_BITMAP + " TEXT, "
            + KEY_COLOR_WALLPAPER_THUMB_BITMAP + " TEXT, "
            + KEY_COLOR_WALLPAPER_IS_PAID + " TEXT );";

    //FOR textual wallpaper
    public static final String TABLE_TEXTUAL_WALLPAPER = "table_textual_wallpaper";
    public static final String KEY_TEXTUAL_WALLPAPER_ID = "textual_id";
    public static final String KEY_TEXTUAL_WALLPAPER_TITLE = "textual_title";
    public static final String KEY_TEXTUAL_WALLPAPER_IMAGE = "textual_image";
    public static final String KEY_TEXTUAL_WALLPAPER_THUMB_IMAGE = "textual_thumb_image";
    public static final String KEY_TEXTUAL_WALLPAPER_IMAGE_BITMAP = "textual_image_bitmap";
    public static final String KEY_TEXTUAL_WALLPAPER_THUMB_BITMAP = "textual_thumbnail_bitmap";
    public static final String KEY_TEXTUAL_WALLPAPER_IS_PAID = "textual_is_paid";

    private static final String CREATE_TEXTUAL_WALLPAPER_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TEXTUAL_WALLPAPER + "( "
            + KEY_TEXTUAL_WALLPAPER_ID + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_TITLE + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_IMAGE + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_THUMB_IMAGE + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_IMAGE_BITMAP + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_THUMB_BITMAP + " TEXT, "
            + KEY_TEXTUAL_WALLPAPER_IS_PAID + " TEXT );";
    // for color
    public static final String TABLE_COLOR = "table_color";
    public static final String KEY_COLOR_ID = "color_id";
    public static final String KEY_COLOR_CODE = "color_code";
    public static final String KEY_COLOR_IS_PAID = "color_is_paid";

    private static final String CREATE_COLOR_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_COLOR + "( "
            + KEY_COLOR_ID + " TEXT, "
            + KEY_COLOR_CODE + " TEXT, "
            + KEY_COLOR_IS_PAID + " TEXT );";

    // for font
    public static final String TABLE_FONT = "table_font";
    public static final String KEY_FONT_ID = "font_id";
    public static final String KEY_FONT_TITLE = "font_title";
    public static final String KEY_FONT_URL = "font_url";
    public static final String KEY_FONT_IS_PAID = "font_is_paid";

    private static final String CREATE_FONT_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FONT + "( "
            + KEY_FONT_ID + " TEXT, "
            + KEY_FONT_TITLE + " TEXT, "
            + KEY_FONT_URL + " TEXT, "
            + KEY_FONT_IS_PAID + " TEXT );";

    // for sound
    public static final String TABLE_SOUND = "table_sound";
    public static final String KEY_SOUND_ID = "sound_id";
    public static final String KEY_SOUND_TITLE = "sound_title";
    public static final String KEY_SOUND_URL = "sound_url";
    public static final String KEY_SOUND_IS_PAID = "sound_is_paid";

    private static final String CREATE_SOUND_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SOUND + "( "
            + KEY_SOUND_ID + " TEXT, "
            + KEY_SOUND_TITLE + " TEXT, "
            + KEY_SOUND_URL + " TEXT, "
            + KEY_SOUND_IS_PAID + " TEXT );";

    private final Context context;
    private SQLiteDatabase sqLiteDb;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    // ---opens the database---
    public DatabaseHelper open() throws SQLException {
        DatabaseHelper1 dbHelper = new DatabaseHelper1(context);

        if (sqLiteDb == null || !sqLiteDb.isOpen()) {
            sqLiteDb = dbHelper.getWritableDatabase();
            sqLiteDb = dbHelper.getReadableDatabase();
        }
//        if (!sqLiteDb.isReadOnly()) {
//            // Enable foreign key constraints
//            sqLiteDb.execSQL("PRAGMA foreign_keys=ON;");
//        }
//
        return this;
    }

    // ---closes the database---
    public void close() {
        if (sqLiteDb != null && sqLiteDb.isOpen()) {
//            dbHelper.close();
            sqLiteDb.close();
        }
    }

    public boolean insertData(String tableName, ContentValues values) {
        open();
        sqLiteDb.insert(tableName, null, values);
        close();
        return true;
    }

    public boolean updateRowData(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        open();
        sqLiteDb.update(tableName, values, selection, selectionArgs);
        close();
        return true;
    }

    public boolean deleteRowData(String tableName, String selection, String[] selectionArgs) {
        open();
        sqLiteDb.delete(tableName, selection, selectionArgs);
        close();
        return true;
    }

    public Cursor getTableDataById(String tableName, String field, String value) {
        open();
        String selectRowQuery = "SELECT * FROM " + tableName + " WHERE " + field + " = '" + value + "'";
//        Log.e("---selectRowQuery---", "" + selectRowQuery);

        return sqLiteDb.rawQuery(selectRowQuery, null);
    }

    public Cursor getRowData(String tableName, String whereColumnId, String columnValue, boolean isGetAllData) {
        open();

        String selectRowQuery = null;
        if (isGetAllData) {
            selectRowQuery = "SELECT * FROM " + tableName + "WHERE"
                    + whereColumnId + " = '" + columnValue + "'";
        } else if (!isGetAllData) {
            selectRowQuery = "SELECT * FROM " + tableName + " WHERE "
                    + whereColumnId + " = '" + columnValue + "' AND " + IS_DELETED + " = " + 0;
        }
        Cursor cursor = sqLiteDb.rawQuery(selectRowQuery, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {

                } while (cursor.moveToNext());
            }
        }
        close();
        return cursor;
    }


    public class DatabaseHelper1 extends SQLiteOpenHelper {

        public DatabaseHelper1(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_COLOR_WALLPAPER_MASTER);
            db.execSQL(CREATE_TEXTUAL_WALLPAPER_MASTER);
            db.execSQL(CREATE_COLOR_MASTER);
            db.execSQL(CREATE_SOUND_MASTER);
            db.execSQL(CREATE_FONT_MASTER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDb, int oldVersion,
                              int newVersion) {
            sqLiteDb.execSQL("DROP table IF EXISTS " + DATABASE_NAME);
            onCreate(sqLiteDb);
        }

    }

    public Cursor getDataOfTable(String tableName) {
        open();
        String selectRowQuery = "SELECT * FROM " + tableName;
        return sqLiteDb.rawQuery(selectRowQuery, null);
    }

    public Cursor getDataOfSubCat(String tableName) {
        open();
        String selectRowQuery = "SELECT * FROM " + tableName;
        return sqLiteDb.rawQuery(selectRowQuery, null);
    }
}