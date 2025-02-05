package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected String by;
    protected String startingDate;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected String saveData;

    public Event(String description, String startingDate, String by) {
        super(description);
        this.by = by;
        this.startingDate = startingDate;
        this.start = LocalDateTime.parse(startingDate, FORMATTER);
        this.end = LocalDateTime.parse(by, FORMATTER);
        this.saveData = "[E]" + super.toString() + " (from: " + startingDate + " to: " + by + ")";
    }

    public Event(String description, String startingDate, String by, Boolean isDone) {
        super(description, isDone);
        this.by = by;
        this.startingDate = startingDate;
        this.start = LocalDateTime.parse(startingDate, FORMATTER);
        this.end = LocalDateTime.parse(by, FORMATTER);
        this.saveData = "[E]" + super.toString() + " (from: " + startingDate + " to: " + by + ")";
    }

    public String getSaveData() {
        return this.saveData;
    }

    @Override
    public String toString() {
        String start = this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        String end = this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
