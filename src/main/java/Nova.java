import exceptions.NovaException;
import parser.Parser;
import storage.Storage;
import taskList.TaskList;
import ui.TextUi;

import java.util.Scanner;

public class Nova {
    private static final Scanner scanner = new Scanner(System.in);
    //private static ArrayList<Task> tasks;
    private static Storage storage;
    private static TaskList tasks;
    private static Parser parser;
    private static TextUi textUi;

    public Nova() {
        storage = new Storage();
        tasks = new TaskList(storage.loadTask());
        parser = new Parser();
        textUi = new TextUi();
    }

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

    public static void main(String[] args) {
        new Nova().run();
    }
}
