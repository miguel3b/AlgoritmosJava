package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.dto.ActividadDiariaDTO;
import com.example.studycalendar.DatabaseHelper;
import com.example.studycalendar.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        databaseHelper = new DatabaseHelper(this);
        StringBuilder resumen = new StringBuilder();

        ArrayList<ActividadDiariaDTO> allActivities = databaseHelper.getActivitiesByDate(null);

        for (ActividadDiariaDTO actividadDiariaDTO :
                allActivities) {
            resumen.append(actividadDiariaDTO.getIdEstudio() +" - "+actividadDiariaDTO.getTitulo() +" - " + actividadDiariaDTO.getDescription() + " - " + actividadDiariaDTO.getFechaEstudio() + " \n");
        }

        TextView detalles = (TextView) findViewById(R.id.tvDetalleActividades);
        detalles.setText(resumen.toString());


        Button btnBack = (Button) findViewById(R.id.filterButtonMainWorkorderSelection);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","probando el click del boton");

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
