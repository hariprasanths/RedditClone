package com.example.android.RedditClone.sqldata;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.RedditClone";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH = "redditinfo";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

    private RedditContract() {
    }

    public static final class RedditInfoEntry implements BaseColumns {
        public final static String TABLE_NAME = "redditinfos";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_THUMBNAIL = "thumbnail";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_TIMESTAMP = "timestamp";
        public final static String COLUMN_COMMENTS = "comments";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
    }
}
