package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.Constants.Constants;
import com.example.dto.ActividadDiariaDTO;
import com.example.studycalendar.DatabaseHelper;
import com.example.studycalendar.R;

import java.util.ArrayList;
import java.util.List;

public class ActividadesDiariasAdapter extends BaseAdapter {

    List<ActividadDiariaDTO> actividadDiariaDTO = new ArrayList<>();
    Context context;
    DatabaseHelper databaseHelper;

    public ActividadesDiariasAdapter(List<ActividadDiariaDTO> actividadDiariaDTO, Context context){
        this.actividadDiariaDTO = actividadDiariaDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.actividadDiariaDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return this.actividadDiariaDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO esta pendiente de terminar esto
        //this.actividadDiariaDTO.get(position).getId();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActividadDiariaDTO studyTable =  actividadDiariaDTO.get(position);

        // TODO - Pendiente por investigar a detalle que hace el .inflate

        //Lo que aqui se esta haciendo es asignar el layout 'daily_item' a la vista 'convertView'
        convertView = LayoutInflater.from(context).inflate(R.layout.daily_item,null);

        TextView tvContenido = convertView.findViewById(R.id.tvContenido);
        tvContenido.setText(studyTable.getTitulo() +  " \n " + studyTable.getDescription());

        // aqui obtenemos del convertView el checkbox y lo marcamos o desmarcamos segun los datos obtenidos
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxTerminado);
        checkBox.setChecked(studyTable.getTerminado() == Constants.ID_CATALOGO_SINO_SI);
        checkBox.setId((int)(long)studyTable.getIdEstudio());

        // Aqui steamos un onChangeListener al checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("ETIQUETA", "valor" + buttonView.getId());
                actualizarEstadoActividad(buttonView.getId(),isChecked);
            }
        });

        return convertView;
    }


    public void actualizarEstadoActividad(Integer idActividad,boolean terminado){
        databaseHelper = new DatabaseHelper(context);
        Integer estado = terminado?Constants.ID_CATALOGO_SINO_SI:Constants.ID_CATALOGO_SINO_NO;
        databaseHelper.actualizarEstadoActividadDia(estado, idActividad);
    }
}
