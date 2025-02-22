package nova.command;

import nova.exceptions.NovaException;
import nova.history.HistoryManager;
import nova.parser.Parser;
import nova.tasklist.TaskList;

/**
 * The Command class handles user commands by parsing input,
 * interacting with the task list, and managing command history.
 */
public class Command {
    private final TaskList tasklist;
    private final Parser parser;
    private final HistoryManager historyManager;
    private final String notANumberMessage = "ERROR: Please input a number";

    /**
     * Constructs a new Command object with the specified task list,
     * parser, and history manager.
     *
     * @param tasklist       the task list to operate on
     * @param parser         the parser used for processing user input
     * @param historyManager the history manager for undo/redo operations
     */
    public Command(TaskList tasklist, Parser parser, HistoryManager historyManager) {
        this.tasklist = tasklist;
        this.parser = parser;
        this.historyManager = historyManager;
    }

    /**
     * Saves the current state of the task list to the history manager.
     */
    private void saveState() {
        historyManager.saveState(this.tasklist.getTaskArrayList());
    }

    /**
     * Executes the bye command, exiting the application.
     *
     * @return a farewell message
     */
    public String executeBye() {
        javafx.application.Platform.exit();
        return "Bye";
    }

    /**
     * Executes the list command
     *
     * @return the task list as a string
     */
    public String executeList() {
        return this.tasklist.getTaskListString();
    }

    /**
     * Checks whether the provided task index is valid for the current task list.
     *
     * @param taskIndex the index of the task to check
     * @throws NovaException if the index is not within the valid range
     */
    private void checkValidIndex(int taskIndex) throws NovaException {
        if (taskIndex > this.tasklist.size() || taskIndex <= 0) {
            throw new NovaException("ERROR: Invalid task number");
        }
    }

    /**
     * Executes the mark command to mark a task as done.
     *
     * @param index the string representing the task index to mark
     * @return a confirmation message or an error message if the operation fails
     */
    private String executeMark(String index) {
        try {
            //Throws NumberFormatException
            int taskIndex = Integer.parseInt(index);

            //Throws NovaException
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.markTask(taskIndex);
            return "marked task as done OwO";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return notANumberMessage;
        }
    }

    /**
     * Executes the unmark command to mark a task as not done.
     *
     * @param index the string representing the task index to unmark
     * @return a confirmation message or an error message if the operation fails
     */
    private String executeUnMark(String index) {
        try {
            //Throws NumberFormatException
            int taskIndex = Integer.parseInt(index);

            //Throws NovaException
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.unMarkTask(taskIndex);
            return "unmarked your task OwO";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return notANumberMessage;
        }
    }

    /**
     * Executes the find command to search for tasks that match the given description.
     *
     * @param description the description to search for
     * @return a string representing the list of matching tasks
     */
    private String executeFind(String description) {
        return tasklist.findTask(description);
    }

    /**
     * Executes the delete command to remove a task from the task list.
     *
     * @param index the string representing the task index to delete
     * @return a confirmation message or an error message if the operation fails
     */
    private String executeDelete(String index) {
        try {
            //Throws NumberFormatException
            int taskIndex = Integer.parseInt(index);

            // Throws NovaException
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.deleteTask(taskIndex);
            return "deleted your task OwO";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return notANumberMessage;
        }
    }

    /**
     * Executes the add-to-do command to add a new to-do task.
     *
     * @param description the description of the to-do task
     * @return a confirmation message after adding the to-do task
     */
    private String executeAddTodo(String description) {
        saveState();
        tasklist.addToDo(description);
        return "added to-do task " + description + " UwO!";
    }

    /**
     * Executes the add-deadline command to add a new deadline task.
     *
     * @param slashedInput an array containing the description and deadline information
     * @return a confirmation message if the task is added, or an error message if the input is invalid
     */
    private String executeAddDeadline(String[] slashedInput) {
        String nullIfValid = validDeadlineLength(slashedInput);
        if (nullIfValid != null) {
            return nullIfValid;
        }

        String description = slashedInput[0].trim();
        String deadlineDate = slashedInput[1].trim();

        nullIfValid = validDeadlineInput(description, deadlineDate);
        if (nullIfValid != null) {
            return nullIfValid;
        }

        saveState();

        try {
            tasklist.addDeadline(description, deadlineDate);
        } catch (NovaException e) {
            return e.getMessage();
        }

        return "added deadline " + description + " OwU!";
    }

    private String validDeadlineLength(String[] slashedInput) {
        if (slashedInput.length < 2) {
            return "ERROR: Too little arguments";
        }
        return null;
    }

    private String validDeadlineInput(String description, String deadlineDate) {
        if (description.isEmpty() || !deadlineDate.contains("by ")) {
            return "ERROR: Invalid format";
        }
        return null;
    }

    /**
     * Executes the add-event command to add a new event task.
     *
     * @param slashedInput an array containing the description, start time, and end time of the event
     * @return a confirmation message if the event is added, or an error message if the input is invalid
     */
    private String executeAddEvent(String[] slashedInput) {
        String nullIfInvalid = validEventLength(slashedInput);
        if (nullIfInvalid != null) {
            return nullIfInvalid;
        }

        String description = slashedInput[0].trim();
        String from = slashedInput[1].trim();
        String to = slashedInput[2].trim();

        nullIfInvalid = validEventInput(description, from, to);
        if (nullIfInvalid != null) {
            return nullIfInvalid;
        }

        saveState();

        try {
            this.tasklist.addEvent(description, from, to);
        } catch (NovaException e) {
            return e.getMessage();
        }

        return "added event " + description + " :(";
    }

    private static String validEventInput(String description, String from, String to) {
        if (description.isEmpty() || !from.contains("from ") || !to.contains("to ")) {
            return "ERROR: Invalid format";
        }
        return null;
    }

    private static String validEventLength(String[] slashedInput) {
        if (slashedInput.length != 3) {
            return "ERROR: Too little arguments";
        }
        return null;
    }

    /**
     * Executes the undo command to revert the last action.
     *
     * @return a message indicating whether the undo was successful or an error occurred
     */
    public String executeUndo() {
        try {
            tasklist.setTaskArrayList(historyManager.getUndoState(tasklist.getTaskArrayList()));
            return "your action has been undone :)";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Executes the redo command to revert an undo operation.
     *
     * @return a message indicating whether the redo was successful or an error occurred
     */
    public String executeRedo() {
        try {
            tasklist.setTaskArrayList(historyManager.getRedoState(tasklist.getTaskArrayList()));
            return "Your action has been reverted :)";
        } catch (NovaException e) {
            return e.getMessage();
        }
    }

    /**
     * Executes the help command to provide information about available commands.
     *
     * @return a string containing detailed help instructions
     */
    public String executeHelp() {
        String singleCommands = "Single word commands are: \n List, Undo, Redo, Find, Bye, Help\n\n";
        String todoCommand = "Adding a todo task:\ntodo <description>\n\n";
        String deadlineCommand = "Adding a deadline task:\ndeadline <description> /by <YYYY-MM-DD HH:MM>\n\n";
        String eventCommand = """
                Adding a event task:
                event <description> /from <YYYY-MM-DD HH:MM>
                /to <YYYY-MM-DD HH:MM>
                """;
        String disclaimer = "\n!!! Please do not use '/' in your descriptions !!!";

        return singleCommands + todoCommand + deadlineCommand + eventCommand + disclaimer;
    }

    /**
     * Parses and executes a command based on the given input.
     *
     * @param input the user input to parse and execute
     * @return the result of executing the command as a string, or an error message if the command fails
     */
    public String executeCommand(String input) {
        assert !input.isEmpty() : "ERROR: Empty input";

        String[] spacedInput;
        try {
            spacedInput = this.parser.parseBySpace(input); // throw exception if only 1 word
        } catch (NovaException e) {
            return e.getMessage();
        }

        String[] slashedInput = this.parser.splitBySlash(spacedInput[1]);
        switch (spacedInput[0].toLowerCase()) {
        case "mark" -> {
            return executeMark(spacedInput[1]);
        }
        case "unmark" -> {
            return executeUnMark(spacedInput[1]);
        }
        case "find" -> {
            return executeFind(spacedInput[1]);
        }
        case "delete" -> {
            return executeDelete(spacedInput[1]);
        }
        case "todo" -> {
            return executeAddTodo(spacedInput[1]);
        }
        case "deadline" -> {
            return executeAddDeadline(slashedInput);
        }
        case "event" -> {
            return executeAddEvent(slashedInput);
        }
        default -> {
            //do nothing
        }
        }

        //execute
        return "ERROR: I dont know what you are trying to say";
    }
}
