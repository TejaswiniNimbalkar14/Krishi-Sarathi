package com.tejaswininimbalkar.krishisarathi.Common.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import com.tejaswininimbalkar.krishisarathi.R;
public class Dashboard extends Activity {

    /** Called when the activity is first created. */
    private GridviewAdapter mAdapter;
    private ArrayList<String> listTitle;

    // for image slider
    private ArrayList<Integer> listImage;
    int[] images = {

            R.drawable.first_image,
            R.drawable.second_image,
            R.drawable.third_image,
            R.drawable.fourth_image,
            R.drawable.fifth_image,
            R.drawable.sixth_image
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // for image slider
        SliderView sliderView = findViewById ( R.id.imageSlider );


        // for image slider
        SliderAdapter sliderAdapter = new SliderAdapter ( images );
        sliderView.setSliderAdapter ( sliderAdapter );
        sliderView.setIndicatorAnimation ( IndicatorAnimationType.WORM );
        sliderView.setSliderTransformAnimation ( SliderAnimations.DEPTHTRANSFORMATION );
        sliderView.startAutoCycle ( );


        prepareList();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listTitle, listImage);


        // Set custom adapter to gridview
        GridView gridView = findViewById ( R.id.gridView1 );
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener

        gridView.setOnItemClickListener( (arg0, arg1, position, arg3) -> {
            Toast.makeText( Dashboard.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent ( getApplicationContext (),GridItemActivity.class );
            startActivity (intent);

        } );

    }




    public void prepareList()
    {
        listTitle = new ArrayList<> ( );

        listTitle.add("Farmer");
        listTitle.add("Equipments");
        listTitle.add("Fertilisers");
        listTitle.add("Market price");
        listTitle.add("Seeds");
        listTitle.add("Transportation");


        listImage = new ArrayList<> ( );
        listImage.add(R.drawable.farmer_image);
        listImage.add(R.drawable.equipments_image );
        listImage.add(R.drawable.fertilizer_image);
        listImage.add(R.drawable.marketpr_image);
        listImage.add(R.drawable.seed_image);
        listImage.add(R.drawable.transportation_image);






    }


}