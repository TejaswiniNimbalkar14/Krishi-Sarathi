package com.tejaswininimbalkar.krishisarathi.Booking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;

public class OwnerAdapter extends FirebaseRecyclerAdapter<OwnerModel, OwnerAdapter.ViewHolder> {


    public OwnerAdapter(@NonNull FirebaseRecyclerOptions<OwnerModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OwnerModel model) {
        holder.name.setText(model.getOwner_name());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_card_d, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.owner_name);


        }


    }
}
