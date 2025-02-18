package nova.command;

import nova.exceptions.NovaException;
import nova.history.HistoryManager;
import nova.parser.Parser;
import nova.tasklist.TaskList;

public class Command {
    private final TaskList tasklist;
    private final Parser parser;
    private final HistoryManager historyManager;
    private final String notANumberMessage = "ERROR: Please input a number";

    public Command(TaskList tasklist, Parser parser, HistoryManager historyManager) {
        this.tasklist = tasklist;
        this.parser = parser;
        this.historyManager = historyManager;
    }

    private void saveState() {
        historyManager.saveState(this.tasklist.getTaskArrayList());
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
            throw new NovaException("ERROR: Invalid task number");
        }
    }

    private String executeMark(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
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

    private String executeUnMark(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
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

    private String executeFind(String description) {
        return tasklist.findTask(description);
    }

    private String executeDelete(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
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

    private String executeAddTodo(String description) {
        saveState();

        tasklist.addToDo(description);
        return "added to-do task " + description + " UwO!";
    }

    private String executeAddDeadline(String[] slashedInput) {
        if (slashedInput.length < 2) {
            return "ERROR: Too little arguments";
        }

        saveState();
        String description = slashedInput[0].trim();
        String deadlineDate = slashedInput[1].trim();

        if (description.isEmpty() || !deadlineDate.contains("by ")) {
            return "ERROR: Invalid format";
        }

        try {
            tasklist.addDeadline(description, deadlineDate);
        } catch (NovaException e) {
            return e.getMessage();
        }

        return "added deadline " + description + " OwU!";
    }

    private String executeAddEvent(String[] slashedInput) {
        if (slashedInput.length != 3) {
            return "ERROR: Too little arguments";
        }

        String description = slashedInput[0].trim();
        String from = slashedInput[1].trim();
        String to = slashedInput[2].trim();

        if (description.isEmpty() || !from.contains("from ") || !to.contains("to ")) {
            return "ERROR: Invalid format";
        }

        saveState();

        try {
            this.tasklist.addEvent(description, from, to);
        } catch (NovaException e) {
            return e.getMessage();
        }

        return "added event " + description + " :("    ;
    }

    public String executeUndo() {
        try {
            tasklist.setTaskArrayList(historyManager.getUndoState(tasklist.getTaskArrayList()));
            return "your action has been undone :)";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String executeRedo() {
        try {
            tasklist.setTaskArrayList(historyManager.getRedoState(tasklist.getTaskArrayList()));
            return "Redo completed";
        } catch (NovaException e) {
            return e.getMessage();
        }
    }

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
        }

        //execute
        return "try catch failed";
    }
}
