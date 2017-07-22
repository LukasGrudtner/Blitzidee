package blitzidee.com.blitzidee.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import blitzidee.com.blitzidee.activities.IdeaActivity;
import blitzidee.com.blitzidee.adapter.IdeaListAdapter;
import blitzidee.com.blitzidee.mapeadores.MapeadorIdea;
import blitzidee.com.blitzidee.model.Idea;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdeasFragment extends Fragment {

    private FloatingActionButton fabAdd;
    private ListView listViewIdeas;
    private ArrayList<Idea> listIdeas;
    private IdeaListAdapter ideaListAdapter;

    public IdeasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ideas, container, false);

        setFloatingActionButton(view);
        listIdeas = loadIdeasFromDatabase();

        listViewIdeas = (ListView) view.findViewById(R.id.list_view_ideas);

        listViewIdeas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIdeaActivity(position);
            }
        });

        ideaListAdapter = new IdeaListAdapter(getActivity(), listIdeas);
        listViewIdeas.setAdapter(ideaListAdapter);
        ideaListAdapter.notifyDataSetChanged();

        return view;
    }

    private void openIdeaActivity(int position) {
        Intent intent = new Intent(getActivity(), IdeaActivity.class);
        intent.putExtra("ideaTitle", listIdeas.get(position).getTitle());
        startActivity(intent);
    }

    private int getIdeaPositionFromArrayList(Idea idea) {
        int position = 0;
        for (Idea aux : listIdeas) {
            if (aux.equals(idea))
                return position;
            else
                position++;
        }

        return position;
    }

    private void setFloatingActionButton(View view) {
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_add_idea);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogAddIdea();
            }
        });
    }

    private void createAlertDialogAddIdea() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);

        /*****************************************************************************************
         * DUAS MANEIRAS DE IMPLEMENTAR UM ALERTDIALOG COM VÁRIAS ENTRADAS:
         *****************************************************************************************/
        /** CRIANDO UM LAYOUT EM TEMPO DE EXECUÇÃO
         * ****************************************************************************************
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextTitle = new EditText(getContext());
        editTextTitle.setHint("Título");
        layout.addView(editTextTitle);

        final EditText editTextDescription = new EditText(getContext());
        editTextDescription.setHint("Descrição");
        layout.addView(editTextDescription);

        alertDialog.setView(layout);
         *****************************************************************************************/

        /******************************************************************************************
         * UTILIZANDO UM LAYOUT PRÉ-DEFINIDO EM UM XML E INFLANDO ELE COM O INFLATER
         *****************************************************************************************/
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_idea_dialog, null, false);

        final EditText editTextTitle = (EditText) view.findViewById(R.id.editTextDialogTitle);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GregorianCalendar gregorianCalendarStart = new GregorianCalendar();
                Idea idea = new Idea();
                idea.setCreationDate(gregorianCalendarStart);
                idea.setTitle(editTextTitle.getText().toString());

                GregorianCalendar gregorianCalendarEnd = new GregorianCalendar();
                gregorianCalendarEnd.set(GregorianCalendar.DAY_OF_MONTH, 1);
                gregorianCalendarEnd.set(GregorianCalendar.MONTH, 1);
                gregorianCalendarEnd.set(GregorianCalendar.YEAR, 1970);
                idea.setEndDate(gregorianCalendarEnd);

                listIdeas.add(idea);
                ideaListAdapter.notifyDataSetChanged();
                saveIdeaOnDatabase(idea);
                scrollMyListViewToBottom();
                openIdeaActivity(getIdeaPositionFromArrayList(idea));

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

    private void saveIdeaOnDatabase(Idea idea) {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getContext());
        mapeadorIdea.put(idea);
        mapeadorIdea.close();
    }

    private ArrayList<Idea> loadIdeasFromDatabase() {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getContext());
        ArrayList<Idea> ideaList = mapeadorIdea.getAll();
        mapeadorIdea.close();
        return ideaList;
    }

    private void scrollMyListViewToBottom() {
        listViewIdeas.post(new Runnable() {
            @Override
            public void run() {
                listViewIdeas.setSelection(ideaListAdapter.getCount()-1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        listIdeas = loadIdeasFromDatabase();

        listViewIdeas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), IdeaActivity.class);
                intent.putExtra("ideaTitle", listIdeas.get(position).getTitle());
                startActivity(intent);
            }
        });

        ideaListAdapter = new IdeaListAdapter(getActivity(), listIdeas);
        listViewIdeas.setAdapter(ideaListAdapter);
        ideaListAdapter.notifyDataSetChanged();
    }
}
