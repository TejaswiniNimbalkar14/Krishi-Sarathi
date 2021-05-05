package com.tejaswininimbalkar.krishisarathi.Owner.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_info;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class MyEquiAdapter extends RecyclerView.Adapter<MyEquiAdapter.MyEquiViewHolder> {

    ArrayList<Equipment_info> mList;
    Context context;

    public MyEquiAdapter(ArrayList<Equipment_info> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyEquiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_equip_card, parent, false);
        return new MyEquiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEquiViewHolder holder, int position) {
        Equipment_info equipment_info = mList.get(position);

        holder.equipName.setText(equipment_info.getEquipment_name());
        holder.companyName.setText(equipment_info.getEquipment_company_name());
        holder.modelYear.setText(equipment_info.getModel_year());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyEquiViewHolder extends RecyclerView.ViewHolder {
        ImageView equipImg, editBtn, dltBtn;
        TextView equipName, companyName, modelYear, rentTag, rent;

        public MyEquiViewHolder(@NonNull View itemView) {
            super(itemView);

            equipImg = itemView.findViewById(R.id.myEquipImg);
            editBtn = itemView.findViewById(R.id.myEquipEditBtn);
            dltBtn = itemView.findViewById(R.id.myEquipDltBtn);
            equipName = itemView.findViewById(R.id.myEquipName);
            companyName = itemView.findViewById(R.id.myEquipCompName);
            modelYear = itemView.findViewById(R.id.myEquipModelYear);
            rentTag = itemView.findViewById(R.id.myEquipRentTag);
            rent = itemView.findViewById(R.id.myEquipRent);
        }
    }
}
