package ch.zli.ds.securenotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.model.ReminderModel;

public class ReminderAdapter extends ArrayAdapter<ReminderModel> {
    public ReminderAdapter(Context context, List<ReminderModel> reminders){
        super(context,0,reminders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ReminderModel reminder = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.name)).setText(reminder.getName());
        return convertView;
    }

}
