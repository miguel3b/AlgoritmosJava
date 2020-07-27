package com.example.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.Constants.Constants;
import com.example.entity.EstudioDetalle;
import com.example.entity.TemaEstudio;
import com.example.studycalendar.DatabaseHelper;
import com.example.studycalendar.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    Button btnAdd;
    private CalendarView calendarView;
    private EditText title, desripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create);
        btnAdd = (Button) findViewById(R.id.button);
        desripcion = (EditText) findViewById(R.id.informacionText);
        title = (EditText) findViewById(R.id.tituloText);
        calendarView = (CalendarView) findViewById(R.id.calendarSelect);
        mDatabaseHelper = new DatabaseHelper(this);

        Button btnBack = (Button) findViewById(R.id.filterButtonMainWorkorderSelection);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","probando el click del boton");

                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = desripcion.getText().toString();

                if (descripcion.trim().equals("")) {
                    toastMessage("Es necesario agreagar una descripcion");
                    return;
                }

                String titulo = title.getText().toString();

                if (titulo.trim().equals("")) {
                    toastMessage("Es necesario agregar un titulo");
                    return;
                }


                TemaEstudio temaEstudio = new TemaEstudio();
                temaEstudio.setDescription(descripcion);
                temaEstudio.setTitulo(titulo);

                long idEstudio = mDatabaseHelper.addData(temaEstudio);

                if (idEstudio != -1) {

                    Calendar cal = Calendar.getInstance();
                    //cal.setTime(new Date(calendarView.getDate()));
                    registrarRepaso(idEstudio, 1, Constants.ID_CATALOGO_SINO_SI, cal.getTime());
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    registrarRepaso(idEstudio, 2, Constants.ID_CATALOGO_SINO_NO, cal.getTime());
                } else {
                    Toast.makeText(getApplicationContext(), "OCURRIO UN ERROR AL REGISTRAR LA OPERACION", Toast.LENGTH_SHORT).show();
                }

                desripcion.setText("");
                title.setText("");
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void registrarRepaso(long idEstudio, Integer numeroRepaso, Integer terminado, Date fechaEstudio) {

        EstudioDetalle estudioDetalle = new EstudioDetalle();
        estudioDetalle.setIdTemaEstudio(idEstudio);
        estudioDetalle.setFechaEstudio(fechaEstudio);
        estudioDetalle.setNumeroRepaso(numeroRepaso);
        estudioDetalle.setTerminado(terminado);

        mDatabaseHelper.registrarNuevoEstudio(estudioDetalle);
    }

    private void toastMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}