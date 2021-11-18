package ch.zli.ds.securenotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.broadcast.Receiver;
import ch.zli.ds.securenotes.model.NoteModel;
import ch.zli.ds.securenotes.model.ReminderModel;
import ch.zli.ds.securenotes.service.NotificationService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    static final String key_reminders = "reminders";

    EditText reminderDescription;
    EditText reminderDate;
    EditText reminderTime;
    Button saveReminderButton;
    Button deleteReminderButton;

    List<ReminderModel> reminders = new LinkedList<>();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        context = getApplicationContext();

        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString(key_reminders, gson.toJson(new ArrayList<ReminderModel>()) );
        reminders = gson.fromJson(json, new TypeToken<List<ReminderModel>>(){}.getType());

        reminderDescription = findViewById(R.id.description);
        reminderDate = findViewById(R.id.reminderDate);
        reminderTime = findViewById(R.id.reminderTime);
        saveReminderButton = findViewById(R.id.saveReminder);
        deleteReminderButton = findViewById(R.id.deleteReminder);

        saveReminderButton.setOnClickListener(v -> {
            try {
                saveReminder();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        deleteReminderButton.setOnClickListener(v -> deleteReminder());

    }

    protected void saveReminder() throws ParseException {
        String dateTimeString = reminderDate.getText().toString().concat(" ").concat(reminderTime.getText().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = formatter.parse(dateTimeString);

        ReminderModel reminder = new ReminderModel(reminderDescription.getText().toString(),date);

        reminders.add(reminder);

        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(reminders);
        prefsEditor.putString(key_reminders, json);
        prefsEditor.commit();

        Intent notifyIntent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long millis = date.getTime();
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    protected void deleteReminder(){

    }

}