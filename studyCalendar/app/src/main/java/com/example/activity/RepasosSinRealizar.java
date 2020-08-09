package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.adapter.ActividadesDiariasAdapter;
import com.example.dto.ActividadDiariaDTO;
import com.example.studycalendar.DatabaseHelper;
import com.example.studycalendar.R;

import java.util.ArrayList;
import java.util.Date;

public class RepasosSinRealizar extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repasos_sin_realizar);


        mDatabaseHelper = new DatabaseHelper(this);

        //lvDailyItems se refiere a el componente que contiene cada unos de los elementos de las actividades diarias
        ListView listView = findViewById(R.id.lvDailyItems);
        ActividadesDiariasAdapter ActividadesDiariasAdapter = new ActividadesDiariasAdapter(getData(),RepasosSinRealizar.this);
        listView.setAdapter(ActividadesDiariasAdapter);
    }

    public ArrayList<ActividadDiariaDTO> getData(){
        mDatabaseHelper = new DatabaseHelper(this);
        ArrayList<ActividadDiariaDTO> dailyActivities = mDatabaseHelper.getRepasosNoRealizados();
        return dailyActivities;
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
