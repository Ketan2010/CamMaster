package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GalleryActivity extends AppCompatActivity {

    GridView gridView;
    DisplayMetrics displayMetrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setTitle("Gallery");

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        double y = Math.pow(screenHeight /displayMetrics.xdpi, 2);
        double x = Math.pow(screenWidth /displayMetrics.xdpi, 2);
        double screenInches = Math.sqrt(x + y);
        screenInches = (double) Math.round(screenInches *10)/10;
        System.out.println("UUUUUereka   "+screenWidth);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new GalleryAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ShareActivity.class);
                intent.putExtra("l",i);
                startActivity(intent);
            }
        });
    }
}