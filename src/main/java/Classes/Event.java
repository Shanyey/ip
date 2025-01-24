package Classes;

public class Event extends Task {
    protected String by;
    protected String startingDate;

    public Event(String description, String startingDate, String by) {
        super(description);
        this.by = by;
        this.startingDate = startingDate;
    }

    @Override
    public String toString() {
        String msg = "[E]" + super.toString() + "(from: " + startingDate + " to: " + by + ")";
        msg = msg.replace("  ", " ");
        return msg;
    }
}
