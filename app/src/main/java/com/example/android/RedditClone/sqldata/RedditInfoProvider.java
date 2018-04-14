package com.example.android.RedditClone.sqldata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditInfoProvider extends ContentProvider {

    private static final int REDDIT_INFO = 100;

     private static final int REDDIT_INFO_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(RedditContract.CONTENT_AUTHORITY, RedditContract.PATH,REDDIT_INFO);
        sUriMatcher.addURI(RedditContract.CONTENT_AUTHORITY,RedditContract.PATH + "/#",REDDIT_INFO_ID);
    }

    RedditInfoDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper = new RedditInfoDbHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case REDDIT_INFO:
                cursor = database.query(RedditContract.RedditInfoEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case REDDIT_INFO_ID:
                selection = RedditContract.RedditInfoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(RedditContract.RedditInfoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        switch (match) {
            case REDDIT_INFO:
                long rowid = database.insert(RedditContract.RedditInfoEntry.TABLE_NAME, null, contentValues);
                return ContentUris.withAppendedId(uri, rowid);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REDDIT_INFO:

                rowsDeleted = database.delete(RedditContract.RedditInfoEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rowsDeleted;
            case REDDIT_INFO_ID:
                selection = RedditContract.RedditInfoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(RedditContract.RedditInfoEntry.TABLE_NAME, selection, selectionArgs);

                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REDDIT_INFO:
                return RedditContract.RedditInfoEntry.CONTENT_LIST_TYPE;
            case REDDIT_INFO_ID:
                return RedditContract.RedditInfoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}
