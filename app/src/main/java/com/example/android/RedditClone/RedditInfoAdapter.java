package com.example.android.RedditClone;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.RedditClone.sqldata.RedditContract;

import java.util.ArrayList;

/**
 * Created by Hari on 16-07-2017.
 */

public class RedditInfoAdapter extends RecyclerView.Adapter<RedditInfoAdapter.MyViewHolder>{

    ArrayList<RedditInfo> redditInfos;
    Context context;

    public RedditInfoAdapter (Context context , ArrayList<RedditInfo> list)
    {
        this.context = context;
        redditInfos = list;
    }
    private class VIEW_TYPES {
        public static final int Normal = 1;
        public static final int Footer = 2;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == redditInfos.size()-1)
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch(viewType)
        {
            case VIEW_TYPES.Footer:

        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final RedditInfo currentInfo = redditInfos.get(position);
        holder.timestamp.setText(currentInfo.getTimestamp());
        holder.noOfComments.setText(String.valueOf(currentInfo.getNoOfComments()));
        holder.title.setText(currentInfo.getTitle());

        if(currentInfo.getThumbnail().contentEquals("image") || currentInfo.getThumbnail().contentEquals("default")
                || currentInfo.getThumbnail().contentEquals("self"))
            holder.thumbnail.setImageResource(R.drawable.reddit_placeholder_img);
        else {
            Glide.with(context)
                    .load(currentInfo.getThumbnail())
                    .into(holder.thumbnail);
        }
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(RedditContract.RedditInfoEntry.COLUMN_THUMBNAIL,currentInfo.getThumbnail());
                values.put(RedditContract.RedditInfoEntry.COLUMN_TITLE,currentInfo.getTitle());
                values.put(RedditContract.RedditInfoEntry.COLUMN_TIMESTAMP,currentInfo.getTimestamp());
                values.put(RedditContract.RedditInfoEntry.COLUMN_COMMENTS,currentInfo.getNoOfComments());
                context.getContentResolver().insert(RedditContract.CONTENT_URI,values);
                Toast.makeText(context,"Post saved succesfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return redditInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,timestamp,noOfComments;
        ImageView thumbnail;
        ImageView saveButton;
        ProgressBar loadingProgressBar;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_view);
            timestamp = (TextView) view.findViewById(R.id.timestamp_view);
            noOfComments = (TextView) view.findViewById(R.id.no_of_comments_view);
            thumbnail =  (ImageView) view.findViewById(R.id.thumbnail_view);
            saveButton = (ImageView) view.findViewById(R.id.save_button);
            loadingProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        }
    }

}
