package ch.zli.ds.securenotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.model.NoteModel;

public class MainActivity extends AppCompatActivity {

    List<NoteModel> notes = new LinkedList<>();

    Button createNoteButton;
    Button createReminderButton;
    ListView noteView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNoteButton = findViewById(R.id.createNote);
        createReminderButton = findViewById(R.id.createReminder);
        noteView = findViewById(R.id.noteView);

        createNoteButton.setOnClickListener(v -> newNote());
        createReminderButton.setOnClickListener(v -> newReminder());
    }

    @Override
    protected void onStart(){
        super.onStart();
        showNotes();
        showReminders();

    }

    @Override
    protected void onResume(){
        super.onResume();
        showNotes();
    }

    protected void newNote(){
        Intent noteIntent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(noteIntent);
    }

    protected void newReminder(){
        Intent reminderIntent = new Intent(MainActivity.this, ReminderActivity.class);
        startActivity(reminderIntent);
    }

    protected void showNotes(){
        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString("Notes", gson.toJson(new ArrayList<NoteModel>()) );
        notes = gson.fromJson(json, new TypeToken<List<NoteModel>>(){}.getType());

        ArrayAdapter<NoteModel> adapter = new ArrayAdapter<>(this, R.layout.activity_main, notes);
        noteView.setAdapter(adapter);

    }

    protected void showReminders(){

    }
}