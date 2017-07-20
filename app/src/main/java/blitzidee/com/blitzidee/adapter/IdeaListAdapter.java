package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 19/07/2017.
 */

public class IdeaListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Idea> listIdeas;

    public IdeaListAdapter(Context context, ArrayList<Idea> objects) {
        super(context, 0, objects);
        this.context = context;
        this.listIdeas = objects;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (listIdeas != null) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.idea_view_list, parent, false);

            TextView textViewIdeaTitle = (TextView) view.findViewById(R.id.textViewIdeaTitle);
            TextView textViewIdeaDate = (TextView) view.findViewById(R.id.textViewIdeaDate);
            TextView textViewPosition = (TextView) view.findViewById(R.id.textViewPosition);

            // Insere Título
            textViewIdeaTitle.setText(listIdeas.get(position).getTitle());

            // Insere Data de Criação
            String date;
            GregorianCalendar gregorianCalendar = listIdeas.get(position).getCreationDate();

            int day = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH);
            if (day < 10) date = "0" + day; else date = String.valueOf(day);

            int month = gregorianCalendar.get(gregorianCalendar.MONTH);
            if (month < 10) date += "/0" + month; else date += "/" + month;

            int year = gregorianCalendar.get(gregorianCalendar.YEAR);
            date += "/" + year;

            textViewIdeaDate.setText(date);

            // Insere Posição
            textViewPosition.setText(String.valueOf(position+1));

        }

        return view;
    }
}
