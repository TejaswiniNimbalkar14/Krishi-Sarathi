//Adapter classes allow us to bind data to a design(activity)
//This class will bind data on on slides of the 'OnBoarding'

package com.tejaswininimbalkar.krishisarathi.Common.OnBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.tejaswininimbalkar.krishisarathi.R;

/*
 * @author Tejaswini Nimbalkar
 */

public class SliderAdapter extends PagerAdapter {

    //'context' means the activity
    Context context;
    LayoutInflater layoutInflater;
    //Arrays of the data to display
    int[] images = {
            R.drawable.image_onboarding1,
            R.drawable.image_onboarding2,
            R.drawable.image_onboarding3,
            R.drawable.image_onboarding4
    };
    int[] headings = {
            R.string.find_equipment,
            R.string.owner_feature,
            R.string.location_weather,
            R.string.locaization
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //To get count of the slides
    @Override
    public int getCount() {
        return headings.length;
    }

    //To check whether the Viewpager is form ConstraintLayout
    //(is view from an object)
    //1.Set the view
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    //2.Instantiate the view
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //Request system to use service of the design(layout)
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //pointing to the layout that we want to use(slides_layout)
        View view = layoutInflater.inflate(R.layout.slides_layout, container, false);

        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);

        //Set data to the views on slides
        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);

        //Add the created view to container
        container.addView(view);

        return view;
    }

    //3.Destroy the view
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
