package blitzidee.com.blitzidee.model;

import java.util.GregorianCalendar;

/**
 * Created by lukas on 27/07/2017.
 */

public class Anime {

    private String title;
    private GregorianCalendar date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}
