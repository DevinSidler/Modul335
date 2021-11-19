package ch.zli.ds.securenotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.model.NoteModel;

public class NoteActivity extends AppCompatActivity {

    static final String key_notes = "note";

    List<NoteModel> notes = new LinkedList<>();

    protected EditText noteName;
    protected EditText noteContent;
    protected Button saveNoteButton;
    protected Button deleteNoteButton;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        context = getApplicationContext();

        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = mPref.getString(key_notes, gson.toJson(new ArrayList<NoteModel>()) );
        notes = gson.fromJson(json, new TypeToken<List<NoteModel>>(){}.getType());

        noteName = findViewById(R.id.noteName);
        noteContent = findViewById(R.id.noteContent);
        saveNoteButton = findViewById(R.id.saveNote);
        deleteNoteButton = findViewById(R.id.deleteNote);

        saveNoteButton.setOnClickListener(v -> saveNote());
        deleteNoteButton.setOnClickListener(v -> deleteNote());

        Intent receivedIntent = getIntent();
        if(receivedIntent.getExtras() != null){
            editNote();
        }

    }

    protected void editNote(){
        NoteModel note = (NoteModel) getIntent().getSerializableExtra("note");
        noteName.setText(note.getName());
        noteContent.setText(note.getNote());

        notes.remove(getIntent().getIntExtra("position", -1));
    }

    protected void saveNote(){

        if (!noteName.getText().toString().isEmpty()) {
            NoteModel note = new NoteModel(noteName.getText().toString(), noteContent.getText().toString());

            notes.add(note);

            SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
            ;
            SharedPreferences.Editor prefsEditor = mPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(notes);
            prefsEditor.putString(key_notes, json);
            prefsEditor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            noteName.setText("Please enter a Name!");
        }
    }

    protected void deleteNote(){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);;
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        prefsEditor.putString(key_notes, json);
        prefsEditor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}