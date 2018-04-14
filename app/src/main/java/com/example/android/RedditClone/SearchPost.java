package com.example.android.RedditClone;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.RedditClone.sqldata.RedditContract;
import com.example.android.RedditClone.sqldata.RedditInfoCursorAdapter;

public class SearchPost extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    EditText inputbox;
    Button searchButton;
    ImageView thumbnail;
    TextView title;
    TextView comments;
    TextView timestamp;
    RedditInfoCursorAdapter adapter;
    ListView searchResultListView;
    String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        inputbox = (EditText) findViewById(R.id.search_input_box);
        searchButton = (Button) findViewById(R.id.search_post_by_title_button);
        thumbnail = (ImageView) findViewById(R.id.thumbnail_view);
        title = (TextView) findViewById(R.id.title_view);
        comments = (TextView) findViewById(R.id.no_of_comments_view);
        timestamp = (TextView) findViewById(R.id.timestamp_view);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.result_layout);
        searchResultListView = (ListView) findViewById(R.id.search_result_list_view);
        layout.setVisibility(View.INVISIBLE);

        adapter = new RedditInfoCursorAdapter(this,null);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = inputbox.getText().toString().trim();
                searchResultListView.setAdapter(adapter);
                layout.setVisibility(View.VISIBLE);
                getSupportLoaderManager().initLoader(0,null,SearchPost.this);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                RedditContract.RedditInfoEntry._ID,
                RedditContract.RedditInfoEntry.COLUMN_THUMBNAIL,
                RedditContract.RedditInfoEntry.COLUMN_TITLE,
                RedditContract.RedditInfoEntry.COLUMN_TIMESTAMP,
                RedditContract.RedditInfoEntry.COLUMN_COMMENTS};

        String selection = RedditContract.RedditInfoEntry.COLUMN_TITLE + "=?";
        String[] selectionArgs = new String[]{inputText};

        return new CursorLoader(this,
                RedditContract.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
