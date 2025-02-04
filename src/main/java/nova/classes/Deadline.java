package nova.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;
    protected LocalDateTime deadline;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected String saveData;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.deadline = LocalDateTime.parse(by, formatter);
        this.saveData = "[D]" + super.toString() + " (by: " + by + ")";
    }

    public Deadline(String description, String by, Boolean isDone) {
        super(description, isDone);
        this.by = by;
        this.deadline = LocalDateTime.parse(by, formatter);
        this.saveData = "[D]" + super.toString() + " (by: " + by + ")";
    }

    public String getSaveData() {
        return this.saveData;
    }

    @Override
    public String toString() {
        String deadline = this.deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
