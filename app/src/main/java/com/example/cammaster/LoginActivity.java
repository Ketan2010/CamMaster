package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    ImageView img_cam;

    private  int REQUEST_CODE_PERMISSION = 101;
    private  final  String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img_cam = (ImageView) findViewById(R.id.img_cam);

        if(AllPermissionsGranted())
        {

        }else{
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION);
        }

        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CameraActivity.class);
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