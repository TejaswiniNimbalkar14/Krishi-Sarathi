package com.tejaswininimbalkar.krishisarathi.Owner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.tejaswininimbalkar.krishisarathi.Owner.Equipment.EquipmentGetInfoFromOwner;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_add_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;


public class Equip_add_adapter extends RecyclerView.Adapter<Equip_add_adapter.ViewHolder> {

    Context context;
    ArrayList<Equipment_add_model> list;

    public Equip_add_adapter(Context context, ArrayList<Equipment_add_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_equipment_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.equip_name.setText(list.get(position).getEquip_name());
        //holder.equip_img.setImageResource(list.get(position).getEquipment_img());
        Picasso.get().load(list.get(position).getEquip_img_Url()).into(holder.equip_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView equip_img;
        TextView equip_name;
        FloatingActionButton floatingActionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            equip_img = itemView.findViewById(R.id.equipment_image);
            equip_name = itemView.findViewById(R.id.equipment_Name);
            floatingActionButton = itemView.findViewById(R.id.floating_button);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Equipment_add_model temp = list.get(getAdapterPosition());
                    Toast.makeText(context, equip_name.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, EquipmentGetInfoFromOwner.class);
                    intent.putExtra("Equip_img", temp.getEquip_img_Url());
                    intent.putExtra("Equip_name", temp.getEquip_name());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

}
