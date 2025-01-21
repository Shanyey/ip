import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        String greet = "Hello! I'm Nova" + '\n' + "What can I do for you?";
        String exit = "Bye. Hope to see you again soon!";
        System.out.println(greet);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(exit);
                break;
            } else {
                System.out.println(input);
            }
        }

    }
}
