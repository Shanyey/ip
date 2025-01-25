import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import Classes.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Task> tasks = new ArrayList<>();


    private static void printAddTask(Task task, int tasksSize) {
        System.out.println("I bet you're gonna be to lazy to do it anyway *rolls eyes*" + "\n" + task);
        System.out.println("Now you have " + tasksSize + " tasks in the list.");
    }

    //handles invalid 1 word inputs
    private static String[] parseAction(String action) throws NovaException {
        action = action.trim();
        String[] splitAction = action.split(" ", 2);
        if (splitAction.length == 1) {
            throw new NovaException("too little arguments or invalid command");
        }
        return splitAction;
    }

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

    public static void main(String[] args) {
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
            } else {
                try {
                    String[] splitAction = parseAction(action);
                    String[] slashedAction = splitAction[1].split("/");
                    String description = slashedAction[0].trim();

                    switch (splitAction[0].toLowerCase()) {
                        case "mark" -> {
                            executeMark(Integer.parseInt(splitAction[1]));
                        }
                        case "unmark" -> {
                            executeUnMark(Integer.parseInt(splitAction[1]));
                        }
                        case "todo" -> {
                            //replace with executeToDo
                            Todo todo = new Todo(splitAction[1]);
                            tasks.add(todo);
                            printAddTask(todo, tasks.size());
                        }
                        case "deadline" -> {
                            //replace with execute deadline
                            String date = slashedAction[1].replace("by ", "");
                            Deadline deadline = new Deadline(description, date);
                            tasks.add(deadline);
                            printAddTask(deadline, tasks.size());
                        }
                        case "event" -> {
                            //replace with execute event
                            String from = slashedAction[1].replace("from ", "").trim();
                            String to = slashedAction[2].replace("to ", "");
                            Event event = new Event(description, from, to);
                            tasks.add(event);
                            printAddTask(event, tasks.size());
                        }
                        default -> {
                            System.out.println("Fam I don't know what you are yapping about");
                        }
                    }
                } catch (NovaException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
