package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment;

/*
 * @author Devendra Kharatmal
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Adapter.MyAdapter;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.MyModel;
import com.tejaswininimbalkar.krishisarathi.R;

public class AgriEquipmentFragment extends Fragment {
    RecyclerView recView;
    MyAdapter adapter;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agri_equipment, container, false);

        if (!isConnected(getActivity())) {
            showConnectionDialog();
        }

        progressBar = (ProgressBar) view.findViewById(R.id.equiProgress);
        recView = (RecyclerView) view.findViewById(R.id.agriRecyclerView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<MyModel> options = new FirebaseRecyclerOptions.Builder<MyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Equipment"), MyModel.class)
                .build();
        adapter = new MyAdapter(options);
        recView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected());
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Please connect to the internet to move further!");
        alertDialog.setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)));
        alertDialog.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}