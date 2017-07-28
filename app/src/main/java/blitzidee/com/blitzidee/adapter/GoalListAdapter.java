package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.mapeadores.MapperGoal;
import blitzidee.com.blitzidee.mapeadores.MapperIdea;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 21/07/2017.
 */

public class GoalListAdapter extends ArrayAdapter{

    private Context context;
    private ArrayList<Goal> listGoals;
    private TextView textViewGoalDescription;

    public GoalListAdapter(Context context, ArrayList<Goal> objects) {
        super(context, 0, objects);
        this.context = context;
        this.listGoals = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (listGoals != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.goal_view_list, parent, false);

            setCheckbox(view, position);
            setDescription(view, position);
            setDeleteButton(view, position);

        }
        return view;
    }



    private void setCheckbox(View view, final int position) {
        final View v = view;

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxGoal);
        if (listGoals.get(position).isComplete())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listGoals.get(position).setConclusion(isChecked);

                MapperGoal mapperGoal = new MapperGoal(context);
                mapperGoal.updateGoal(listGoals.get(position));
                mapperGoal.close();

                updateIdeaConclusion(ideaIsComplete(), listGoals.get(position));

                notifyDataSetChanged();

            }
        });
    }

    private boolean ideaIsComplete() {
        for (Goal goal : listGoals) {
            if (!goal.isComplete())
                return false;
        }

        return true;
    }

    private void updateIdeaConclusion(boolean isComplete, Goal goal) {
        MapperIdea mapperIdea = new MapperIdea(context);
        Idea idea = mapperIdea.get(goal.getIdeaId());
        idea.setConclusion(isComplete);
        mapperIdea.updateIdea(idea.getTitle(), idea);
        mapperIdea.close();
    }

    private void setDescription(View view, int position) {

        textViewGoalDescription = (TextView) view.findViewById(R.id.textViewGoalDescription);
        textViewGoalDescription.setText(listGoals.get(position).getDescription());

        if (listGoals.get(position).isComplete())
            textViewGoalDescription.setPaintFlags(textViewGoalDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setDeleteButton(View view, final int position) {

        ImageView imageViewDeleteButton = (ImageView) view.findViewById(R.id.imageViewDeleteButton);
        imageViewDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogRemoveGoal(listGoals.get(position));
            }
        });
    }

    private void createAlertDialogRemoveGoal(final Goal goal) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Você realmente deseja apagar este objetivo?");

        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeAction(goal);
                Toast.makeText(context, "Removido!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void removeAction(Goal goal) {
        listGoals.remove(listGoals.indexOf(goal));
        notifyDataSetChanged();

        deleteGoalFromDatabase(goal);
    }

    private void deleteGoalFromDatabase(Goal goal) {
        MapperGoal mapperGoal = new MapperGoal(context);
        mapperGoal.remove(goal);
        mapperGoal.close();
    }

    /*****
     * Paint.ANTI_ALIAS_FLAG
     * parâmetro para remover as flags do textView
     */
}
