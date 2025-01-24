import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import Classes.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String addedToList = "I bet you're gonna be to lazy to do it anyway *rolls eyes*";
        String numTasks1 = "Now you have ";
        String numTasks2 = " tasks in the list.";

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

                switch (splitAction[0].toLowerCase()) {
                    case "mark" -> {
                        Task task = tasks.get(Integer.parseInt(splitAction[1]) - 1);
                        task.setDone();
                        System.out.println("Wow you actually managed to finish this task");
                        System.out.println(task);
                    }
                    case "unmark" -> {
                        Task task = tasks.get(Integer.parseInt(splitAction[1]) - 1);
                        task.setNotDone();
                        System.out.println("I knew you wouldn't finish this task");
                        System.out.println(task);
                    }
                    case "todo" -> {
                        Todo todo = new Todo(splitAction[1]);
                        tasks.add(todo);
                        System.out.println(addedToList + "\n" + todo);
                        System.out.println(numTasks1 + tasks.size() + numTasks2);
                    }
                    case "deadline" -> {
                        String[] slashedAction = splitAction[1].split("/"); // ["desc ", "by Sun"]
                        String description = slashedAction[0].trim();
                        String date = slashedAction[1].replace("by ", "");
                        Deadline deadline = new Deadline(description, date);
                        // gives "desc" and "Sun"
                        tasks.add(deadline);
                        System.out.println(addedToList + "\n" + deadline);
                        System.out.println(numTasks1 + tasks.size() + numTasks2);
                    }
                    case "event" -> {
                        //["desc ", "from 2pm ", "to 4pm"]
                        String[] slashedAction = splitAction[1].split("/");
                        String description = slashedAction[0].trim();
                        String from = slashedAction[1].replace("from ", "").trim();
                        String to = slashedAction[2].replace("to ", "");
                        Event event = new Event(description, from, to);
                        tasks.add(event);
                        System.out.println(addedToList + "\n" + event);
                        System.out.println(numTasks1 + tasks.size() + numTasks2);
                    }
                    default -> {
                        System.out.println("Fam I don't know what you are yapping about");
                    }
                }
            }
        }
    }
}
