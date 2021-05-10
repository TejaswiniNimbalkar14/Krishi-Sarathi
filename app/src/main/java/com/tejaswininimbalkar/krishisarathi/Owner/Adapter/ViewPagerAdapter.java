package com.tejaswininimbalkar.krishisarathi.Owner.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.EquipmentRequestAccept;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.Equipment_Request_To_Owner;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.AcceptedFragment;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.HistoryFragment;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.PendingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1 :
                return new EquipmentRequestAccept();
        }

        return new Equipment_Request_To_Owner();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
