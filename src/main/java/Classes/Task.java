package Classes;

public class Task {
    private final String description;
    private boolean isDone = false; //set to false

    public Task(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return (isDone? "[X] " : "[ ] ") + description;
    }
}
