package blitzidee.com.blitzidee.model;

import java.util.GregorianCalendar;

/**
 * Created by lukas on 19/07/2017.
 */

public class Idea {

    private String title;
    private String description;
    private GregorianCalendar creationDate;
    private GregorianCalendar endDate;
    private boolean isComplete = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
