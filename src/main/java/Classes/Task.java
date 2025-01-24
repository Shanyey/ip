package Classes;

public class Task {
    private final String description;
    private boolean isDone = false; //set to false

    public Task(String description) {
        this.description = description;
    }
    public void done() {
        isDone = true;
    }

    public void undone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return (isDone? "[X] " : "[ ] ") + description;
    }
}
