//This adapter class will allow to bind the data to recycler view
package com.tejaswininimbalkar.krishisarathi.Common.Localization;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tejaswininimbalkar.krishisarathi.R;

import java.util.List;

/*
 * @author Tejaswini Nimbalkar
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private ItemClickListener mListener;

    private int isSelected = -1;

    private List<LanguageDTO> mLanguageList;

    public LanguageAdapter(ItemClickListener listener, int selectedLanguagePosition, List<LanguageDTO> languageList) {
        if (selectedLanguagePosition > -1) {
            isSelected = selectedLanguagePosition;
        }
        mLanguageList = languageList;
        mListener = listener;
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
        holder.language.setText(mLanguageList.get(position).getLanguageTitle());
        holder.language.setTextColor(position == isSelected ? ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_700) :
                ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
    }

    @Override
    public int getItemCount() {
        return mLanguageList == null ? 0 : mLanguageList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position, String language);
    }

    //View holder to hold the language list items
    public class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView language;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);

            language = itemView.findViewById(R.id.languageTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            if (mLanguageList != null && pos != -1 && pos < mLanguageList.size()) {
                isSelected = pos;
                mListener.onItemClick(pos, mLanguageList.get(pos).getLanguageTitle());
            }
        }
    }
}
