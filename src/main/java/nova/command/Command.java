package nova.command;

import nova.exceptions.NovaException;
import nova.parser.Parser;
import nova.tasklist.TaskList;

public class Command {
    TaskList tasklist;
    Parser parser;

    public Command(TaskList tasklist, Parser parser) {
        this.tasklist = tasklist;
        this.parser = parser;
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

    public String executeMark(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            this.tasklist.markTask(taskIndex);

            return "Marked task as done";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Please enter a number";
        }
    }



    public String executeUnMark(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            this.tasklist.unMarkTask(taskIndex);

            return "Unmarked Task";
        } catch (NovaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Please enter a number";
        }
    }

    public String executeDelete(String index) {
        try {
            int taskIndex = Integer.parseInt(index);
            checkValidIndex(taskIndex);

            this.tasklist.deleteTask(taskIndex);

            return "Deleted Task";
        } catch (NovaException e) {
            return e.getMessage();
        }
    }

    public String executeFind(String description) {
        return tasklist.findTask(description);
    }

    public String executeAddTodo(String description) {
        tasklist.addToDo(description);
        return "Added Task";
    }

    public String executeAddDeadline(String[] slashedInput) {
        if (slashedInput.length < 2) {
            return "Too little arguments";
        }

        String description = slashedInput[0].trim();
        String deadlineDate = slashedInput[1].trim();

        if (description.isEmpty() || !deadlineDate.contains("by ")) {
            return "Invalid format";
        }

        tasklist.addDeadline(description, deadlineDate);
        return "Added Deadline";
    }

    public String executeAddEvent(String[] slashedInput) {
        if (slashedInput.length < 3) {
            return "Too little arguments";
        }

        String description = slashedInput[0].trim();
        String from = slashedInput[1].trim();
        String to = slashedInput[2].trim();

        if (description.isEmpty() || !from.contains("from ") || !to.contains("to ")) {
            return "Invalid format";
        }
        this.tasklist.addEvent(description, from, to);
        return "adding event";
    }

    public String executeCommand(String input) {
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
