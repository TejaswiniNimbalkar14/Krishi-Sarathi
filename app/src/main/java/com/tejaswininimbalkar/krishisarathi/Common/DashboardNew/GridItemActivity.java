package com.tejaswininimbalkar.krishisarathi.Common.DashboardNew;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GridItemActivity extends AppCompatActivity {

    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // for full screen
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_grid_item );

        Title = findViewById ( R.id.gridTV );

        Intent intent = getIntent ();
        Title.setText ( intent.getStringExtra( "Title"));

    }
}