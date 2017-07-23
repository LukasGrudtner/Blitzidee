package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

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

            if (listIdeas.get(position).isComplete())
                view.setBackgroundColor(view.getResources().getColor(R.color.colorBackgroundCompleted));

            // Insere Título
            setTitle(view, position);

            // Insere Data de Criação
            setCreationDate(view, position);

            // Insere Posição
            setStringPosition(view, position);

            // Insere Imagem
            setImageIcon(view, position);
        }
        return view;
    }

    private void setTitle(View view, int position) {
        TextView textViewIdeaTitle = (TextView) view.findViewById(R.id.textViewIdeaTitle);
        textViewIdeaTitle.setText(listIdeas.get(position).getTitle());

        if (listIdeas.get(position).isComplete()) {
            textViewIdeaTitle.setTextColor(view.getResources().getColor(R.color.color_subtitles_dark));
            textViewIdeaTitle.setPaintFlags(textViewIdeaTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void setCreationDate(View view, int position) {
        TextView textViewIdeaDate = (TextView) view.findViewById(R.id.textViewIdeaDate);

        String date;
        GregorianCalendar gregorianCalendar = listIdeas.get(position).getCreationDate();

        int day = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH);
        if (day < 10) date = "0" + day; else date = String.valueOf(day);

        int month = gregorianCalendar.get(gregorianCalendar.MONTH);
        if (month < 10) date += "/0" + month; else date += "/" + month;

        int year = gregorianCalendar.get(gregorianCalendar.YEAR);
        date += "/" + year;

        textViewIdeaDate.setText(date);
    }

    private void setStringPosition(View view, int position) {
        TextView textViewPosition = (TextView) view.findViewById(R.id.textViewPosition);

        textViewPosition.setText(String.valueOf(position+1));
    }

    private void setImageIcon(View view, int position) {
        ImageView imageViewLamp = (ImageView) view.findViewById(R.id.imageViewLamp);

        if (listIdeas.get(position).isComplete())
            imageViewLamp.setImageResource(R.drawable.ic_action_foursquare);
        else
            imageViewLamp.setImageResource(R.drawable.ic_action_bulb);
    }
}
