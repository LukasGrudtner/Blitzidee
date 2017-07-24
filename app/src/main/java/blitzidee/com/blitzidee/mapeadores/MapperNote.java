package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.model.Book;
import blitzidee.com.blitzidee.model.Note;

/**
 * Created by lukas on 23/07/2017.
 */

public class MapperNote extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bltizidee.notes";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS NOTES (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DESCRIPTION VARCHAR, " +
            "BOOK_ID INTEGER)";

    public MapperNote(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void put(Note note) {
        insereNovaNota(note);
    }

    private void insereNovaNota(Note note) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO NOTES (DESCRIPTION, BOOK_ID) " +
                    "VALUES('" + note.getDescription() + "', " +
                    "'" + note.getBookId() + "')");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getAll(Book book) {

        ArrayList<Note> noteList = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM NOTES WHERE BOOK_ID = '" + book.getId() + "'", null);

            /* Recuperar o índice de cada coluna. */
            int columnDescription = cursor.getColumnIndex("DESCRIPTION");

            cursor.moveToFirst();
            Note note;

            for (int i = 0; i < cursor.getCount(); i++) {
                note = new Note();
                note.setDescription(cursor.getString(columnDescription));
                note.setBookId(book.getId());

                noteList.add(note);
                cursor.moveToNext();
            }

            database.close();
            return noteList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return noteList;
    }

    public void remove(Note note) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DELETE FROM NOTES WHERE BOOK_ID = '" + note.getBookId() + "' AND DESCRIPTION = '" + note.getDescription() + "'");
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
