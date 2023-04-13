package com.example.chatvia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatvia.R;

public class SettingListViewAdapter extends BaseAdapter {
    Context context;
    String[] title;
    LayoutInflater inflater;

    public SettingListViewAdapter(Context ctx, String[] title) {
        this.context = ctx;
        this.title = title;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.setting_listitem, null);
        TextView txtView = (TextView) view.findViewById(R.id.textView);
        txtView.setText(title[i]);
        return view;
    }
}
