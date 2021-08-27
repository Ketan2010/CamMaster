package com.example.cammaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        image_view = findViewById(R.id.image_view);

        Intent intent = getIntent();

        int position = intent.getExtras().getInt("l");

        GalleryAdapter galleryadapter = new GalleryAdapter(this);
        image_view.setImageResource(galleryadapter.imageArray[position]);

        Button btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogShare();
            }

            private void showBottomSheetDialogShare() {

                final BottomSheetDialog bottomSheetDialogShare = new BottomSheetDialog(ShareActivity.this,R.style.BottomSheetTheme);
                bottomSheetDialogShare.setContentView(R.layout.bottom_sheet_share_dialog_layout);

                LinearLayout copy = bottomSheetDialogShare.findViewById(R.id.download);
                bottomSheetDialogShare.show();
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

                LinearLayout copy = bottomSheetDialog.findViewById(R.id.date_layout);
                LinearLayout share = bottomSheetDialog.findViewById(R.id.location_layout);
                LinearLayout upload = bottomSheetDialog.findViewById(R.id.size_layout);
                LinearLayout download = bottomSheetDialog.findViewById(R.id.dimensions_layout);

                bottomSheetDialog.show();
            }
        });









    }
}