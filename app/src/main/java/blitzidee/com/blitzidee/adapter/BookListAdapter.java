package blitzidee.com.blitzidee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.mapeadores.MapperBook;
import blitzidee.com.blitzidee.model.Book;

/**
 * Created by lukas on 23/07/2017.
 */

public class BookListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Book> bookList;

    public BookListAdapter(Context context, ArrayList<Book> objects) {
        super(context, 0, objects);
        this.context = context;
        this.bookList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (bookList != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.book_view_list, parent, false);

            setTitle(view, position);
            setAuthor(view, position);
            setStringPosition(view, position);
            setImageIcon(view, position);
        }

        return view;
    }

    private void setTitle(View view, int position) {
        TextView textViewBookTitle = (TextView) view.findViewById(R.id.textViewBookTitle);
        textViewBookTitle.setText(bookList.get(position).getTitle());
    };

    private void setAuthor(View view, int position) {
        TextView textViewBookAuthor = (TextView) view.findViewById(R.id.textViewBookAuthor);
        textViewBookAuthor.setText(bookList.get(position).getAuthor());
    }

    private void setStringPosition(View view, int position) {
        TextView textViewStringPosition = (TextView) view.findViewById(R.id.textViewBookPosition);
        textViewStringPosition.setText(String.valueOf(position+1));
    }

    private void setImageIcon(View view, int position) {
        ImageView imageViewBookmark = (ImageView) view.findViewById(R.id.imageViewBookmark);
        imageViewBookmark.setImageResource(R.drawable.ic_action_bookmark);
    }

}
