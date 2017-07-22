package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 22/07/2017.
 */

public class MapeadorGoal extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blitzidee.goals";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS GOALS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DESCRIPTION VARCHAR, " +
            "IDEA_ID INTEGER, " +
            "IS_COMPLETE INTEGER)";

    public MapeadorGoal(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void put(Goal goal) {

        insereNovaMeta(goal);
    }

    private void insereNovaMeta(Goal goal) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO GOALS (DESCRIPTION, IDEA_ID, IS_COMPLETE) " +
                    "VALUES('" + goal.getDescription() + "', " +
                    "'" + goal.getIdeaId() + "', " +
                    "'" + ((goal.isComplete())? 1 : 0) + "')");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGoal(Goal goal) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("UPDATE GOALS SET IS_COMPLETE = '" + (goal.isComplete()? 1 : 0) + "' " +
                    "WHERE IDEA_ID = '" + goal.getIdeaId() + "' AND DESCRIPTION = '" + goal.getDescription() + "'");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Goal> getAll(Idea idea) {

        ArrayList<Goal> goalList = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM GOALS WHERE IDEA_ID = '" + idea.getId() + "'", null);

            /* Recuperar o índice de cada coluna. */
            int columnDescription = cursor.getColumnIndex("DESCRIPTION");
            int columnIsComplete = cursor.getColumnIndex("IS_COMPLETE");

            cursor.moveToFirst();
            Goal goal;

            for (int i = 0; i < cursor.getCount(); i++) {
                goal = new Goal();
                goal.setDescription(cursor.getString(columnDescription));
                goal.setConclusion((cursor.getInt(columnIsComplete) == 1) ? true : false);
                goal.setIdeaId(idea.getId());

                goalList.add(goal);
                cursor.moveToNext();
            }

            database.close();
            return goalList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return goalList;
    }

    public void remove(Goal goal) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DELETE FROM GOALS WHERE IDEA_ID = '" + goal.getIdeaId() + "' AND DESCRIPTION = '" + goal.getDescription() + "'");
            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
