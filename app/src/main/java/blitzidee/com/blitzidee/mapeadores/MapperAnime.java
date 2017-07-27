package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.model.Anime;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 27/07/2017.
 */

public class MapperAnime extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blitzidee.animes";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS ANIMES (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TITLE VARCHAR, " +
            "DAY INTEGER, " +
            "MONTH INTEGER, " +
            "YEAR INTEGER)";

    public MapperAnime(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void put(Anime anime) {
        insereNovoAnime(anime);
    }

    private void insereNovoAnime(Anime anime) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO ANIMES (TITLE, DAY, MONTH, YEAR) " +
                    "VALUES('" + anime.getTitle() + "', " +
                    "'" + anime.getDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "'" + anime.getDate().get(GregorianCalendar.MONTH) + "', " +
                    "'" + anime.getDate().get(GregorianCalendar.YEAR) + "')");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Anime get(String title) {
        Anime anime = null;

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM ANIMES " +
                    "WHERE TITLE = '" + title + "'", null);

            if (cursor.getCount() > 0) {
                int columnDay = cursor.getColumnIndex("DAY");
                int columnMonth = cursor.getColumnIndex("MONTH");
                int columnYear = cursor.getColumnIndex("YEAR");

                cursor.moveToFirst();
                anime = new Anime();
                anime.setTitle(title);

                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnDay));
                gregorianCalendar.set(GregorianCalendar.MONTH, cursor.getInt(columnMonth));
                gregorianCalendar.set(GregorianCalendar.YEAR, cursor.getInt(columnYear));
                anime.setDate(gregorianCalendar);
            }

            database.close();
            return anime;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Anime> getAll() {

        ArrayList<Anime> animeList = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM ANIMES", null);

            /* Recuperar o índice de cada coluna. */
            int columnTitle = cursor.getColumnIndex("TITLE");
            int columnDay = cursor.getColumnIndex("DAY");
            int columnMonth = cursor.getColumnIndex("MONTH");
            int columnYear = cursor.getColumnIndex("YEAR");

            cursor.moveToFirst();
            Anime anime;

            for (int i = 0; i < cursor.getCount(); i++) {
                anime = new Anime();
                anime.setTitle(cursor.getString(columnTitle));

                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnDay));
                gregorianCalendar.set(GregorianCalendar.MONTH, cursor.getInt(columnMonth));
                gregorianCalendar.set(GregorianCalendar.YEAR, cursor.getInt(columnYear));
                anime.setDate(gregorianCalendar);

                animeList.add(anime);
                cursor.moveToNext();
            }

            database.close();
            return animeList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return animeList;
    }

    public void remove(Anime anime) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DELETE FROM ANIMES WHERE TITLE = '" + anime.getTitle() + "'");

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
