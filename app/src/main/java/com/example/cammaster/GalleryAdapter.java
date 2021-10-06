package com.example.cammaster;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public ArrayList<String> xyz = new ArrayList<String>();
    public ArrayList<String> loc = new ArrayList<String>();
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> time = new ArrayList<String>();

    public GalleryAdapter(Context context, ArrayList<String> xyz, ArrayList<String> loc, ArrayList<String> date, ArrayList<String> time) {
        this.context = context;
        this.xyz = xyz;
        this.loc = loc;
        this.date = date;
        this.time = time;
    }

    @Override
    public int getCount() {
        return xyz.size();
    }

    @Override
    public Object getItem(int i) {
        return xyz.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        Picasso.get().load(xyz.get(i)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(360,250));
        imageView.setAdjustViewBounds(true);
        imageView.setBackgroundColor(Color.rgb(255, 255, 255));
        imageView.setPadding(30,30,30,30);
        Resources res = context.getResources();
        imageView.setBackground(ResourcesCompat.getDrawable(res, R.drawable.image_border, null));
        return imageView;
    }
}
