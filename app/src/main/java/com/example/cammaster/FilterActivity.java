package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class FilterActivity extends AppCompatActivity {
    public String month = "09";
    public String year = "2021";
    public ArrayList<String> m = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        m.add("January");
        m.add("February");
        m.add("March");
        m.add("April");
        m.add("May");
        m.add("June");
        m.add("July");
        m.add("August");
        m.add("September");
        m.add("October");
        m.add("November");
        m.add("December");
        Button filterBtn = findViewById(R.id.filterBtn);
        Spinner spnM = findViewById(R.id.spinnerM);
        ArrayAdapter<CharSequence> adapterM=ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnM.setAdapter(adapterM);
        Spinner spnY = findViewById(R.id.spinnerY);
        ArrayAdapter<CharSequence> adapterY=ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnY.setAdapter(adapterY);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textM = spnM.getSelectedItem().toString();
                String textY = spnY.getSelectedItem().toString();
                int indexM = m.indexOf(textM) + 1;
                String temp;
                if(indexM < 10){
                    temp = "0" + indexM;
                }
                else{
                    temp = "" + indexM;
                }
                System.out.println("Month: " + temp);
                System.out.println("Year: " + textY);
                Intent intent = new Intent(getApplicationContext(),GalleryActivity.class);
                intent.putExtra("month", temp);
                intent.putExtra("year", textY);
                startActivity(intent);
            }
        });
    }
}