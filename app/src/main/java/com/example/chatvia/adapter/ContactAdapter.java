package com.example.chatvia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.LayoutMode;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.bottomsheets.BottomSheet;
import com.afollestad.materialdialogs.customview.DialogCustomViewExtKt;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chatvia.ChatDetailActivity;
import com.example.chatvia.R;
import com.example.chatvia.models.ButtonIcon;
import com.example.chatvia.models.User;
import com.example.chatvia.ws.Command;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;

    private List<User> users;
    private String userId;

    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setContext(Context context, String userId) {
        this.context = context;
        this.userId = userId;
        this.users = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user = users.get(position);

        if(user == null)
            return;
        if(user.getAvatar() == null || user.getAvatar().isEmpty()) {
            Glide.with(context).load(R.drawable.no_avatar).centerCrop().into(holder.imgView);
        } else {
            Glide.with(context).load(user.getAvatar()).error(R.drawable.no_avatar).centerCrop().into(holder.imgView);
        }
        holder.txtView.setText(user.getFullname());
        holder.wrap.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatDetailActivity.class);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("command", Command.SEND_START_CHAT_PRIVATE);
            jsonObject.addProperty("senderId", userId);
            jsonObject.addProperty("receiverId", user.getId());

            intent.putExtra("jsonGetChatDetail", jsonObject.toString());

            context.startActivity(intent);
        });

        holder.wrap.setOnLongClickListener(view -> {
            MaterialDialog dialog = new MaterialDialog(context, new BottomSheet(LayoutMode.WRAP_CONTENT));
            List<ButtonIcon> buttonIcons = new ArrayList<>();
            buttonIcons.add(new ButtonIcon(R.drawable.user_time, "Hủy kết bạn"));
            buttonIcons.add(new ButtonIcon(R.drawable.block_icon, "Chặn"));

            View customView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_view, null);
            CoordinatorLayout layout = customView.findViewById(R.id.bottomSheet_wrap);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            buttonIcons.forEach(buttonIcon -> {
                View bottomSheetItemView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_item_icon, null);
                LinearLayout wrap = bottomSheetItemView.findViewById(R.id.bottomSheet_item_wrap);
                TextView text = bottomSheetItemView.findViewById(R.id.bottomSheet_item_text);
                ImageView img = bottomSheetItemView.findViewById(R.id.bottomSheet_item_icon);
                text.setText(buttonIcon.getText());
                img.setImageResource(buttonIcon.getIcon());

                wrap.setOnClickListener(view2 -> {
                    Toast.makeText(context, buttonIcon.getText(), Toast.LENGTH_SHORT).show();
                });

                linearLayout.addView(bottomSheetItemView);
            });

            layout.addView(linearLayout);
            DialogCustomViewExtKt.customView(dialog, null, customView, false, false, false, false);
            dialog.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if(users != null)
            return users.size();
        return 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imgView;
        private TextView txtView;
        private LinearLayout wrap;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.item_contact_avatar);
            txtView = itemView.findViewById(R.id.item_contact_fullname);
            wrap = itemView.findViewById(R.id.item_contact_wrap);
        }
    }
}
