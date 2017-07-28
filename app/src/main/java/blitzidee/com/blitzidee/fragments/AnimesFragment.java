package blitzidee.com.blitzidee.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.adapter.AnimeListAdapter;
import blitzidee.com.blitzidee.mapeadores.MapperAnime;
import blitzidee.com.blitzidee.model.Anime;
import blitzidee.com.blitzidee.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimesFragment extends Fragment {

    private FloatingActionButton fabAdd;
    private ListView listViewAnimes;
    private ArrayList<Anime> animeList;
    private AnimeListAdapter animeListAdapter;

    public AnimesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animes, container, false);

        setFloatingActionButton(view);
        animeList = loadAnimesFromDatabase();

        listViewAnimes = (ListView) view.findViewById(R.id.list_view_animes);
        animeListAdapter = new AnimeListAdapter(getActivity(), animeList);
        listViewAnimes.setAdapter(animeListAdapter);
        listViewAnimes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createAlertDialogRemoveAnime(position);
                return true;
            }
        });
        animeListAdapter.notifyDataSetChanged();

        return view;
    }

    private void createAlertDialogRemoveAnime(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Você realmente deseja remover o anime '" + animeList.get(position).getTitle() + "'?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAnimeFromDatabase(animeList.get(position));
                animeList.remove(getPositionOfAnimeFromList(animeList.get(position)));
                animeListAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "Removido!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deleteAnimeFromDatabase(Anime anime) {
        MapperAnime mapperAnime = new MapperAnime(getActivity());
        mapperAnime.remove(anime);
        mapperAnime.close();
    }

    private int getPositionOfAnimeFromList(Anime anime) {
        int position = 0;
        for (Anime aux : animeList) {
            if (anime.equals(aux))
                return position;
            else
                position++;
        }
        return position;
    }

    private void setFloatingActionButton(View view) {
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_add_book);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogAddAnime();
            }
        });
    }

    private void createAlertDialogAddAnime() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_anime_dialog, null, false);

        final EditText editTextTitle = (EditText) view.findViewById(R.id.editTextDialogAnimeTitle);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                Anime anime = new Anime();

                anime.setDate(gregorianCalendar);
                anime.setTitle(editTextTitle.getText().toString());

                animeList.add(anime);
                animeListAdapter.notifyDataSetChanged();
                saveAnimeOnDatabase(anime);
                scrollMyListViewToBottom();

                Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void saveAnimeOnDatabase(Anime anime) {
        MapperAnime mapperAnime = new MapperAnime(getActivity());
        mapperAnime.put(anime);
        mapperAnime.close();
    }

    private ArrayList<Anime> loadAnimesFromDatabase() {
        MapperAnime mapperAnime = new MapperAnime(getActivity());
        ArrayList<Anime> animeList = mapperAnime.getAll();
        mapperAnime.close();
        return animeList;
    }

    private void scrollMyListViewToBottom() {
        listViewAnimes.post(new Runnable() {
            @Override
            public void run() {
                listViewAnimes.setSelection(animeListAdapter.getCount()-1);
            }
        });
    }

}
