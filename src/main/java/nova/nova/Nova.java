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
     *
     * @param input the user's raw chat input
     * @return the formatted response after executing the appropriate command
     */
    public String getResponse(String input) {
        String response;
        String cleanedInput = input.trim().toLowerCase().replaceAll("\\s+", " ");

        if (cleanedInput.isEmpty()) {
            response = "ERROR: No command given";
        } else if (cleanedInput.equals("bye")) {
            storage.saveTask(taskList.getTaskArrayList());
            response = command.executeBye();
        } else if (cleanedInput.equals("list")) {
            response = command.executeList();
        } else if (cleanedInput.equals("undo")) {
            response = command.executeUndo();
        } else if (cleanedInput.equals("redo")) {
            response = command.executeRedo();
        } else if (cleanedInput.equals("help")) {
            response = command.executeHelp();
        } else {
            response = command.executeCommand(cleanedInput);
        }
        return ui.returnMessage(response);
    }
}
