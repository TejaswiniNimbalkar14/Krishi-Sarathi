package com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tejaswininimbalkar.krishisarathi.User.YourOrders.AcceptedFragment;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.HistoryFragment;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.PendingFragment;

public class YourOrderFragmentAdapter extends FragmentStateAdapter {



    public YourOrderFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new AcceptedFragment();
            case 2 :
                return new HistoryFragment();
        }

        return new PendingFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
