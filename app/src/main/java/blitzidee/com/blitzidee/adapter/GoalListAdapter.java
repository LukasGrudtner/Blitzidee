package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;
import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.mapeadores.MapeadorGoal;
import blitzidee.com.blitzidee.mapeadores.MapeadorIdea;
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

                MapeadorGoal mapeadorGoal = new MapeadorGoal(context);
                mapeadorGoal.updateGoal(listGoals.get(position));
                mapeadorGoal.close();

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
        MapeadorIdea mapeadorIdea = new MapeadorIdea(context);
        Idea idea = mapeadorIdea.get(goal.getIdeaId());
        idea.setConclusion(isComplete);
        mapeadorIdea.updateIdea(idea.getTitle(), idea);
        mapeadorIdea.close();
    }

    private void setDescription(View view, int position) {

        textViewGoalDescription = (TextView) view.findViewById(R.id.textViewGoalDescription);
        textViewGoalDescription.setText(listGoals.get(position).getDescription());

        if (listGoals.get(position).isComplete())
            textViewGoalDescription.setPaintFlags(textViewGoalDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    /*****
     * Paint.ANTI_ALIAS_FLAG
     * parâmetro para remover as flags do textView
     */
}
