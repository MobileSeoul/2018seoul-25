package com.seoulite_android.seoulite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FavCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView mStarIcon;
    public TextView mNameTextView;
    public TextView mMemoTextView;

    public FavCardViewHolder(@NonNull View itemView) {
        super(itemView);
        mStarIcon = itemView.findViewById(R.id.fav_star_img);
        mNameTextView = itemView.findViewById(R.id.fav_card_name);
        mMemoTextView = itemView.findViewById(R.id.fav_card_memo);
    }
}
