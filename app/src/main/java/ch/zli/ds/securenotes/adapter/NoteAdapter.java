package ch.zli.ds.securenotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.model.NoteModel;
import ch.zli.ds.securenotes.model.ReminderModel;

public class NoteAdapter extends ArrayAdapter<NoteModel> {
    public NoteAdapter(Context context, List<NoteModel> notes){
        super(context,0,notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NoteModel note = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.name)).setText(note.getName());
        return convertView;
    }

}
