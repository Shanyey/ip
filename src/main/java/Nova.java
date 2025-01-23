import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        //initialise variables
        ArrayList<Task> array = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String addedToList = "Got it. I've added this task:";

        System.out.println("Hello! I'm Nova" + '\n' + "What can I do for you?");
        while (true) {
            String input = scanner.nextLine();
            input = input.toLowerCase();

            /* exit out of chat */
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) { // print out all tasks
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < array.size(); i++) {
                    System.out.println((i+1) + "." + array.get(i));
                }
            } else { //check action
                String[] words = input.split(" ");
                switch (words[0]) {
                    case "mark" -> {
                        array.get(Integer.parseInt(words[1]) - 1).done();
                        System.out.println("Nice! I've marked this task as done:" + '\n' + array.get(Integer.parseInt(words[1]) - 1));
                    }
                    case "unmark" -> {
                        array.get(Integer.parseInt(words[1]) - 1).undone();
                        System.out.println("OK, I've marked this task as not done yet:" + '\n' + array.get(Integer.parseInt(words[1]) - 1));
                    }
                    case "deadline" -> {
                        String[] words2 = input.split("/");
                        String deadline = words2[1].replaceAll("by ", "");
                        String task = words2[0].replace("deadline ", "");
                        Deadline activity = new Deadline(task, deadline);
                        array.add(activity);
                        System.out.println(addedToList + '\n' + activity);
                        System.out.println("Now you have " + array.size() + " tasks in the list.");
                    }
                    case "todo" -> {
                        Todo task = new Todo(input.replace("todo ", ""));
                        array.add(task);
                        System.out.println(addedToList + '\n' + task);
                        System.out.println("Now you have " + array.size() + " tasks in the list.");
                    }
                    case "event" -> {
                        String[] words2 = input.split("/");
                        String task = words2[0].replace("event ", "");
                        String startingDate = words2[1].replace("from ", "");
                        String deadline = words2[2].replace("to ", "");
                        Event event = new Event(task, startingDate, deadline);
                        array.add(event);
                        System.out.println(addedToList + '\n' + event);
                        System.out.println("Now you have " + array.size() + " tasks in the list.");
                    }
                    default -> {
                        System.out.println("Invalid input");
                    }
                }
            }
        }

    }
}
