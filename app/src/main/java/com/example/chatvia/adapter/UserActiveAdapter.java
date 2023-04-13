package com.example.chatvia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatvia.R;
import com.example.chatvia.models.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class UserActiveAdapter extends RecyclerView.Adapter<UserActiveAdapter.UserActiveViewHolder> {
    private List<User> users;
    private Context context;
    private String userId;

    public void setContext(Context context, String userId) {
        this.context = context;
        this.users = new ArrayList<>();
        this.userId = userId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_active, parent, false);
        return new UserActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserActiveViewHolder holder, int position) {
        User user = users.get(position);

        if(user == null) {
            return;
        }

        holder.txtView.setText(user.getFullname().substring(user.getFullname().indexOf(' ') + 1));
        Glide.with(context).load(user.getAvatar()).error(R.drawable.no_avatar).centerCrop().into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        if(users != null)
            return  users.size();

        return 0;
    }

    public class UserActiveViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView imgView;
        public TextView txtView;

        public UserActiveViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.itemUserActive_imageview);
            txtView = itemView.findViewById(R.id.itemUserActive_textview);
        }
    }
}
