package blitzidee.com.blitzidee.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by lukas on 23/07/2017.
 */

public class Book {

    private String id;
    private String title;
    private String author;
    private GregorianCalendar date;
    private ArrayList<Note> noteArrayList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public ArrayList<Note> getNoteArrayList() {
        return noteArrayList;
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }
}
