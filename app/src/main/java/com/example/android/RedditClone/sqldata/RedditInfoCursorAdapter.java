package com.example.android.RedditClone.sqldata;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.RedditClone.R;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditInfoCursorAdapter extends CursorAdapter {
    public RedditInfoCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title, timestamp, noOfComments;
        ImageView thumbnail;
        ImageView saveButton;
        title = (TextView) view.findViewById(R.id.title_view);
        timestamp = (TextView) view.findViewById(R.id.timestamp_view);
        noOfComments = (TextView) view.findViewById(R.id.no_of_comments_view);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail_view);
        saveButton = (ImageView) view.findViewById(R.id.save_button);
        saveButton.setVisibility(View.INVISIBLE);
        String thumbnailText = cursor.getString(cursor.getColumnIndex(RedditContract.RedditInfoEntry.COLUMN_THUMBNAIL));
        String titleView = cursor.getString(cursor.getColumnIndex(RedditContract.RedditInfoEntry.COLUMN_TITLE));
        String timestampView = cursor.getString(cursor.getColumnIndex(RedditContract.RedditInfoEntry.COLUMN_TIMESTAMP));
        int comments = cursor.getInt(cursor.getColumnIndex(RedditContract.RedditInfoEntry.COLUMN_COMMENTS));

        timestamp.setText(timestampView);
        noOfComments.setText(String.valueOf(comments));
        title.setText(titleView);

        if (thumbnailText.contentEquals("image") || thumbnailText.contentEquals("default")
                || thumbnailText.contentEquals("self"))
            thumbnail.setImageResource(R.drawable.reddit_placeholder_img);
        else {
            Glide.with(context)
                    .load(thumbnailText)
                    .into(thumbnail);
        }

    }
}
