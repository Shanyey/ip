package nova.tasks;

/**
 * Represents the base class for tasks in Nova chatbot.
 * Provides common attributes and methods for different type of tasks.
 *
 * @author Shanyey
 */
public abstract class Task implements Cloneable {
    private final String description;
    private boolean isDone = false;

    /**
     * Constructs a new task with the specified description.
     * Initially marked as not done when created.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Constructs a new Task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone A boolean indicating whether the task is completed.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks this task as done.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void setNotDone() {
        isDone = false;
    }

    /**
     * Checks whether this task is completed.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task formatted for saving.
     * Each subclass must implement this method.
     *
     * @return A string representing the task in a format suitable for storage.
     */
    public abstract String getSaveData();

    /**
     * Returns a string representation of the task, including its completion status.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    public boolean descriptionContains(String description) {
        return this.description.contains(description);
    }

    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
