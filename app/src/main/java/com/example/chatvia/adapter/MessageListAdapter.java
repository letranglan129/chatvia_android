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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chatvia.ImageDetailActivity;
import com.example.chatvia.R;
import com.example.chatvia.models.ButtonIcon;
import com.example.chatvia.models.MessageGroupDay;
import com.example.chatvia.ws.receiver.Message;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private List<MessageGroupDay> messages;
    private String userId;
    private Context context;

    public void setContext(Context context,  String userId) {
        this.context = context;
        this.userId = userId;
        this.messages = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMessages(List<MessageGroupDay> messages) {
        this.messages.clear();
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_group_wrap, parent, false);
        return new MessageViewHolder(view);
    }

    @SuppressLint({"CheckResult", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Gson gson = new Gson();
        MessageGroupDay messageGroupDay = messages.get(position);

        List<List<Message>> test = messageGroupDay.getMessageList();

        for (int i = 0; i < test.size(); i++) {
            List<Message> messageList = test.get(i);
            View groupWrap = LayoutInflater.from(context).inflate(R.layout.message_group_item, null);
            ImageView avatarView = groupWrap.findViewById(R.id.messageGroup_avatar);
            LinearLayout wrapText = groupWrap.findViewById(R.id.messageGroup_wrap_text);
            LinearLayout wrapMessageGroup = groupWrap.findViewById(R.id.messageGroup_content);
            if (Objects.equals(messageList.get(0).getSenderId(), userId)) {
                wrapMessageGroup.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }


            for (int j = 0; j < messageList.size(); j++) {
                View view;
                switch (messageList.get(j).getFormat()) {
                    case "image": {
                        view = LayoutInflater.from(context).inflate(R.layout.message_image_item, null);
                        break;
                    }
                    case "file": {
                        view = LayoutInflater.from(context).inflate(R.layout.message_file_item, null);
                        break;
                    }
                    default: {
                        view = LayoutInflater.from(context).inflate(R.layout.message_text_item, null);
                        break;
                    }
                }

                LinearLayout wrapMsg = view.findViewById(R.id.messageTextItem_layout);
                TextView msg = wrapMsg.findViewById(R.id.messageTextItem_content);
                LinearLayout imageContainer = wrapMsg.findViewById(R.id.messageTextItem_imageContainer);
                TextView msgTime = wrapMsg.findViewById(R.id.messageTextItem_time);
                TextView msgViewed = wrapMsg.findViewById(R.id.messageTextItem_viewed);
                TextView groupTime = wrapMessageGroup.findViewById(R.id.messageGroup_time_textview);
                LocalDateTime dateTime = LocalDateTime.parse(messageList.get(j).getSentAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String sentAt = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                switch (messageList.get(j).getFormat()) {
                    case "image": {
                        messageList.get(j).getFiles().forEach(file -> {
                            ImageView imageView = new ImageView(context);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            imageView.setPadding(4,4,4,4);
                            Glide.with(context).load(file.getHref()).error(R.drawable.no_avatar).fitCenter().into(imageView);


                            imageView.setOnClickListener(view1 -> {
                                Intent intent = new Intent(context, ImageDetailActivity.class);
                                intent.putExtra("href", file.getHref());
                                intent.putExtra("name", file.getName());
                                context.startActivity(intent);
                            });
                            imageContainer.addView(imageView);
                        });
                        break;
                    }
                    case "file": {
                        msg.setText(messageList.get(j).getFiles().get(0).getName());
                        break;
                    }
                    default: {
                        msg.setText(messageList.get(j).getMessage());
                        break;
                    }
                }


                msgTime.setText(sentAt);
                msgTime.setVisibility(View.GONE);
                msgViewed.setVisibility(View.GONE);

                if (j == 0) {
                    groupTime.setText(sentAt);
                }

                if (j == messageList.size() - 1) {
                    msgTime.setVisibility(View.VISIBLE);
                }

                int finalJ = j;
                wrapMsg.setOnClickListener(view1 -> {
                    if (Objects.equals(messageList.get(0).getSenderId(), userId)) {
                        if(messageList.get(finalJ).getViewedAt() != null) {
                            msgViewed.setVisibility(View.VISIBLE);
                        } else {
                            msgViewed.setText("Đã gửi");
                            msgViewed.setVisibility(View.VISIBLE);
                        }
                    }
                });

                wrapMsg.setOnLongClickListener(view1 -> {
                    MaterialDialog dialog = new MaterialDialog(context, new BottomSheet(LayoutMode.WRAP_CONTENT));
                    List<ButtonIcon> buttonIcons = new ArrayList<>();
                    buttonIcons.add(new ButtonIcon(R.drawable.copy_icon, "Sao chép"));
                    buttonIcons.add(new ButtonIcon(R.drawable.share_icon, "Chuyển tiếp"));
                    buttonIcons.add(new ButtonIcon(R.drawable.delete_icon, "Xóa phía tôi"));
                    buttonIcons.add(new ButtonIcon(R.drawable.delete_icon, "Xóa hai phía"));

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

                wrapText.addView(wrapMsg);

            }

            holder.layoutWrapMessage.addView(groupWrap);
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutWrapMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutWrapMessage = itemView.findViewById(R.id.messageGroup_wrap_layout);
        }
    }
}
