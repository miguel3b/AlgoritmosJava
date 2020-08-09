package com.example.activity;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.Constants.Constants;
import com.example.entity.EstudioDetalle;
import com.example.entity.TemaEstudio;
import com.example.service.NotificationService;
import com.example.studycalendar.DatabaseHelper;
import com.example.adapter.DetalleEstudioAdapter;
import com.example.studycalendar.R;
import com.example.studycalendar.StudyDialogFragment;
import com.example.util.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNELID1";
    private DatabaseHelper mDatabaseHelper;
    private  ListView listView;
    private Integer alarmID = 2;
    private DetalleEstudioAdapter detalleEstudioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseHelper = new DatabaseHelper(this);

        mDatabaseHelper.backUp();

        Long actividadesTerminadas = mDatabaseHelper.getCountByEstado(Constants.ID_CATALOGO_SINO_SI);
        Long actividadesIncompletas = mDatabaseHelper.getCountByEstado(Constants.ID_CATALOGO_SINO_NO);

        TextView tvTerminadas = (TextView) findViewById(R.id.textView6);
        TextView tvIncompletas = (TextView) findViewById(R.id.textView7);

        tvTerminadas.setText(actividadesTerminadas.toString());
        tvIncompletas.setText(actividadesIncompletas.toString());

        TextView viewById = (TextView) findViewById(R.id.tvDailyActivities);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RepasosSinRealizar.class);
                startActivity(intent);
            }
        });

        TextView viewIncompletas = (TextView) findViewById(R.id.tvActividadesIncompletas);

        viewIncompletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyStudy.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.lvItems);
        detalleEstudioAdapter = new DetalleEstudioAdapter(this, getData());
        listView.setAdapter(detalleEstudioAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("activityDetail", detalleEstudioAdapter.getItem(position));
                startActivity(intent);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        // crearNotificacionPrueba();


        if (isMyServiceRunning(NotificationService.class)) {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH,1);
            cal.set(Calendar.HOUR_OF_DAY, 8);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Log.d(":)", "la hora configurada es:" + cal.getTime());

            Toast.makeText(this, "SE REINICIO EL SERVICIO", Toast.LENGTH_SHORT).show();
            Utils.setAlarm(alarmID, cal.getTimeInMillis(), MainActivity.this);

        } else {
            Toast.makeText(this, "El servicio estaba en ejecucion", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            verificarTemasSinProximosRepasos();

        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CharSequence name = getString(R.string.channel_name);
            // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "elNombre", importance);
            channel.setDescription("laDescripcion");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

       // notificationManager.notify(notificationId, builder.build());
    }



    public void openDialog(){

        StudyDialogFragment studyDialogFragment = new StudyDialogFragment();
        studyDialogFragment.show(getSupportFragmentManager(),"example dialog");

    }

    public List<TemaEstudio> getData(){

        mDatabaseHelper = new DatabaseHelper(this);
        List<TemaEstudio> listTemaEstudio = mDatabaseHelper.getListContents();
        return listTemaEstudio;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void crearNotificacionPrueba(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("El titulo de la notificacion")
                .setContentText("El contenido de la notificacion")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CharSequence name = getString(R.string.channel_name);
            // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "elNombre", importance);
            channel.setDescription("laDescripcion");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, builder.build());


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void verificarTemasSinProximosRepasos(){


        ArrayList<EstudioDetalle> estudioDetalles = mDatabaseHelper.verificarTemasSinProximosRepasos();
        estudioDetalles.stream().
                forEach(x -> Log.d("TAXG",String.valueOf(x.getIdTemaEstudio())+" - "+ x.getFechaEstudio() +" - "+ x.getTerminado() +" - "+ x.getNumeroRepaso()) );


       if(estudioDetalles.size() > 0){
            Utils.generarProximosRepasos(estudioDetalles,this);
           Toast.makeText(this, "SE ACTUALIZARON " +estudioDetalles.size()+ " TEMAS", Toast.LENGTH_SHORT).show();
       }else if(estudioDetalles.size() == 0){

           Toast.makeText(this, "TODOS LOS TEMAS ESTABAN ACTUALIZADOS", Toast.LENGTH_SHORT).show();

       }
    }


/**  TODO - MANERA SENCILLA DE LLENAR UN LIST VIEW
    public void getData(ListView listView){

        mDatabaseHelper = new DatabaseHelper(this);
        ArrayList<String> arrayView = new ArrayList<String>();

        Cursor listContents = mDatabaseHelper.getListContents();

        if(listContents.getCount() > 0){

            while(listContents.moveToNext()){
                arrayView.add(listContents.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayView);
                listView.setAdapter(listAdapter);
            }

        }
    }

 **/


        public void generarRespaldoRegistros(){

            //mDatabaseHelper.

        }
}
