import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import Classes.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    private static void printMark(Task task) {
        System.out.println("Wow you actually managed to finish this task");
        System.out.println(task);
    }
    private static void printUnMark(Task task) {
        System.out.println("I knew you wouldn't finish this task");
        System.out.println(task);
    }
    private static void printAddTask(Task task, int tasksSize) {
        System.out.println("I bet you're gonna be to lazy to do it anyway *rolls eyes*" + "\n" + task);
        System.out.println("Now you have " + tasksSize + " tasks in the list.");
    }

    public static void main(String[] args) {
        //initialise variables
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //start loop and get inputs
        System.out.println("Ughh, what can i do for you?");
        while (true) {
            String action = scanner.nextLine();
            if (action.equals("Bye")) {
                System.out.println("Goodbye! Hope to see you never!");
                break;
            } else if (action.equals("List")) {
                System.out.println("These are the tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
            } else {
                String[] splitAction = action.split(" ", 2);
                String[] slashedAction = splitAction[1].split("/");
                String description = slashedAction[0].trim();

                switch (splitAction[0].toLowerCase()) {
                    case "mark" -> {
                        Task task = tasks.get(Integer.parseInt(splitAction[1]) - 1);
                        task.setDone();
                        printMark(task);
                    }
                    case "unmark" -> {
                        Task task = tasks.get(Integer.parseInt(splitAction[1]) - 1);
                        task.setNotDone();
                        printUnMark(task);
                    }
                    case "todo" -> {
                        Todo todo = new Todo(splitAction[1]);
                        tasks.add(todo);
                        printAddTask(todo, tasks.size());
                    }
                    case "deadline" -> {
                        String date = slashedAction[1].replace("by ", "");
                        Deadline deadline = new Deadline(description, date);
                        tasks.add(deadline);
                        printAddTask(deadline, tasks.size());
                    }
                    case "event" -> {
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
            }
        }
    }
}
