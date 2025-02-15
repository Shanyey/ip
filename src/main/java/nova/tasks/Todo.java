package nova.tasks;

/**
 * Represents a To-do class which is a subtype of the Task class.
 *
 * @author Shanyey
 */
public class Todo extends Task {

    /**
     * Constructs a new To-do object with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a new to-do object with the specified description and completion status.
     *
     * @param description The description of the to-do task.
     * @param isDone A boolean indicating whether the task is complete.
     */
    public Todo(String description, Boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns a string representation of the to-do task formatted for saving.
     *
     * @return A string formatted in a suitable way for saving.
     */
    public String getSaveData() {
        return this.toString();
    }

    /**
     * Returns a string representation of the to-do task.
     * Adds a [T] prefix to indicate it is a to-do type
     *
     * @return A formatted string representing the to-do task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public Todo clone() {
        return (Todo) super.clone();
    }
}
