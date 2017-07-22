package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.model.Goal;

/**
 * Created by lukas on 21/07/2017.
 */

public class GoalListAdapter extends ArrayAdapter{

    private Context context;
    private ArrayList<Goal> listGoals;


    public GoalListAdapter(Context context, ArrayList<Goal> objects) {
        super(context, 0, objects);
        this.context = context;
        this.listGoals = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (listGoals != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.goal_view_list, parent, false);

            setCheckbox(view, position);
            setDescription(view, position);

        }

        return view;
    }



    private void setCheckbox(View view, int position) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxGoal);
        if (listGoals.get(position).isComplete())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);
    }

    private void setDescription(View view, int position) {
        TextView textViewGoalDescription = (TextView) view.findViewById(R.id.textViewGoalDescription);
        textViewGoalDescription.setText(listGoals.get(position).getDescription());
    }
}