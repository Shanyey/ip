import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Nova" + '\n' + "What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        String[] list = new String[100];
        int i = 0;
        while (true) {
            String input = scanner.nextLine();
            /* exit out of chat */
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                for (int j = 0; j < i; j++) {
                    System.out.println(j+1 + ". " + list[j]);
                }
            } else {
                list[i] = input;
                i += 1;
                System.out.println("added: "+ input);
            }
        }

    }
}
