package nova.nova;

import nova.command.Command;
import nova.history.HistoryManager;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasklist.TaskList;
import nova.ui.Ui;
/**
 * The main class for the Nova chatbot.
 * Nova allows users to manage task by adding, deleting, marking and unmarking tasks.
 * Tasks can be different types such as To-do, Deadline and Event.
 */
public class Nova {

    private final Storage storage;
    private final Parser parser;
    private final HistoryManager historyManager;
    private final Ui ui;
    private final TaskList taskList;
    private final Command command;


    /**
     * Constructs a Nova chatbot instance.
     * Initializes storage, task management, parsing, and user interface components.
     */
    public Nova() {
        storage = new Storage();
        parser = new Parser();
        historyManager = new HistoryManager();
        ui = new Ui();
        taskList = new TaskList(storage.loadTask());
        command = new Command(taskList, parser, historyManager);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String response;

        if (input.isEmpty()) {
            response = "ERROR: No command given";
        } else if (input.equalsIgnoreCase("bye")) {
            storage.saveTask(taskList.getTaskArrayList());
            response = command.executeBye();
        } else if (input.equalsIgnoreCase("list")) {
            response = command.executeList();
        } else if (input.equalsIgnoreCase("undo")) {
            response = command.executeUndo();
        } else if (input.equalsIgnoreCase("redo")) {
            response = command.executeRedo();
        } else if (input.equalsIgnoreCase("help")) {
            response = command.executeHelp();
        } else {
            response = command.executeCommand(input);
        }
        return ui.returnMessage(response);
    }
}
