package com.example.android.RedditClone;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RedditClone.sqldata.RedditContract;
import com.example.android.RedditClone.sqldata.RedditInfoCursorAdapter;

public class History extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView historyList;
    RedditInfoCursorAdapter adapter;
    TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyList = (ListView) findViewById(R.id.history_list);
        adapter = new RedditInfoCursorAdapter(this,null);
        emptyTextView = (TextView) findViewById(R.id.empty_view_history);
        historyList.setEmptyView(emptyTextView);

        historyList.setAdapter(adapter);
        getSupportLoaderManager().initLoader(0,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all:
                getContentResolver().delete(RedditContract.CONTENT_URI,null,null);
                Toast.makeText(getApplicationContext(),"All SAVED POSTS DELETED",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                break;
            case R.id.search_post_button:
                Intent intent = new Intent(History.this,SearchPost.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                RedditContract.RedditInfoEntry._ID,
                RedditContract.RedditInfoEntry.COLUMN_THUMBNAIL,
                RedditContract.RedditInfoEntry.COLUMN_TITLE,
                RedditContract.RedditInfoEntry.COLUMN_TIMESTAMP,
                RedditContract.RedditInfoEntry.COLUMN_COMMENTS};


        return new CursorLoader(this,
                RedditContract.CONTENT_URI,
                projection,
                null,
                null,
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
