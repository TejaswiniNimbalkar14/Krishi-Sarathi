package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Adapter;

/*
 * @author Devendra Kharatmal
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.User.Booking.BookingActivity;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {

    ArrayList<OwnerModel> mList;
    Context context;
    String e_name;

    public OwnerAdapter(Context context ,ArrayList<OwnerModel> mList ,String e_name) {
        this.mList = mList;
        this.context = context;
        this.e_name = e_name;
    }

    @NonNull
    @Override
    public OwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_owner, parent ,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerAdapter.ViewHolder holder, int position) {
        OwnerModel ownerModel = mList.get(position);
        holder.ownerName.setText(ownerModel.getUserName());
        holder.ownerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();

                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("name",e_name);
                intent.putExtra("key",ownerModel.getOwner_ID());
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ownerName;
        CardView ownerCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerName = itemView.findViewById(R.id.owner_name);
            ownerCardView = itemView.findViewById(R.id.owner_cardView);
        }
    }



}
