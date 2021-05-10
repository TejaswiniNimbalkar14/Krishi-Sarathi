package com.tejaswininimbalkar.krishisarathi.Common.Navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter.YourOrderFragmentAdapter;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.PendingModel;

public class YourOrdersActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager2 pager2;
    YourOrderFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);


        Intent intent = getIntent();
        String ownerId = intent.getStringExtra("ownerId");

        Toast.makeText(getApplicationContext() ,""+ownerId,Toast.LENGTH_LONG).show();

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new YourOrderFragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image_wallclock));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image_correction).setText(getResources().getString(R.string.orders)));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image_history));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}