package com.example.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dto.ActividadDiariaDTO;
import com.example.adapter.ActividadesDiariasAdapter;
import com.example.studycalendar.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;

import com.example.studycalendar.R;

import java.util.ArrayList;
import java.util.Date;

public class DailyStudy extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_study);


        mDatabaseHelper = new DatabaseHelper(this);

        ListView listView = findViewById(R.id.lvDailyItems);
        ActividadesDiariasAdapter ActividadesDiariasAdapter = new ActividadesDiariasAdapter(getData(),DailyStudy.this);
        listView.setAdapter(ActividadesDiariasAdapter);
    }

    public ArrayList<ActividadDiariaDTO> getData(){

        mDatabaseHelper = new DatabaseHelper(this);
        ArrayList<ActividadDiariaDTO> dailyActivities = mDatabaseHelper.getActivitiesByDate(new Date());
        return dailyActivities;

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
