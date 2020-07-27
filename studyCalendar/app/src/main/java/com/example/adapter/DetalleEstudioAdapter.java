package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.TemaEstudio;
import com.example.studycalendar.R;

import java.util.List;

public class DetalleEstudioAdapter extends BaseAdapter {

    List<TemaEstudio> studyList;
    private Context context;

    public DetalleEstudioAdapter(Context context, List<TemaEstudio> studyTablelist){
        this.studyList = studyTablelist;
        this.context = context;
    }


    @Override
    public int getCount() {
        return studyList.size();
    }

    @Override
    public TemaEstudio getItem(int position) {
        return studyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (studyList.get(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TemaEstudio temaEstudio =  studyList.get(position);

        // TODO - Pendiente por investigar a detalle que hace el .inflate
        convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        TextView tvContenido = convertView.findViewById(R.id.tvContenido);
        tvContenido.setText(temaEstudio.getDescription());
        TextView tvTitulo = convertView.findViewById(R.id.tvTitulo);
        tvTitulo.setText(temaEstudio.getId() +" - "+temaEstudio.getTitulo());

        return convertView;
    }
}
