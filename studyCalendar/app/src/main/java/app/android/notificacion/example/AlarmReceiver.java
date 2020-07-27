package app.android.notificacion.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.service.NotificationService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO crearAlarma2 creamos el alarm receiver
        Intent service1 = new Intent(context, NotificationService.class);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("WALKIRIA", " ALARM RECEIVED!!!");
    }
}