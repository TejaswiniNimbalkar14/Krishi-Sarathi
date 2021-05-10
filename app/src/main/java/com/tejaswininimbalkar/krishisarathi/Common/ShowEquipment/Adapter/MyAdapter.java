package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Adapter;

/*
 * @author Devendra Kharatmal
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.AgriEquipmentFragment;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.MyModel;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.EquiDetailsFragment;
import com.tejaswininimbalkar.krishisarathi.R;

public class MyAdapter extends FirebaseRecyclerAdapter<MyModel, MyAdapter.MyViewHolder> {

    public MyAdapter(@NonNull FirebaseRecyclerOptions<MyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position, @NonNull MyModel myModel) {
        myViewHolder.name.setText(myModel.getEquip_name());
       /* myViewHolder.price.setText(myModel.getMachine_Price());
        myViewHolder.description.setText(myModel.getDescp());*/
        Glide.with(myViewHolder.img.getContext()).load(myModel.getEquip_img_Url()).into(myViewHolder.img);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompat activity = (AppCompat) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new EquiDetailsFragment(myModel.getEquip_name()
                                , myModel.getEquip_img_Url())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_equipment, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, description;
        ImageView img;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            img = itemView.findViewById(R.id.machineImg);
            name = itemView.findViewById(R.id.Name);
            description = itemView.findViewById(R.id.Description);

        }
    }
}
