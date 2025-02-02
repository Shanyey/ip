package Classes;

public abstract class Task {
    private final String description;
    private boolean isDone = false; //set to false

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone() {
        isDone = true;
    }

    public void setNotDone() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public String getSavedData() {
        return "test";
    }

    public abstract String getSaveData();

    @Override
    public String toString() {
        return (isDone? "[X] " : "[ ] ") + description;
    }
}
