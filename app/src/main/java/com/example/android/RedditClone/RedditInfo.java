package com.example.android.RedditClone;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditInfo {

    private String mThumbnail;
    private String mTitle;
    private String mTimestamp;
    private int mNoOfComments;

   public RedditInfo(String thumbnail,String title,String timestamp,int noOfComments)
    {
        mThumbnail = thumbnail;
        mTitle = title;
        mTimestamp = timestamp;
        mNoOfComments = noOfComments;
    }

    public String getThumbnail()
    {
        return mThumbnail;
    }
    public String getTitle()
    {
        return mTitle;
    }
    public String getTimestamp()
    {
        return mTimestamp;
    }
    public int getNoOfComments()
    {
        return mNoOfComments;
    }
}
