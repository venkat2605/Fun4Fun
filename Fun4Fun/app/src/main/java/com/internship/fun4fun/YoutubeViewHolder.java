package com.internship.fun4fun;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class YoutubeViewHolder extends RecyclerView.ViewHolder {

    public ImageView videoThumbnailImageView;

    public TextView videoTitle,videoTime;
    public LinearLayout parentLayout;

    public YoutubeViewHolder(View itemView) {
        super(itemView);
        videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view);
        videoTitle = itemView.findViewById(R.id.video_title_label);
        videoTime=itemView.findViewById(R.id.video_time);
        parentLayout =itemView.findViewById(R.id.parentLayout);
    }
}
