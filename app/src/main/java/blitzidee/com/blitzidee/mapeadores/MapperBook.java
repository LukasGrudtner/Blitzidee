package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.model.Book;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 23/07/2017.
 */

public class MapperBook extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blitzidee.books";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS BOOKS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TITLE VARCHAR, " +
            "AUTHOR VARCHAR, " +
            "DAY INTEGER, " +
            "MONTH INTEGER, " +
            "YEAR INTEGER)";

    public MapperBook(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void put(Book book) {
        insereNovoLivro(book);
    }

    private void insereNovoLivro(Book book) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO BOOKS (TITLE, AUTHOR, DAY, MONTH, YEAR) " +
                    "VALUES('" + book.getTitle() + "', " +
                    "'" + book.getAuthor() + "', " +
                    "'" + book.getDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "'" + book.getDate().get(GregorianCalendar.MONTH) + "', " +
                    "'" + book.getDate().get(GregorianCalendar.YEAR)  + "')");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book get(String title) {
        Book book = null;

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM BOOKS " +
                    "WHERE TITLE = '" + title + "'", null);

            if (cursor.getCount() > 0) {
                int columnAuthor = cursor.getColumnIndex("AUTHOR");
                int columnDay = cursor.getColumnIndex("DAY");
                int columnMonth = cursor.getColumnIndex("MONTH");
                int columnYear = cursor.getColumnIndex("YEAR");

                cursor.moveToFirst();
                book = new Book();
                book.setTitle(title);
                book.setAuthor(cursor.getString(columnAuthor));

                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnDay));
                gregorianCalendar.set(GregorianCalendar.MONTH, cursor.getInt(columnMonth));
                gregorianCalendar.set(GregorianCalendar.YEAR, cursor.getInt(columnYear));
                book.setDate(gregorianCalendar);
            }

            database.close();
            return book;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public ArrayList<Book> getAll() {

        ArrayList<Book> bookList = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            Cursor cursor = database.rawQuery("SELECT * FROM BOOKS", null);

            /* Recuperar o índice de cada coluna. */
            int columnTitle = cursor.getColumnIndex("TITLE");
            int columnAuthor = cursor.getColumnIndex("AUTHOR");
            int columnDay = cursor.getColumnIndex("DAY");
            int columnMonth = cursor.getColumnIndex("MONTH");
            int columnYear = cursor.getColumnIndex("YEAR");

            cursor.moveToFirst();
            Book book;

            for (int i = 0; i < cursor.getCount(); i++) {
                book = new Book();
                book.setTitle(cursor.getString(columnTitle));
                book.setAuthor(cursor.getString(columnAuthor));

                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnDay));
                gregorianCalendar.set(GregorianCalendar.MONTH, cursor.getInt(columnMonth));
                gregorianCalendar.set(GregorianCalendar.YEAR, cursor.getInt(columnYear));
                book.setDate(gregorianCalendar);

                bookList.add(book);
                cursor.moveToNext();
            }

            database.close();
            return bookList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public void remove(Book book) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DELETE FROM BOOKS WHERE TITLE = '" + book.getTitle() + "'");

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
