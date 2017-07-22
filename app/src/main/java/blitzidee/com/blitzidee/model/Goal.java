package blitzidee.com.blitzidee.model;

/**
 * Created by lukas on 21/07/2017.
 */

public class Goal {

    private int ideaId;
    private String description;
    private boolean isComplete = false;

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

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
