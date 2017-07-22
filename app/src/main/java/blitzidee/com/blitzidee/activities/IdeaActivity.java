package blitzidee.com.blitzidee.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.adapter.GoalListAdapter;
import blitzidee.com.blitzidee.mapeadores.MapeadorGoal;
import blitzidee.com.blitzidee.mapeadores.MapeadorIdea;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

public class IdeaActivity extends AppCompatActivity {

    private Idea idea;
    private TextView textViewTitle;
    private TextView textViewCreationDate;
    private TextView textViewEndDate;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    private ListView listViewGoals;
    private ArrayList<Goal> listGoals;
    private GoalListAdapter goalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        String ideaTitle = getIntent().getExtras().getString("ideaTitle");
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        idea = mapeadorIdea.get(ideaTitle);
        mapeadorIdea.close();

        createToolbar();

        textViewTitle = (TextView) findViewById(R.id.textViewIdeaTitle);
        textViewTitle.setText(idea.getTitle());

        setFloatingActionButton();

        /* Implementação dos Goals */
        listGoals = idea.getGoalArrayList();
        listViewGoals = (ListView) findViewById(R.id.list_view_goals);
        goalListAdapter = new GoalListAdapter(getApplicationContext(), listGoals);
        listViewGoals.setAdapter(goalListAdapter);
        goalListAdapter.notifyDataSetChanged();
    }

    private void setFloatingActionButton() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_goal);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogAddGoal();
            }
        });
    }

    private void createAlertDialogAddGoal() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_idea_dialog, null, false);

        final EditText editTextDescription = (EditText) view.findViewById(R.id.editTextDialogTitle);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Goal goal = new Goal();
                goal.setIdeaId(idea.getId());
                goal.setDescription(editTextDescription.getText().toString());
                idea.getGoalArrayList().add(goal);
                goalListAdapter.notifyDataSetChanged();
                saveGoalOnDatabase(goal);
                scrollMyListViewToBottom();

                Toast.makeText(getApplicationContext(), "Adicionado!", Toast.LENGTH_SHORT).show();
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

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_idea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_delete:
                doActionDelete();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doActionEdit() {
        Intent intent = new Intent(this, EditIdeaActivity.class);
        intent.putExtra("ideaTitle", idea.getTitle());
        startActivity(intent);
    }

    private void doActionDelete() {
        deleteIdeaFromDatabase(idea);
        Toast.makeText(getApplicationContext(), "Removida!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteIdeaFromDatabase(Idea idea) {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        mapeadorIdea.remove(idea);
        mapeadorIdea.close();
    }

    private void saveIdeaOnDatabase() {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        mapeadorIdea.put(idea);
        mapeadorIdea.close();
    }

    private void setCreationDateInTextView(GregorianCalendar creationDate) {
        String date;

        int day = creationDate.get(GregorianCalendar.DAY_OF_MONTH);
        if (day < 10) date = "0" + day; else date = String.valueOf(day);

        int month = creationDate.get(GregorianCalendar.MONTH);
        if (month < 10) date += "/0" + month; else date += "/" + month;

        int year = creationDate.get(GregorianCalendar.YEAR);
        date += "/" + year;

        textViewCreationDate.setText(date);
    }

    private void setEndDateInTextView(GregorianCalendar endDate) {
        String date;

        int dayEnd = endDate.get(GregorianCalendar.DAY_OF_MONTH);
        if (dayEnd < 10) date = "0" + dayEnd; else date = String.valueOf(dayEnd);

        int monthEnd = endDate.get(GregorianCalendar.MONTH);
        if (monthEnd < 10) date += "/0" + monthEnd; else date += "/" + monthEnd;

        int yearEnd = endDate.get(GregorianCalendar.YEAR);
        date += "/" + yearEnd;

        /* Verifica se a data de término é válida */
        if (!endDateIsValid(endDate))
            date = "";

        textViewEndDate.setText(date);
    }

    private boolean endDateIsValid(GregorianCalendar gregorianCalendar) {
        GregorianCalendar gcAux = new GregorianCalendar();
        gcAux.set(GregorianCalendar.YEAR, 1970);

        if (gregorianCalendar.get(GregorianCalendar.YEAR) == gcAux.get(GregorianCalendar.YEAR))
            return false;
        else
            return true;

    }

    private void scrollMyListViewToBottom() {
        listViewGoals.post(new Runnable() {
            @Override
            public void run() {
                listViewGoals.setSelection(goalListAdapter.getCount()-1);
            }
        });
    }

    private void saveGoalOnDatabase(Goal goal) {
        MapeadorGoal mapeadorGoal = new MapeadorGoal(getApplicationContext());
        mapeadorGoal.put(goal);
        mapeadorGoal.close();
    }

    private ArrayList<Goal> loadGoalsFromDatabase() {
        MapeadorGoal mapeadorGoal = new MapeadorGoal(getApplicationContext());
        ArrayList<Goal> goalList = mapeadorGoal.getAll(idea);
        mapeadorGoal.close();
        return goalList;
    }
}
