package com.example.android.RedditClone;

import com.example.android.RedditClone.data.RedditData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditRestAdapter {

    private static final String REDDIT_URL = "https://www.reddit.com";

    private interface RedditApiInterface{
        @GET("/hot/.json")
        Call<RedditData> getRedditPostsFromApi(@Query("limit") int limit,@Query("after") String after);
    }
    RedditApiInterface redditApi;


    public RedditRestAdapter ()
    {
        Retrofit retrofit =  new Retrofit.Builder()
                                .baseUrl(REDDIT_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        redditApi = retrofit.create(RedditApiInterface.class);

    }

    public Call<RedditData> getRedditPosts(String postIdAfter)
    {
        return redditApi.getRedditPostsFromApi(5,postIdAfter);
    }
}
