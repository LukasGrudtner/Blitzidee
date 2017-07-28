package blitzidee.com.blitzidee.mapeadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.model.Book;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Idea;

/**
 * Created by lukas on 23/07/2017.
 */

public class MapperBook extends SQLiteOpenHelper {

    private MapperNote mapperNote;
    private static final String DATABASE_NAME = "blitzidee.books";
    private static final String STRING_CREATION_TABLE = "CREATE TABLE IF NOT EXISTS BOOKS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TITLE VARCHAR, " +
            "AUTHOR VARCHAR, " +
            "START_DAY INTEGER, " +
            "START_MONTH INTEGER, " +
            "START_YEAR INTEGER, " +
            "END_DAY INTEGER, " +
            "END_MONTH INTEGER, " +
            "END_YEAR INTEGER, " +
            "WAS_READ INTEGER)";

    public MapperBook(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mapperNote = new MapperNote(context);
    }

    public void put(Book book) {
        insereNovoLivro(book);
    }

    private void insereNovoLivro(Book book) {

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("INSERT INTO BOOKS (TITLE, AUTHOR, START_DAY, START_MONTH, START_YEAR, " +
                    "END_DAY, END_MONTH, END_YEAR, WAS_READ) " +
                    "VALUES('" + book.getTitle() + "', " +
                    "'" + book.getAuthor() + "', " +
                    "'" + book.getStartDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "'" + book.getStartDate().get(GregorianCalendar.MONTH) + "', " +
                    "'" + book.getStartDate().get(GregorianCalendar.YEAR)  + "', " +
                    "'" + book.getEndDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "'" + book.getEndDate().get(GregorianCalendar.MONTH) + "', " +
                    "'" + book.getEndDate().get(GregorianCalendar.YEAR)  + "', " +
                    "'" + (book.wasRead()? 1 : 0) + "')");

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        atualizaLivroExiste(book);
    }

    private void atualizaLivroExiste(Book book) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("UPDATE BOOKS SET AUTHOR = '" + book.getAuthor() + "', " +
                    "START_DAY = '" + book.getStartDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "START_MONTH = '" + book.getStartDate().get(GregorianCalendar.MONTH) + "', " +
                    "START_YEAR = '" + book.getStartDate().get(GregorianCalendar.YEAR) + "', " +
                    "END_DAY = '" + book.getEndDate().get(GregorianCalendar.DAY_OF_MONTH) + "', " +
                    "END_MONTH = '" + book.getEndDate().get(GregorianCalendar.MONTH) + "', " +
                    "END_YEAR = '" + book.getEndDate().get(GregorianCalendar.YEAR) + "', " +
                    "WAS_READ = '" + (book.wasRead()? 1 : 0) + "' " +
                    "WHERE TITLE = '" + book.getTitle() + "'");

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
                int columnId = cursor.getColumnIndex("ID");
                int columnAuthor = cursor.getColumnIndex("AUTHOR");
                int columnStartDay = cursor.getColumnIndex("START_DAY");
                int columnStartMonth = cursor.getColumnIndex("START_MONTH");
                int columnStartYear = cursor.getColumnIndex("START_YEAR");
                int columnEndDay = cursor.getColumnIndex("END_DAY");
                int columnEndMonth = cursor.getColumnIndex("END_MONTH");
                int columnEndYear = cursor.getColumnIndex("END_YEAR");
                int columnWasRead = cursor.getColumnIndex("WAS_READ");

                cursor.moveToFirst();
                book = new Book();
                book.setId(cursor.getInt(columnId));
                book.setTitle(title);
                book.setAuthor(cursor.getString(columnAuthor));
                book.setRead((cursor.getInt(columnWasRead) == 1)? true : false);

                GregorianCalendar gregorianCalendarStart = new GregorianCalendar();
                gregorianCalendarStart.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnStartDay));
                gregorianCalendarStart.set(GregorianCalendar.MONTH, cursor.getInt(columnStartMonth));
                gregorianCalendarStart.set(GregorianCalendar.YEAR, cursor.getInt(columnStartYear));
                book.setStartDate(gregorianCalendarStart);

                GregorianCalendar gregorianCalendarEnd = new GregorianCalendar();
                gregorianCalendarEnd.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnEndDay));
                gregorianCalendarEnd.set(GregorianCalendar.MONTH, cursor.getInt(columnEndMonth));
                gregorianCalendarEnd.set(GregorianCalendar.YEAR, cursor.getInt(columnEndYear));
                book.setEndDate(gregorianCalendarEnd);

                book.setNoteArrayList(mapperNote.getAll(book));

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
            int columnStartDay = cursor.getColumnIndex("START_DAY");
            int columnStartMonth = cursor.getColumnIndex("START_MONTH");
            int columnStartYear = cursor.getColumnIndex("START_YEAR");
            int columnEndDay = cursor.getColumnIndex("END_DAY");
            int columnEndMonth = cursor.getColumnIndex("END_MONTH");
            int columnEndYear = cursor.getColumnIndex("END_YEAR");
            int columnWasRead = cursor.getColumnIndex("WAS_READ");

            cursor.moveToFirst();
            Book book;

            for (int i = 0; i < cursor.getCount(); i++) {
                book = new Book();
                book.setTitle(cursor.getString(columnTitle));
                book.setAuthor(cursor.getString(columnAuthor));
                book.setRead((cursor.getInt(columnWasRead) == 1) ? true : false);

                GregorianCalendar gregorianCalendarStart = new GregorianCalendar();
                gregorianCalendarStart.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnStartDay));
                gregorianCalendarStart.set(GregorianCalendar.MONTH, cursor.getInt(columnStartMonth));
                gregorianCalendarStart.set(GregorianCalendar.YEAR, cursor.getInt(columnStartYear));
                book.setStartDate(gregorianCalendarStart);

                GregorianCalendar gregorianCalendarEnd = new GregorianCalendar();
                gregorianCalendarEnd.set(GregorianCalendar.DAY_OF_MONTH, cursor.getInt(columnEndDay));
                gregorianCalendarEnd.set(GregorianCalendar.MONTH, cursor.getInt(columnEndMonth));
                gregorianCalendarEnd.set(GregorianCalendar.YEAR, cursor.getInt(columnEndYear));
                book.setEndDate(gregorianCalendarEnd);

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

    public void drop() {
        try {
            SQLiteDatabase database = this.getWritableDatabase();

            database.execSQL(STRING_CREATION_TABLE);

            database.execSQL("DROP TABLE BOOKS");

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
