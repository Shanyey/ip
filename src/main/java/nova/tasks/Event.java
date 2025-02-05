package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task, which is a subtype of the Task class.
 *
 * @author shanyey
 */
public class Event extends Task {
    protected String by;
    protected String startingDate;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected String saveData;

    /**
     * Constructs a new Event with the specified description, start date, and end date.
     * The event task is initially marked as not done.
     *
     * @param description The description of the event task.
     * @param startingDate The start date/time of the event in "yyyy-MM-dd HH:mm" format.
     * @param by The end date/time of the event in "yyyy-MM-dd HH:mm" format.
     */
    public Event(String description, String startingDate, String by) {
        super(description);
        this.by = by;
        this.startingDate = startingDate;
        this.start = LocalDateTime.parse(startingDate, FORMATTER);
        this.end = LocalDateTime.parse(by, FORMATTER);
        this.saveData = "[E]" + super.toString() + " (from: " + startingDate + " to: " + by + ")";
    }

    /**
     * Constructs a new Event with the specified description, start date, end date, and completion status.
     *
     * @param description The description of the event task.
     * @param startingDate The start date/time of the event in "yyyy-MM-dd HH:mm" format.
     * @param by The end date/time of the event in "yyyy-MM-dd HH:mm" format.
     * @param isDone A boolean indicating whether the event is completed.
     */
    public Event(String description, String startingDate, String by, Boolean isDone) {
        super(description, isDone);
        this.by = by;
        this.startingDate = startingDate;
        this.start = LocalDateTime.parse(startingDate, FORMATTER);
        this.end = LocalDateTime.parse(by, FORMATTER);
        this.saveData = "[E]" + super.toString() + " (from: " + startingDate + " to: " + by + ")";
    }

    /**
     * Returns a formatted string representation of the event for saving purposes.
     *
     * @return A string representing the event in a format suitable for storage.
     */
    public String getSaveData() {
        return this.saveData;
    }

    /**
     * Returns a string representation of the event for user display.
     * The format includes a "[E]" prefix and the formatted start and end times.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        String start = this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        String end = this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
