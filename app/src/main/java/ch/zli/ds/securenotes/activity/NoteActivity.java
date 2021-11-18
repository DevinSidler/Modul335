package ch.zli.ds.securenotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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
    List<NoteModel> notes = new LinkedList<>();

    protected EditText noteName;
    protected EditText noteContent;
    protected Button saveNoteButton;
    protected Button deleteNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString("Notes", gson.toJson(new ArrayList<NoteModel>()) );
        notes = gson.fromJson(json, new TypeToken<List<NoteModel>>(){}.getType());

        noteName = findViewById(R.id.noteName);
        noteContent = findViewById(R.id.noteContent);
        saveNoteButton = findViewById(R.id.saveNote);
        deleteNoteButton = findViewById(R.id.deleteNote);

        saveNoteButton.setOnClickListener(v -> saveNote());
        deleteNoteButton.setOnClickListener(v -> deleteNote());

    }

    protected void saveNote(){
        NoteModel note = new NoteModel(noteName.getText().toString(), noteContent.getText().toString());

        notes.add(note);

        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        prefsEditor.putString("Notes", json);
        prefsEditor.commit();

    }

    protected void deleteNote(){

    }

}