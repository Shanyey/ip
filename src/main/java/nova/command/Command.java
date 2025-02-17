package nova.command;

import nova.exceptions.NovaException;
import nova.history.HistoryManager;
import nova.parser.Parser;
import nova.tasklist.TaskList;

public class Command {
    private final TaskList tasklist;
    private final Parser parser;
    private final HistoryManager historyManager;

    public Command(TaskList tasklist, Parser parser) {
        this.tasklist = tasklist;
        this.parser = parser;
        this.historyManager = new HistoryManager();
    }

    private void saveState() {
        historyManager.saveState(this.tasklist.getTasks());
    }

    public String executeBye() {
        javafx.application.Platform.exit();
        return "Bye";
    }

    public String executeList() {
        return this.tasklist.getTaskListString();
    }

    private void checkValidIndex(int taskIndex) throws NovaException {
        if (taskIndex > this.tasklist.size() || taskIndex <= 0) {
            throw new NovaException("Invalid task number");
        }
    }

    private String executeMark(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.markTask(taskIndex);

            return "Marked task as done";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Please enter a number";
        }
    }

    private String executeUnMark(String index) {


        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.unMarkTask(taskIndex);

            return "Unmarked Task";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Please enter a number";
        }
    }

    private String executeDelete(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            saveState();
            this.tasklist.deleteTask(taskIndex);

            return "Deleted Task";
        } catch (NovaException e) {
            return e.getMessage();
        }
    }

    private String executeFind(String description) {
        return tasklist.findTask(description);
    }

    private String executeAddTodo(String description) {
        saveState();

        tasklist.addToDo(description);
        return "Added Task";
    }

    private String executeAddDeadline(String[] slashedInput) {
        if (slashedInput.length < 2) {
            return "Too little arguments";
        }

        saveState();
        String description = slashedInput[0].trim();
        String deadlineDate = slashedInput[1].trim();

        if (description.isEmpty() || !deadlineDate.contains("by ")) {
            return "Invalid format";
        }

        tasklist.addDeadline(description, deadlineDate);
        return "Added Deadline";
    }

    private String executeAddEvent(String[] slashedInput) {
        if (slashedInput.length < 3) {
            return "Too little arguments";
        }

        String description = slashedInput[0].trim();
        String from = slashedInput[1].trim();
        String to = slashedInput[2].trim();

        if (description.isEmpty() || !from.contains("from ") || !to.contains("to ")) {
            return "Invalid format";
        }

        saveState();
        this.tasklist.addEvent(description, from, to);
        return "adding event";
    }

    public String executeUndo() {
        try {
            tasklist.setTasks(historyManager.getUndoState(tasklist.getTasks()));
            return "Undo completed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String executeRedo() {
        try {
            tasklist.setTasks(historyManager.getRedoState(tasklist.getTasks()));
            return "Redo completed";
        } catch (NovaException e) {
            return e.getMessage();
        }
    }

    public String executeHelp() {
        String singleCommands = "Single word commands are: \n List, Undo, Redo, Bye, Help\n\n";
        String todoCommand = "Adding a todo task:\ntodo <description>\n\n";
        String deadlineCommand = "Adding a deadline task:\ndeadline <description> /by <YYYY-MM-DD HH:MM>\n\n";
        String eventCommand = "Adding a event task:\nevent <description> /from <YYYY-MM-DD HH:MM> /to <YYYY-MM-DD HH:MM>\n\n";
        return singleCommands + todoCommand + deadlineCommand + eventCommand;
    }

    public String executeCommand(String input) {
        assert !input.isEmpty() : "Empty input";
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
        case "delete" -> {
            return executeDelete(spacedInput[1]);
        }
        case "find" -> {
            return executeFind(spacedInput[1]);
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
        }

        //execute
        return "try catch failed";
    }
}
