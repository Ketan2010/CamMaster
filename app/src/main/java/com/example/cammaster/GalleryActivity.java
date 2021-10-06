package com.example.cammaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryActivity extends AppCompatActivity {
    GridView gridView;
    DisplayMetrics displayMetrics;
    Button img_cam;
    private FirebaseAuth mAuth;
    private  FirebaseDatabase mDatabase;
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
        Intent intent = getIntent();
        String month = intent.getExtras().getString("month");
        String year = intent.getExtras().getString("year");
        getSupportActionBar().setTitle("Gallery");
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        double y = Math.pow(screenHeight /displayMetrics.xdpi, 2);
        double x = Math.pow(screenWidth /displayMetrics.xdpi, 2);

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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("images");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> imgarr = new ArrayList<String>();
                ArrayList<String> loc = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> time = new ArrayList<String>();
                int i = 0;
                for(DataSnapshot datas:snapshot.getChildren())
                {
                    String path=datas.child("path").getValue().toString();
                    String datef=datas.child("date").getValue().toString();
                    String timef=datas.child("time").getValue().toString();
                    String locf=datas.child("location").getValue().toString();
                    if(month.equals("0") && year.equals("0"))
                    {
                        Collections.reverse(loc);
                        Collections.reverse(date);
                        Collections.reverse(imgarr);
                        Collections.reverse(time);
                        loc.add(locf);
                        date.add(datef);
                        imgarr.add(path);
                        time.add(timef);
                        i=i+1;
                        Collections.reverse(loc);
                        Collections.reverse(date);
                        Collections.reverse(imgarr);
                        Collections.reverse(time);
                    }
                    else {
                        if (datef.substring(3, 5).equals(month) && datef.substring(6, 10).equals(year)) {
                            Collections.reverse(loc);
                            Collections.reverse(date);
                            Collections.reverse(imgarr);
                            Collections.reverse(time);
                            loc.add(locf);
                            date.add(datef);
                            imgarr.add(path);
                            time.add(timef);
                            i=i+1;
                            Collections.reverse(loc);
                            Collections.reverse(date);
                            Collections.reverse(imgarr);
                            Collections.reverse(time);
                        }
                    }
                }
                GalleryAdapter galleryadapter = new GalleryAdapter(getApplicationContext(), imgarr, loc, date, time);
                gridView.setAdapter(galleryadapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        gridView.setAdapter(new GalleryAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ShareActivity.class);
                intent.putExtra("l",i);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(GalleryActivity.this, LoginActivity.class));
                break;

            case R.id.filter:
                startActivity(new Intent(GalleryActivity.this, FilterActivity.class));
        }
        return true;
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