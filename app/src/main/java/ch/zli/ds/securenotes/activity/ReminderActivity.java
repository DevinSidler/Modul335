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
import android.preference.PreferenceManager;
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

        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
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

        Intent receivedIntent = getIntent();
        if(receivedIntent.getExtras() != null){
            editReminder();
        }

    }

    protected void editReminder(){
        ReminderModel reminder = (ReminderModel) getIntent().getSerializableExtra("reminder");
        reminderDescription.setText(reminder.getName());

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        String dateOnly = dateFormat.format(reminder.getDateTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat(("HH:mm:ss"));
        String timeOnly = timeFormat.format(reminder.getDateTime());

        reminderDate.setText(dateOnly);
        reminderTime.setText(timeOnly);

        reminders.remove(getIntent().getIntExtra("position", -1));
    }

    protected void saveReminder() throws ParseException {

        if (!reminderTime.getText().toString().isEmpty() | !reminderDate.getText().toString().isEmpty() | !reminderDescription.getText().toString().isEmpty()) {

            String dateTimeString = reminderDate.getText().toString().concat(" ").concat(reminderTime.getText().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = formatter.parse(dateTimeString);

            ReminderModel reminder = new ReminderModel(reminderDescription.getText().toString(), date);

            reminders.add(reminder);

            SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = mPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(reminders);
            prefsEditor.putString(key_reminders, json);
            prefsEditor.commit();

            Intent notifyIntent = new Intent(this, Receiver.class);
            notifyIntent.putExtra("description", reminder.getName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            long millis = date.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            if (reminderTime.getText().toString().isEmpty()){
                reminderTime.setText("Enter a Valid Time!");
            }
            if (reminderDate.getText().toString().isEmpty()){
                reminderDate.setText("Enter a Valid Date!");
            }
            if (reminderDescription.getText().toString().isEmpty()){
                reminderDescription.setText("Enter a Description!");
            }
        }

    }

    protected void deleteReminder(){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(reminders);
        prefsEditor.putString(key_reminders, json);
        prefsEditor.commit();

       Intent intent = new Intent(this, MainActivity.class);
       startActivity(intent);
    }

}