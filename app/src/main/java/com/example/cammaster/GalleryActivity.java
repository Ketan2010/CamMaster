package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryActivity extends AppCompatActivity {

    GridView gridView;
    DisplayMetrics displayMetrics;
    Button img_cam;

    private  int REQUEST_CODE_PERMISSION = 101;
    private  final  String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };


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

        img_cam = (Button) findViewById(R.id.img_cam);
        if(AllPermissionsGranted())
        {

        }else{
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION);
        }

        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });

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

    private boolean AllPermissionsGranted()
    {
        for(String permissions : REQUIRED_PERMISSIONS)
        {
            if(ContextCompat.checkSelfPermission(this,permissions)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    };
}