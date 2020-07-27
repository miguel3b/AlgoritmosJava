package com.example.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.Constants.Constants;
import com.example.entity.EstudioDetalle;
import com.example.studycalendar.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

import app.android.notificacion.example.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

public class Utils {


    public static void setAlarm(int i, Long timestamp, Context ctx) {

        //TODO crearAlarma1 registramos la alarma en el sistema
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        // se agregan estas lineas para que la alarma se repita cada 24 horas
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timestamp, 24*60*60*1000, pendingIntent);

    }


    public static void generarProximosRepasos(ArrayList<EstudioDetalle> dayActivities, Context context){

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);

        for (EstudioDetalle repasoActual :
                dayActivities) {

            Calendar cal = Calendar.getInstance();
            EstudioDetalle estudioDetalle = new EstudioDetalle();
            cal.setTime(repasoActual.getFechaEstudio());

            if (repasoActual.getNumeroRepaso() == 2) {
                cal.add(Calendar.DAY_OF_MONTH, 7);
            } else if (repasoActual.getNumeroRepaso() >= 3) {
                cal.add(Calendar.DAY_OF_MONTH, 30);
            }

            estudioDetalle.setTerminado(Constants.ID_CATALOGO_SINO_NO);
            estudioDetalle.setFechaEstudio(cal.getTime());
            estudioDetalle.setNumeroRepaso(repasoActual.getNumeroRepaso() +1);
            estudioDetalle.setIdTemaEstudio(repasoActual.getIdTemaEstudio());
            mDatabaseHelper.registrarNuevoEstudio(estudioDetalle);
        }
    }
}