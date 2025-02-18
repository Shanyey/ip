package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import nova.exceptions.NovaException;

/**
 * Represents a deadline task, which is a subtype of the Task class.
 *
 * @author Shanyey
 */
public class Deadline extends Task {
    protected String by;
    protected LocalDateTime deadline;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected String saveData;

    /**
     * Constructs a new Deadline task with the specified description and due date.
     * The task is initially marked as not done.
     *
     * @param description The description of the deadline task.
     * @param by The deadline in "yyyy-MM-dd HH:mm" format.
     */
    public Deadline(String description, String by) throws NovaException {
        super(description);

        try {
            this.deadline = LocalDateTime.parse(by, formatter);
        } catch (DateTimeParseException e) {
            throw new NovaException("ERROR: invalid date time format.");
        }

        if (deadline.isBefore(LocalDateTime.now())) {
            throw new NovaException("ERROR: deadline should be in the future.");
        }

        this.by = by;
        this.saveData = "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Constructs a new Deadline task with the specified description, due date, and completion status.
     *
     * @param description The description of the deadline task.
     * @param by The deadline in "yyyy-MM-dd HH:mm" format.
     * @param isDone A boolean indicating whether the task is completed.
     */
    public Deadline(String description, String by, Boolean isDone) {
        super(description, isDone);

        this.by = by;
        this.deadline = LocalDateTime.parse(by, formatter);
        this.saveData = "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns a formatted string representation of the deadline task for saving purposes.
     *
     * @return A string representing the deadline task in a format suitable for storage.
     */
    public String getSaveData() {
        return this.saveData;
    }

    /**
     * Returns a string representation of the deadline task for user display.
     * The format includes a "[D]" prefix and the formatted deadline date.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        String deadline = this.deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));

        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }

    /**
     * Creates and returns a copy of this deadline instance.
     *
     * @return a cloned copy of this deadline object.
     */
    @Override
    public Deadline clone() {
        return (Deadline) super.clone();
    }
}
