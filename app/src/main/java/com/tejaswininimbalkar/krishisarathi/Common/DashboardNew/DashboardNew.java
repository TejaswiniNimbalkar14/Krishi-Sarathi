package com.tejaswininimbalkar.krishisarathi.Common.DashboardNew;


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


public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    private GridviewAdapter mAdapter;
    private ArrayList<String> listTitle;

    // for image slider
    private ArrayList<Integer> listImage;
    int[] images = {

            R.drawable.image_slider_one,
            R.drawable.image_slider_two,
            R.drawable.image_slider_three,
            R.drawable.image_slider_four,
            R.drawable.image_slider_five,
            R.drawable.image_slider_six
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try {
        setContentView ( R.layout.dashboardnew );
        //}
        //catch (Exception e)
        //{
        // Toast.makeText ( this,e.getMessage (), Toast.LENGTH_SHORT ).show ( );
        // }

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
            Toast.makeText(MainActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent ( getApplicationContext (),GridItemActivity.class );
            startActivity (intent);

        } );

    }




    public void prepareList()
    {
        listTitle = new ArrayList<> ( );

        listTitle.add("Farmer");
        listTitle.add("Equipment");
        listTitle.add("Fertilisers");
        listTitle.add("Market price");
        listTitle.add("Seeds");
        listTitle.add("Transports");


        listImage = new ArrayList<> ( );
        listImage.add(R.drawable.image_farmer );
        listImage.add(R.drawable.image__equipments );
        listImage.add(R.drawable.image_fertiliser );
        listImage.add(R.drawable.image_marketpr );
        listImage.add(R.drawable.image_seed );
        listImage.add(R.drawable.image_transportation );






    }


}
