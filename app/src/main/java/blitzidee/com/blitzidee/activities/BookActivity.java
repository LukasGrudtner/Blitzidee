package blitzidee.com.blitzidee.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.adapter.NoteListAdapter;
import blitzidee.com.blitzidee.mapeadores.MapperBook;
import blitzidee.com.blitzidee.mapeadores.MapperNote;
import blitzidee.com.blitzidee.model.Book;
import blitzidee.com.blitzidee.model.Goal;
import blitzidee.com.blitzidee.model.Note;

public class BookActivity extends AppCompatActivity {

    private Book book;
    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewDate;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    private ListView listViewNotes;
    private ArrayList<Note> noteList;
    private NoteListAdapter noteListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        String bookTitle = getIntent().getExtras().getString("bookTitle");
        MapperBook mapperBook = new MapperBook(getApplicationContext());
        book = mapperBook.get(bookTitle);
        mapperBook.close();

        createToolbar();

        setTitle();
        setAuthor();
        setDate();

        setFloatingActionButton();

        noteList = book.getNoteArrayList();
        listViewNotes = (ListView) findViewById(R.id.list_view_notes);
        noteListAdapter = new NoteListAdapter(getApplicationContext(), noteList);
        listViewNotes.setAdapter(noteListAdapter);
        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createAlertDialogRemoveNote(position);
                return true;
            }
        });
        noteListAdapter.notifyDataSetChanged();
    }

    private void createAlertDialogRemoveNote(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Você realmente deseja apagar esta nota?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNoteFromDatabase(noteList.get(position));
                noteList.remove(getPositionOfNoteFromList(noteList.get(position)));
                noteListAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Removido!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deleteNoteFromDatabase(Note note) {
        MapperNote mapperNote = new MapperNote(getApplicationContext());
        mapperNote.remove(note);
        mapperNote.close();
    }

    private int getPositionOfNoteFromList(Note note) {
        int position = 0;
        for (Note aux : noteList) {
            if (note.equals(aux))
                return position;
            else
                position++;
        }
        return position;
    }

    private void setFloatingActionButton() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogAddNote();
            }
        });
    }

    private void createAlertDialogAddNote() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_note_dialog, null, false);

        final EditText editTextDescription = (EditText) view.findViewById(R.id.editTextDialogNote);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Note note = new Note();
                note.setDescription(editTextDescription.getText().toString());
                note.setBookId(book.getId());
                book.getNoteArrayList().add(note);
                noteListAdapter.notifyDataSetChanged();
                saveNoteOnDatabase(note);
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

    private void saveNoteOnDatabase(Note note) {
        MapperNote mapperNote = new MapperNote(getApplicationContext());
        mapperNote.put(note);
        mapperNote.close();
    }

    private void setTitle() {
        TextView textViewTitle = (TextView) findViewById(R.id.textViewBookActivityTitle);
        textViewTitle.setText(book.getTitle());

    }

    private void setAuthor() {
        TextView textViewAuthor = (TextView) findViewById(R.id.textViewBookActivityAuthor);
        textViewAuthor.setText(book.getAuthor());
    }

    private void setDate() {
        TextView textViewDate = (TextView) findViewById(R.id.textViewBookActivityDate);

        String date;
        GregorianCalendar gregorianCalendar = book.getStartDate();

        int day = gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH);
        if (day < 10) date = "0" + day; else date = String.valueOf(day);

        int month = gregorianCalendar.get(GregorianCalendar.MONTH);
        if (month < 10) date += "/0" + month; else date += "/" + month;

        int year = gregorianCalendar.get(GregorianCalendar.YEAR);
        date += "/" + year;

        String fullDate = date + " - ";
        GregorianCalendar gregorianCalendarEnd = book.getEndDate();

        int dayEnd = gregorianCalendarEnd.get(GregorianCalendar.DAY_OF_MONTH);
        if (dayEnd < 10) fullDate += "0" + dayEnd; else fullDate += String.valueOf(dayEnd);

        int monthEnd = gregorianCalendarEnd.get(GregorianCalendar.MONTH);
        if (monthEnd < 10) fullDate += "/0" + monthEnd; else fullDate += "/" + monthEnd;

        int yearEnd = gregorianCalendarEnd.get(GregorianCalendar.YEAR);
        fullDate += "/" + yearEnd;

        GregorianCalendar aux = new GregorianCalendar();
        aux.set(GregorianCalendar.DAY_OF_MONTH, 1);
        aux.set(GregorianCalendar.MONTH, 1);
        aux.set(GregorianCalendar.YEAR, 1970);

        if (book.getEndDate().get(GregorianCalendar.YEAR) == aux.get(GregorianCalendar.YEAR))
            textViewDate.setText(date);
        else
            textViewDate.setText(fullDate);
    }

    private void scrollMyListViewToBottom() {
        listViewNotes.post(new Runnable() {
            @Override
            public void run() {
                listViewNotes.setSelection(noteListAdapter.getCount()-1);
            }
        });
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_done:
                doActionDone();
                break;
            case R.id.item_delete:
                doActionDelete();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doActionDone() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        book.setEndDate(gregorianCalendar);
        book.setRead(true);

        updateBookOnDatabase(book);

        setDate();
        Toast.makeText(getApplicationContext(), "Atualizado!", Toast.LENGTH_SHORT).show();
    }

    private void doActionDelete() {
        deleteBookFromDatabase(book);
        Toast.makeText(getApplicationContext(), "Removido!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateBookOnDatabase(Book book) {
        MapperBook mapperBook = new MapperBook(getApplicationContext());
        mapperBook.updateBook(book);
        mapperBook.close();
    }

    private void deleteBookFromDatabase(Book book) {
        MapperBook mapperBook = new MapperBook(getApplicationContext());
        mapperBook.remove(book);
        mapperBook.close();
    }
}
