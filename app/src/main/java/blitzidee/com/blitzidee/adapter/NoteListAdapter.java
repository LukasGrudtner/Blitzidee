package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.model.Note;

/**
 * Created by lukas on 23/07/2017.
 */

public class NoteListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Note> noteList;

    public NoteListAdapter(Context context, ArrayList<Note> objects) {
        super(context, 0, objects);
        this.context = context;
        this.noteList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (noteList != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.note_view_list, parent, false);

            setDescription(view, position);

        }

        return view;
    }

    private void setDescription(View view, int position) {
        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewNoteDescription);
        textViewDescription.setText(noteList.get(position).getDescription());
    }
}
