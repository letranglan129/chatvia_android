package com.example.chatvia.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollToTopDataObserver extends RecyclerView.AdapterDataObserver {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    public ScrollToTopDataObserver(LinearLayoutManager layoutManager, RecyclerView recyclerView) {
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

        if (lastVisiblePosition == -1 || positionStart >= itemCount - 1 && lastVisiblePosition == positionStart - 1) {
            recyclerView.scrollToPosition(positionStart);
        }
    }
}
