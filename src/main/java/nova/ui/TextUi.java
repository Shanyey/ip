package nova.ui;

import nova.tasks.Task;
import nova.tasklist.TaskList;

import java.util.ArrayList;

public class TextUi {

    public TextUi() {
    }

    public void printWelcomeMessage() {
        System.out.println("Ughh, what can i do for you?");
    }

    public void printExitMessage() {
        System.out.println("Goodbye! Hope to see you never!");
    }

    public void printUnknownInputMessage() {
        System.out.println("Fam I don't know what you are yapping about");
    }

    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public void printTasksList(TaskList tasks) {
        ArrayList<Task> tasksList = tasks.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks added.");
        } else {
            System.out.println("These are the tasks:");
            for (int i = 0; i < tasksList.size(); i++) {
                System.out.println((i + 1) + "." + tasks.getTask(i));
            }
        }
    }
}
