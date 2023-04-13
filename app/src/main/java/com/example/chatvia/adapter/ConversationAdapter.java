package com.example.chatvia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.bumptech.glide.Glide;
import com.example.chatvia.ChatDetailActivity;
import com.example.chatvia.R;
import com.example.chatvia.models.ButtonIcon;
import com.example.chatvia.models.Conversation;
import com.example.chatvia.ws.Command;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<Conversation> conversations;
    private Context context;
    private String userId;

    public void setContext(Context context, String userId) {
        this.context = context;
        this.conversations = new ArrayList<>();
        this.userId = userId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setConversations(List<Conversation> conversations) {
        conversations.sort((c1, c2) -> {
            @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return Objects.requireNonNull(format.parse(c2.getSentAt())).compareTo(format.parse(c1.getSentAt()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @SuppressLint({"CheckResult", "ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = conversations.get(position);

        if (conversation == null)
            return;

        LocalDateTime dateTime = LocalDateTime.parse(conversation.getSentAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sentAt = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        if(conversation.getAvatar() == null || conversation.getAvatar().isEmpty()) {
            Glide.with(context).load(R.drawable.no_avatar).centerCrop().into(holder.imgView);
        } else {
            Glide.with(context).load(conversation.getAvatar()).error(R.drawable.no_avatar).centerCrop().into(holder.imgView);
        }

        holder.txtViewName.setText(Objects.equals(conversation.type, "multi") ? conversation.getGroupName() : conversation.getReceiverName());
        holder.txtViewLastMessage.setText(conversation.getMessage());
        holder.txtViewTime.setText(sentAt);

        holder.wrapLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatDetailActivity.class);
            JsonObject jsonObject = new JsonObject();
            if (Objects.equals(conversation.getType(), "multi")) {
                jsonObject.addProperty("command", Command.SEND_START_CHAT_MULTI);
                jsonObject.addProperty("userId", userId);
                jsonObject.addProperty("groupId", conversation.getGroupId());
            } else {
                jsonObject.addProperty("command", Command.SEND_START_CHAT_PRIVATE);
                jsonObject.addProperty("senderId", userId);
                jsonObject.addProperty("receiverId", conversation.getReceiverId());
            }

            intent.putExtra("jsonGetChatDetail", jsonObject.toString());

            context.startActivity(intent);

        });
        holder.wrapLayout.setOnLongClickListener(view -> {
            MaterialDialog dialog = new MaterialDialog(context, new BottomSheet(LayoutMode.WRAP_CONTENT));
            List<ButtonIcon> buttonIcons = new ArrayList<>();
            buttonIcons.add(new ButtonIcon(R.drawable.delete_icon, "Xóa cuộc trò chuyện"));
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
        if(conversations != null)
            return  conversations.size();
        return 0;
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imgView;
        private TextView txtViewName;
        private TextView txtViewLastMessage;
        private TextView txtViewTime;
        private LinearLayout wrapLayout;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.conversationitem_imageview_avatar);
            txtViewName = itemView.findViewById(R.id.conversationitem_textview_name);
            txtViewLastMessage = itemView.findViewById(R.id.conversationitem_textview_lastmessage
            );
            txtViewTime = itemView.findViewById(R.id.conversationitem_textview_time);
            wrapLayout = itemView.findViewById(R.id.conversationitem_linearlayout);
        }
    }
}
