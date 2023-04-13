package com.example.chatvia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chatvia.ImageDetailActivity;
import com.example.chatvia.R;
import com.example.chatvia.models.FileMessage;

import java.util.List;

public class ManagerImageAdapter extends RecyclerView.Adapter<ManagerImageAdapter.ImageViewHolder> {

    private List<FileMessage> mImageUrls;
    private Context mContext;

    public ManagerImageAdapter(Context context, List<FileMessage> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(mContext).setDefaultRequestOptions(new RequestOptions().override(600))
                .load(mImageUrls.get(position).getHref())
                .fitCenter()
                .into(holder.imageView);

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ImageDetailActivity.class);
            intent.putExtra("href", mImageUrls.get(position).getHref());
            intent.putExtra("name", mImageUrls.get(position).getName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.managerImage_imageView);
            cardView = itemView.findViewById(R.id.managerImage_cardView);
        }
    }
}
