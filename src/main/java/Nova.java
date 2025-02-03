import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import Classes.Todo;
import storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static Storage storage;

    //add to task list
    private static void printAddTask(Task task, int tasksSize) {
        System.out.println("I bet you're gonna be to lazy to do it anyway *rolls eyes*" + "\n" + task);
        System.out.println("Now you have " + tasksSize + " tasks in the list.");
    }

    //add to parser
    private static String[] parseAction(String action) throws NovaException {
        action = action.trim();
        String[] splitAction = action.split(" ", 2);
        if (splitAction.length == 1) {
            throw new NovaException("too little arguments or invalid command");
        }
        return splitAction;
    }

    //add to task list
    private static void executeMark(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.get(index - 1);
        if (task.isDone()) {
            throw new NovaException("task is already done");
        } else {
            task.setDone();
            System.out.println("Wow you actually managed to finish this task" + "\n" + task);
        }
    }

    //add to task list
    private static void executeUnMark(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.get(index - 1);
        if (!task.isDone()) {
            throw new NovaException("task is already unmarked");
        } else {
            task.setNotDone();
            System.out.println("I knew you wouldn't finish this task" + "\n" + task);
        }
    }

    //add to task list
    private static void executeDelete(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.get(index - 1);
        tasks.remove(index - 1);
        System.out.println("I knew you wouldn't do this task" + "\n" + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    //add to task list
    private static void addToDo(String desc) {
        Todo todo = new Todo(desc, false);
        tasks.add(todo);
        printAddTask(todo, tasks.size());
    }

    //add to task list
    private static void addDeadline(String[] slashedAction) throws NovaException {
        if (slashedAction.length < 2) {
            throw new NovaException("too little arguments");
        }
        String desc = slashedAction[0].trim();
        String deadline = slashedAction[1].trim();
        if (desc.isEmpty() || !deadline.contains("by ")) {
            throw new NovaException("invalid format");
        }
        Deadline task = new Deadline(desc, deadline.replace("by ", ""));
        tasks.add(task);
        printAddTask(task, tasks.size());
    }

    //add to task list
    private static void addEvent(String[] slashedAction) throws NovaException {
        if (slashedAction.length < 3) {
            throw new NovaException("too little arguments");
        }
        String desc = slashedAction[0].trim();
        String from = slashedAction[1].trim();
        String to = slashedAction[2].trim();
        if (desc.isEmpty() || !from.contains("from ") || !to.contains("to ")) {
            throw new NovaException("invalid format");
        }
        Event event = new Event(desc, from.replace("from ", ""), to.replace("to ", ""));
        tasks.add(event);
        printAddTask(event, tasks.size());
    }

    public static void main(String[] args) {
        storage = new Storage();

        try {
            storage.loadTask(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //start loop and get inputs
        System.out.println("Ughh, what can i do for you?");
        while (true) {
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye! Hope to see you never!");
                break;
            } else if (action.equalsIgnoreCase("list")) {
                System.out.println("These are the tasks:");
                if (tasks.isEmpty()) {
                    System.out.println("No tasks added.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
                continue;
            } else {
                try {
                    String[] splitAction = parseAction(action); //[ type, desc + others]
                    String[] slashedAction = splitAction[1].split("/"); //[desc, by or start, to]
                    switch (splitAction[0].toLowerCase()) {
                        case "mark" -> executeMark(Integer.parseInt(splitAction[1]));
                        case "unmark" -> executeUnMark(Integer.parseInt(splitAction[1]));
                        case "delete" -> executeDelete(Integer.parseInt(splitAction[1]));
                        case "todo" -> addToDo(splitAction[1]);
                        case "deadline" -> addDeadline(slashedAction);
                        case "event" -> addEvent(slashedAction);
                        default -> System.out.println("Fam I don't know what you are yapping about");
                    }
                } catch (NovaException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
            storage.saveTask(tasks);
        }
    }
}
