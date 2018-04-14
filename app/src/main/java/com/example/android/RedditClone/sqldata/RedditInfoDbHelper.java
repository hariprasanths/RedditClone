package com.example.android.RedditClone.sqldata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditInfoDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "infos.db";
    private static final int DATABASE_VERSION = 1;
    public RedditInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + RedditContract.RedditInfoEntry.TABLE_NAME + " (" +
                RedditContract.RedditInfoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RedditContract.RedditInfoEntry.COLUMN_THUMBNAIL + " TEXT, " +
                RedditContract.RedditInfoEntry.COLUMN_TITLE + " TEXT, " +
                RedditContract.RedditInfoEntry.COLUMN_TIMESTAMP + " TEXT NOT NULL, " +
                RedditContract.RedditInfoEntry.COLUMN_COMMENTS + " INTEGER);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
