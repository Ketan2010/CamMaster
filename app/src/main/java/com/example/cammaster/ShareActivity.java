package com.example.cammaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    ImageView image_view;
    public String dateD;
    public String timeD;
    public String locD;
    public String pathD;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        image_view = findViewById(R.id.image_view);
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("l");
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
                GalleryAdapter galleryadapter = new GalleryAdapter(getApplicationContext(), imgarr, loc, date, time);
                Picasso.get().load(galleryadapter.xyz.get(position)).into(image_view);
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ShareActivity.this,R.style.BottomSheetTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_details_dialog_layout);
                dateD = galleryadapter.date.get(position);
                locD = galleryadapter.loc.get(position);
                timeD = galleryadapter.time.get(position);
                pathD = galleryadapter.xyz.get(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image_view.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),
                    bitmap, "Some Title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });

        Button mBottton = findViewById(R.id.btn_details1);
        mBottton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
            private void showBottomSheetDialog() {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ShareActivity.this,R.style.BottomSheetTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_details_dialog_layout);
                TextView dateT = bottomSheetDialog.findViewById(R.id.date);
                TextView locT = bottomSheetDialog.findViewById(R.id.location);
                TextView timeT = bottomSheetDialog.findViewById(R.id.time);
                dateT.setText(dateD);
                locT.setText(locD);
                timeT.setText(timeD);
                bottomSheetDialog.show();
            }
        });
    }

}