package blitzidee.com.blitzidee.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by lukas on 23/07/2017.
 */

public class Book {

    private int id;
    private String title;
    private String author;
    private GregorianCalendar endDate;
    private ArrayList<Note> noteArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public GregorianCalendar getEndDate() { return endDate; }

    public void setEndDate(GregorianCalendar endDate) { this.endDate = endDate; }

    public ArrayList<Note> getNoteArrayList() {
        return noteArrayList;
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }
}
