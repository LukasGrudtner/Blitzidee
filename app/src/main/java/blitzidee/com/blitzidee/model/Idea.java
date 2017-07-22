package blitzidee.com.blitzidee.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by lukas on 19/07/2017.
 */

public class Idea {

    private String title;
    private ArrayList<Goal> goalArrayList;
    private GregorianCalendar creationDate;
    private GregorianCalendar endDate;
    private boolean isComplete = false;
    private int id;

    public Idea() {
        goalArrayList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Goal> getGoalArrayList() {
        return goalArrayList;
    }

    public void setGoalArrayList(ArrayList<Goal> goalArrayList) {
        this.goalArrayList = goalArrayList;
    }

    public GregorianCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(GregorianCalendar creationDate) {
        this.creationDate = creationDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setConclusion(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
