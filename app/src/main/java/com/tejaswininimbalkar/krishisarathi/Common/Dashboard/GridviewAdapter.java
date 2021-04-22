package com.tejaswininimbalkar.krishisarathi.Common.Dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.tejaswininimbalkar.krishisarathi.R;
public class GridviewAdapter extends BaseAdapter
{
    private final ArrayList<String> listTitle;
    private final ArrayList<Integer> listImage;
    private final Activity activity;

    public GridviewAdapter(Activity activity,ArrayList<String> listTitle, ArrayList<Integer> listImage) {
        super();
        this.listTitle = listTitle;
        this.listImage = listImage;
        this.activity = activity;
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

    public static class ViewHolder
    {
        public ImageView imgViewImage;
        public TextView txtViewTitle;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.txtViewTitle = convertView.findViewById(R.id.textView1);
            view.imgViewImage = convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitle.setText(listTitle.get(position));
        view.imgViewImage.setImageResource(listImage.get(position));

        return convertView;
    }
}