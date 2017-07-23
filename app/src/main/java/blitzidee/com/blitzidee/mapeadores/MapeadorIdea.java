package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 19/07/2017.
 */

public class MapeadorIdea extends SQLiteOpenHelper{

    private MapeadorGoal mapeadorGoal;
    private static final String DATABASE_NAME = "blitzidee.ideas";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS IDEAS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TITLE VARCHAR, " +
            "START_DAY INTEGER, " +
            "START_MONTH INTEGER, " +
            "START_YEAR INTEGER, " +
            "END_DAY INTEGER, " +
            "END_MONTH INTEGER, " +
            "END_YEAR INTEGER, " +
            "IS_COMPLETE INTEGER)";

    public MapeadorIdea(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mapeadorGoal = new MapeadorGoal(context);
    }

    public void put(Idea idea) {

        if (this.get(idea.getTitle()) != null)
            atualizaIdeiaExistente(idea.getTitle(), idea);
        else
            insereNovaIdeia(idea);
    }

    public void updateIdea(String oldTitle, Idea idea) {
        atualizaIdeiaExistente(oldTitle, idea);
    }

    private void insereNovaIdeia(Idea idea) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO IDEAS (TITLE, START_DAY, START_MONTH, " +
                            "START_YEAR, END_DAY, END_MONTH, END_YEAR, IS_COMPLETE) " +
                            "VALUES('" + idea.getTitle() + "', " +
                            "'" + idea.getCreationDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                            "'" + idea.getCreationDate().get(GregorianCalendar.MONTH) + "', " +
                            "'" + idea.getCreationDate().get(GregorianCalendar.YEAR) + "', " +
                            "'" + idea.getEndDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                            "'" + idea.getEndDate().get(GregorianCalendar.MONTH) + "', " +
                            "'" + idea.getEndDate().get(GregorianCalendar.YEAR) + "', " +
                            "'" + ((idea.isComplete())? 1 : 0) + "')");

            Cursor cursor = database.rawQuery("SELECT ID FROM IDEAS WHERE TITLE = '" + idea.getTitle() + "'", null);
            cursor.moveToFirst();
            idea.setId(cursor.getInt(0));

            for (Goal goal : idea.getGoalArrayList())
                mapeadorGoal.put(goal);

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizaIdeiaExistente(String oldTitle, Idea idea) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("UPDATE IDEAS SET START_DAY = '" + idea.getCreationDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "START_MONTH = '" + idea.getCreationDate().get(GregorianCalendar.MONTH) + "', " +
                    "START_YEAR = '" + idea.getCreationDate().get(GregorianCalendar.YEAR) + "', " +
                    "END_DAY = '" + idea.getEndDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "END_MONTH = '" + idea.getEndDate().get(GregorianCalendar.MONTH) + "', " +
                    "END_YEAR = '" + idea.getEndDate().get(GregorianCalendar.YEAR) + "', " +
                    "IS_COMPLETE = '" + ((idea.isComplete())? 1 : 0) + "' " +
                    "WHERE TITLE = '" + oldTitle + "'");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Idea get(String title) {
        Idea idea = null;

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT ID, START_DAY, START_MONTH, " +
                "START_YEAR, END_DAY, END_MONTH, END_YEAR, IS_COMPLETE FROM IDEAS " +
                "WHERE TITLE = '" + title + "'", null);

            if (cursor.getCount() > 0) {
                int columnId = cursor.getColumnIndex("ID");
                int columnStartDay = cursor.getColumnIndex("START_DAY");
                int columnStartMonth = cursor.getColumnIndex("START_MONTH");
                int columnStartYear = cursor.getColumnIndex("START_YEAR");
                int columnEndDay = cursor.getColumnIndex("END_DAY");
                int columnEndMonth = cursor.getColumnIndex("END_MONTH");
                int columnEndYear = cursor.getColumnIndex("END_YEAR");
                int columnIsComplete = cursor.getColumnIndex("IS_COMPLETE");

                cursor.moveToFirst();
                idea = new Idea();
                idea.setId(cursor.getInt(columnId));
                idea.setTitle(title);
                idea.setConclusion((cursor.getInt(columnIsComplete) == 1) ? true : false);

                GregorianCalendar gregorianCalendarStart = new GregorianCalendar();
                gregorianCalendarStart.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnStartDay));
                gregorianCalendarStart.set(GregorianCalendar.MONTH, cursor.getInt(columnStartMonth));
                gregorianCalendarStart.set(GregorianCalendar.YEAR, cursor.getInt(columnStartYear));
                idea.setCreationDate(gregorianCalendarStart);

                GregorianCalendar gregorianCalendarEnd = new GregorianCalendar();
                gregorianCalendarEnd.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnEndDay));
                gregorianCalendarEnd.set(GregorianCalendar.MONTH, cursor.getInt(columnEndMonth));
                gregorianCalendarEnd.set(GregorianCalendar.YEAR, cursor.getInt(columnEndYear));
                idea.setEndDate(gregorianCalendarEnd);

                idea.setGoalArrayList(mapeadorGoal.getAll(idea));
            }

            database.close();
            return idea;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Idea> getAll() {

        ArrayList<Idea> ideaList = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM IDEAS", null);

            /* Recuperar o índice de cada coluna. */
            int columnId = cursor.getColumnIndex("ID");
            int columnTitle = cursor.getColumnIndex("TITLE");
            int columnStartDay = cursor.getColumnIndex("START_DAY");
            int columnStartMonth = cursor.getColumnIndex("START_MONTH");
            int columnStartYear = cursor.getColumnIndex("START_YEAR");
            int columnEndDay = cursor.getColumnIndex("END_DAY");
            int columnEndMonth = cursor.getColumnIndex("END_MONTH");
            int columnEndYear = cursor.getColumnIndex("END_YEAR");
            int columnIsComplete = cursor.getColumnIndex("IS_COMPLETE");

            cursor.moveToFirst();
            Idea idea;

            for (int i = 0; i < cursor.getCount(); i++) {
                idea = new Idea();
                idea.setId(cursor.getInt(columnId));
                idea.setTitle(cursor.getString(columnTitle));
                idea.setConclusion((cursor.getInt(columnIsComplete) == 1) ? true : false);

                GregorianCalendar gregorianCalendarStart = new GregorianCalendar();
                gregorianCalendarStart.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnStartDay));
                gregorianCalendarStart.set(GregorianCalendar.MONTH, cursor.getInt(columnStartMonth));
                gregorianCalendarStart.set(GregorianCalendar.YEAR, cursor.getInt(columnStartYear));
                idea.setCreationDate(gregorianCalendarStart);

                GregorianCalendar gregorianCalendarEnd = new GregorianCalendar();
                gregorianCalendarEnd.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnEndDay));
                gregorianCalendarEnd.set(GregorianCalendar.MONTH, cursor.getInt(columnEndMonth));
                gregorianCalendarEnd.set(GregorianCalendar.YEAR, cursor.getInt(columnEndYear));
                idea.setEndDate(gregorianCalendarEnd);

                ideaList.add(idea);
                cursor.moveToNext();
            }

            database.close();
            return ideaList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ideaList;
    }


    public void remove(Idea idea) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DELETE FROM IDEAS WHERE TITLE = '" + idea.getTitle() + "'");

            for (Goal goal : idea.getGoalArrayList())
                mapeadorGoal.remove(goal);

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
