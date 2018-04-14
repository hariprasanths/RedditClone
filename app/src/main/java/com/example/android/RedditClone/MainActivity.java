package com.example.android.RedditClone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RedditClone.data.RedditData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    RedditRestAdapter restAdapter;
    RecyclerView recyclerView;
    ArrayList<RedditInfo> redditInfos;
    RedditInfoAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    TextView emptyView;
    String postIdAfter = "";
    LinearLayout loadingLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);
        loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);

        loadingLayout.setVisibility(View.GONE);

        if(redditInfos == null)
            redditInfos = new ArrayList<>();

        adapter = new RedditInfoAdapter(this,redditInfos);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                loadingLayout.setVisibility(View.VISIBLE);
            loadMoreData(adapter.getItemCount()-1);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.refresh_button:
                loadingLayout.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                if(restAdapter == null)
                    restAdapter = new RedditRestAdapter();
                redditInfos.clear();
                Call<RedditData> call = restAdapter.getRedditPosts(postIdAfter);
                call.enqueue(new Callback<RedditData>() {
                    @Override
                    public void onResponse(Call<RedditData> call, Response<RedditData> response) {

                        for (int i = 0; i < response.body().getData().getChildren().size();i++) {
                            String title = response.body().getData().getChildren().get(i).getData().getTitle();
                            String thumbnail = response.body().getData().getChildren().get(i).getData().getThumbnail();
                            int noOfComments = response.body().getData().getChildren().get(i).getData().getNumComments();
                            double doubleDate = response.body().getData().getChildren().get(i).getData().getCreated();
                            long longDate = (long) doubleDate * 1000;
                            Date date = new Date(longDate);
                            String timestamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SS").format(date);
                            redditInfos.add(i,new RedditInfo(thumbnail, title, timestamp, noOfComments));
                            adapter.notifyDataSetChanged();
                        }
                        postIdAfter = response.body().getData().getChildren().get(4).getData().getName();
                        loadingLayout.setVisibility(View.GONE);


                    }

                    @Override
                    public void onFailure(Call<RedditData> call, Throwable t) {
                        loadingLayout.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Error loading info",Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case R.id.history_menu_button:
                Intent intent = new Intent(MainActivity.this,History.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void loadMoreData(final int index)
    {
        emptyView.setVisibility(View.INVISIBLE);
        if(restAdapter == null)
            restAdapter = new RedditRestAdapter();
        Call<RedditData> call = restAdapter.getRedditPosts(postIdAfter);
        call.enqueue(new Callback<RedditData>() {
            @Override
            public void onResponse(Call<RedditData> call, Response<RedditData> response) {

                for (int i = 0; i < 5;i++) {
                    String title = response.body().getData().getChildren().get(i).getData().getTitle();
                    String thumbnail = response.body().getData().getChildren().get(i).getData().getThumbnail();
                    int noOfComments = response.body().getData().getChildren().get(i).getData().getNumComments();
                    double doubleDate = response.body().getData().getChildren().get(i).getData().getCreated();
                    long longDate = (long) doubleDate * 1000;
                    Date date = new Date(longDate);
                    String timestamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SS").format(date);
                    redditInfos.add(new RedditInfo(thumbnail, title, timestamp, noOfComments));
                    adapter.notifyDataSetChanged();
                }
                postIdAfter = response.body().getData().getChildren().get(4).getData().getName();
                loadingLayout.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<RedditData> call, Throwable t) {

                loadingLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Error loading info",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
