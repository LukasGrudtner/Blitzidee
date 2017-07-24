package blitzidee.com.blitzidee.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.activities.BookActivity;
import blitzidee.com.blitzidee.adapter.BookListAdapter;
import blitzidee.com.blitzidee.mapeadores.MapperBook;
import blitzidee.com.blitzidee.model.Book;
import blitzidee.com.blitzidee.model.Idea;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    private FloatingActionButton fabAdd;
    private ListView listViewBooks;
    private ArrayList<Book> bookList;
    private BookListAdapter bookListAdapter;




    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        setFloatingActionButton(view);
        bookList = loadBooksFromDatabase();

        listViewBooks = (ListView) view.findViewById(R.id.list_view_books);
        bookListAdapter = new BookListAdapter(getActivity(), bookList);
        listViewBooks.setAdapter(bookListAdapter);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openBookActivity(position);
            }
        });
        bookListAdapter.notifyDataSetChanged();

        return view;
    }

    private void openBookActivity(int position) {
        Intent intent = new Intent(getActivity(), BookActivity.class);
        intent.putExtra("bookTitle", bookList.get(position).getTitle());
        startActivity(intent);
    }

    private void setFloatingActionButton(View view) {
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_add_book);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogAddBook();
            }
        });
    }

    private void createAlertDialogAddBook() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_book_dialog, null, false);

        final EditText editTextTitle = (EditText) view.findViewById(R.id.editTextDialogBookTitle);
        final EditText editTextAuthor = (EditText) view.findViewById(R.id.editTextDialogBookAuthor);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                Book book = new Book();

                book.setDate(gregorianCalendar);
                book.setTitle(editTextTitle.getText().toString());
                book.setAuthor(editTextAuthor.getText().toString());

                bookList.add(book);
                bookListAdapter.notifyDataSetChanged();
                saveBookOnDatabase(book);
                scrollMyListViewToBottom();

                Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_SHORT).show();
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

    private void saveBookOnDatabase(Book book) {
        MapperBook mapperBook = new MapperBook(getActivity());
        mapperBook.put(book);
        mapperBook.close();
    }

    private ArrayList<Book> loadBooksFromDatabase() {
        MapperBook mapperBook = new MapperBook(getActivity());
        ArrayList<Book> bookList = mapperBook.getAll();
        mapperBook.close();
        return bookList;
    }

    private void scrollMyListViewToBottom() {
        listViewBooks.post(new Runnable() {
            @Override
            public void run() {
                listViewBooks.setSelection(bookListAdapter.getCount()-1);
            }
        });
    }

}
