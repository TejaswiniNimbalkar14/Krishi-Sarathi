package com.tejaswininimbalkar.krishisarathi.Common.Dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

/*
 * @author Leena Bhadane and Bhagyashri Bharule
 */

public class GridviewAdapter extends BaseAdapter {
    private final ArrayList<String> listTitle;
    private final ArrayList<Integer> listImage;
    //private final Activity activity;
    private final Fragment fragment;

    public GridviewAdapter(Fragment fragment, ArrayList<String> listTitle, ArrayList<Integer> listImage) {
        super();
        this.listTitle = listTitle;
        this.listImage = listImage;
        this.fragment = fragment;
        //this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listTitle.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = fragment.getLayoutInflater();

        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.txtViewTitle = convertView.findViewById(R.id.textView1);
            view.imgViewImage = convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitle.setText(listTitle.get(position));
        view.imgViewImage.setImageResource(listImage.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public ImageView imgViewImage;
        public TextView txtViewTitle;
    }
}