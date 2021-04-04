//This adapter class will allow to bind the data to recycler view
package com.tejaswininimbalkar.krishisarathi.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tejaswininimbalkar.krishisarathi.R;

import java.util.List;

/*
 * @author Tejaswini Nimbalkar
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    List<String> language_list;

    public LanguageAdapter(List<String> list) {
        language_list = list;
    }

    //1.Create view holder
    @NonNull
    @Override
    public LanguageAdapter.LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_language, parent, false);
        LanguageViewHolder languageViewHolder = new LanguageViewHolder(view);
        return languageViewHolder;

    }

    //2.Bind data to holder
    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.LanguageViewHolder holder, int position) {

        holder.language.setText(language_list.get(position));

    }

    @Override
    public int getItemCount() {
        return language_list.size();
    }

    //View holder to hold the language list items
    public class LanguageViewHolder extends RecyclerView.ViewHolder{

        TextView language;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);

            language = itemView.findViewById(R.id.languageTV);
        }
    }
}
