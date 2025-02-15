package nova.nova;

import nova.command.Command;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasklist.TaskList;

/**
 * The main class for the Nova chatbot.
 * Nova allows users to manage task by adding, deleting, marking and unmarking tasks.
 * Tasks can be different types such as To-do, Deadline and Event.
 */
public class Nova {

    private final Storage storage;
    private final TaskList tasks;
    private final Parser parser;
    private final Command command;

    /**
     * Constructs a Nova chatbot instance.
     * Initializes storage, task management, parsing, and user interface components.
     */
    public Nova() {
        storage = new Storage();
        tasks = new TaskList(storage.loadTask());
        parser = new Parser();
        command = new Command(tasks, parser);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            storage.saveTask(tasks.getTasks());
            return command.executeBye();
        } else if (input.equalsIgnoreCase("list")) {
            return command.executeList();
        } else if (input.isEmpty()) {
            return "No command given";
        } else if (input.equalsIgnoreCase("undo")) {
            return command.executeUndo();
        } else if (input.equalsIgnoreCase("redo")) {
            return command.executeRedo();
        } else {
            return command.executeCommand(input);
        }
    }
}
