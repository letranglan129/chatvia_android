package com.example.chatvia.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatvia.ui.ChatTabFragment;
import com.example.chatvia.ui.ContactTabFragment;
import com.example.chatvia.ui.GroupTabFragment;
import com.example.chatvia.ui.SettingTabFragment;

public class NavigationViewPagerAdapter extends FragmentStateAdapter {
    public NavigationViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ContactTabFragment();
            case 2:
                return new GroupTabFragment();
            case 3:
                return new SettingTabFragment();
            default:
                return new ChatTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
