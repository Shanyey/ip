import java.util.ArrayList;
import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        //initialise variables
        ArrayList<Task> array = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Nova" + '\n' + "What can I do for you?");
        while (true) {
            String input = scanner.nextLine();
            input = input.toLowerCase();

            /* exit out of chat */
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) { /* print out all tasks*/
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < array.size(); i++) {
                    System.out.println((i+1) + "." + array.get(i));
                }
            } else { //check action
                String[] words = input.split(" ");
                if (words[0].equals("mark")) { // check for mark
                    array.get(Integer.parseInt(words[1]) - 1).done();
                    System.out.println("Nice! I've marked this task as done:" + '\n' + array.get(Integer.parseInt(words[1]) - 1));
                } else if (words[0].equals("unmark")) {// check for unmark
                    array.get(Integer.parseInt(words[1]) - 1).undone();
                    System.out.println("OK, I've marked this task as not done yet:" + '\n' + array.get(Integer.parseInt(words[1]) - 1));
                } else {//add task to list
                    array.add(new Task(input));
                    System.out.println("added: " + input);
                }
            }
        }

    }
}
