package nova;

import nova.exceptions.NovaException;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasklist.TaskList;
import nova.ui.TextUi;

import java.util.Scanner;

/**
 *  The main class for the Nova chatbot.
 *  Nova allows users to manage task by adding, deleting, marking and unmarking tasks.
 *  Tasks can be different types such as To-do, Deadline and Event.
 */
public class Nova {
    private static final Scanner scanner = new Scanner(System.in);

    private final Storage storage;
    private final TaskList tasks;
    private final Parser parser;
    private final TextUi textUi;

    /**
     * Constructs a Nova chatbot instance.
     * Initializes storage, task management, parsing, and user interface components.
     */
    public Nova() {
        storage = new Storage();
        tasks = new TaskList(storage.loadTask());
        parser = new Parser();
        textUi = new TextUi();
    }

    /**
     * Starts the chatbot and processes user input in a loop.
     * The chatbot supports commands like "bye", "list", "mark", "unmark", "delete", "to-do", "deadline", and "event".
     * User inputs are parsed and appropriate actions are performed.
     */
    public void run() {
        textUi.printWelcomeMessage();
        while (true) {
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("bye")) {
                textUi.printExitMessage();
                break;
            } else if (action.equalsIgnoreCase("list")) {
                textUi.printTasksList(tasks);
                continue;
            } else {
                try {
                    String[] splitAction = parser.parseBySpace(action); //[ type, desc + others]
                    String[] slashedAction = parser.splitBySlash(splitAction[1]); //[desc, by or start, to]
                    switch (splitAction[0].toLowerCase()) {
                    case "mark" -> tasks.markTask(Integer.parseInt(splitAction[1]));
                    case "unmark" -> tasks.unMarkTask(Integer.parseInt(splitAction[1]));
                    case "delete" -> tasks.deleteTask(Integer.parseInt(splitAction[1]));
                    case "todo" -> tasks.addToDo(splitAction[1]);
                    case "deadline" -> tasks.addDeadline(slashedAction);
                    case "event" -> tasks.addEvent(slashedAction);
                    default -> textUi.printUnknownInputMessage();
                    }
                } catch (NovaException e) {
                    textUi.printErrorMessage(e);
                    continue;
                }
            }
            storage.saveTask(tasks.getTasks());
        }
    }

    /**
     * The main entry point of the Nova chatbot.
     * It creates an instance of Nova and starts execution.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Nova().run();
    }
}
