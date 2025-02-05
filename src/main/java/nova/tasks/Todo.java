package nova.tasks;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public String getSaveData() {
        return this.toString();
    }

    public Todo(String description, Boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
