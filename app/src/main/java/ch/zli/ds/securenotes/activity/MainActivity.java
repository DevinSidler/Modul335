package ch.zli.ds.securenotes.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.model.NoteModel;
import ch.zli.ds.securenotes.model.ReminderModel;

public class MainActivity extends AppCompatActivity {

    static final String key_notes = "Notes";
    static final String key_reminders = "reminders";

    List<NoteModel> notes = new LinkedList<>();
    List<ReminderModel> reminders = new LinkedList<>();

    Button createNoteButton;
    Button createReminderButton;
    ListView noteView;
    ListView reminderView;

    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentication();

        createNoteButton = findViewById(R.id.createNote);
        createReminderButton = findViewById(R.id.createReminder);
        noteView = findViewById(R.id.noteView);
        reminderView = findViewById(R.id.reminderView);

        createNoteButton.setOnClickListener(v -> newNote());
        createReminderButton.setOnClickListener(v -> newReminder());
    }

    @Override
    protected void onStart() {
        super.onStart();
        showNotes();
        showReminders();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        authentication();
    }

    protected void newNote() {
        Intent noteIntent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(noteIntent);
    }

    protected void newReminder() {
        Intent reminderIntent = new Intent(MainActivity.this, ReminderActivity.class);
        startActivity(reminderIntent);
    }

    protected void showNotes() {
        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString(key_notes, gson.toJson(new ArrayList<NoteModel>()));
        notes = gson.fromJson(json, new TypeToken<List<NoteModel>>() {
        }.getType());

        ArrayAdapter<NoteModel> adapter = new ArrayAdapter<>(this, R.layout.activity_main, notes);
        noteView.setAdapter(adapter);

    }

    protected void showReminders() {
        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString(key_reminders, gson.toJson(new ArrayList<NoteModel>()));
        reminders = gson.fromJson(json, new TypeToken<List<NoteModel>>() {
        }.getType());

        ArrayAdapter<ReminderModel> adapter = new ArrayAdapter<>(this, R.layout.activity_main, reminders);
        reminderView.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected void authentication() {
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser("Authentication Error : " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Succeed");
            }
        };

        checkBiometricSupport();
        BiometricPrompt biometricPrompt = new BiometricPrompt
                .Builder(getApplicationContext())
                .setTitle("Title of Prompt")
                .setSubtitle("Subtitle")
                .setDescription("Uses FP")
                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void
                    onClick(DialogInterface dialogInterface, int i) {
                        notifyUser("Authentication Cancelled");
                    }
                }).build();

        biometricPrompt.authenticate(
                getCancellationSignal(),
                getMainExecutor(),
                authenticationCallback);

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private Boolean checkBiometricSupport() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings");
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        } else
            return true;
    }

    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private CancellationSignal getCancellationSignal() {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        notifyUser("Authentication was Cancelled by the user");
                    }
                });
        return cancellationSignal;
    }
}