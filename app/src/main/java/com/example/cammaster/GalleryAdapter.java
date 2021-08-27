package com.example.cammaster;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    public int[] imageArray ={
            R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto,R.drawable.cam,R.drawable.acm_certificate,R.drawable.muskaan,R.drawable.dummyphoto
    };

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int i) {
        return imageArray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageArray[i]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(375,300));
        imageView.setAdjustViewBounds(true);
//        imageView.setBackgroundColor(Color.rgb(255, 255, 255));
        imageView.setPadding(30,30,30,30);
        Resources res = context.getResources();
        imageView.setBackground(ResourcesCompat.getDrawable(res, R.drawable.image_border, null));
        return imageView;
    }
}
