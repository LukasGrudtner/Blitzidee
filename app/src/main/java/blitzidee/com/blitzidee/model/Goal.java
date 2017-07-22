package blitzidee.com.blitzidee.model;

/**
 * Created by lukas on 21/07/2017.
 */

public class Goal {

    private String description;
    private boolean isComplete = false;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setConclusion(boolean isComplete) {
        this.isComplete = isComplete;
    }

}
