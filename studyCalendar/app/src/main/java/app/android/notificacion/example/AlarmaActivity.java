package app.android.notificacion.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studycalendar.R;
import com.example.util.Utils;

import java.util.Calendar;

public class AlarmaActivity extends AppCompatActivity {

    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        motrarHoraConfigurada();
        configurarOnClickBotonSeleccionarHora();
    }

    private void configurarOnClickBotonSeleccionarHora() {

        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(AlarmaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        // pendiente revisar esta seccion de codigo, aqui hace algo interesante R.string.changed_to
                        Toast.makeText(AlarmaActivity.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        Utils.setAlarm(alarmID, today.getTimeInMillis(), AlarmaActivity.this);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });
    }


    public void motrarHoraConfigurada(){

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;
        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);

        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }

    }
}
