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
import blitzidee.com.blitzidee.model.Anime;

/**
 * Created by lukas on 27/07/2017.
 */

public class AnimeListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Anime> animeList;

    public AnimeListAdapter(Context context, ArrayList<Anime> objects) {
        super(context, 0, objects);
        this.context = context;
        this.animeList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (animeList != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.anime_view_list, parent, false);

            setTitle(view, position);
            setDate(view, position);
            setStringPosition(view, position);

        }

        return view;
    }

    private void setTitle(View view, int position) {
        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewAnimeTitle);
        textViewTitle.setText(animeList.get(position).getTitle());
    }

    private void setDate(View view, int position) {
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewAnimeDate);

        String date;
        GregorianCalendar gregorianCalendar = animeList.get(position).getDate();

        int day = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH);
        if (day < 10) date = "0" + day; else date = String.valueOf(day);

        int month = gregorianCalendar.get(gregorianCalendar.MONTH);
        if (month < 10) date += "/0" + month; else date += "/" + month;

        int year = gregorianCalendar.get(gregorianCalendar.YEAR);
        date += "/" + year;

        textViewDate.setText(date);
    }

    private void setStringPosition(View view, int position) {
        TextView textViewPosition = (TextView) view.findViewById(R.id.textViewAnimePosition);
        textViewPosition.setText(String.valueOf(position+1));
    }
}
