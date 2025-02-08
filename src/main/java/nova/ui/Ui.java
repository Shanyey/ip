package nova.ui;

import java.util.ArrayList;

import nova.tasklist.TaskList;
import nova.tasks.Task;


/**
 * The TextUi class handles user interface interactions by printing messages
 * and task lists to the console.
 */
public class Ui {

    /**
     * Prints the welcome message to the user.
     */
    public void printWelcomeMessage() {
        System.out.println("Ughh, what can i do for you?");
    }

    /**
     * Prints the exit message when the user quits the application.
     */
    public void printExitMessage() {
        System.out.println("Goodbye! Hope to see you never!");
    }

    /**
     * Prints a message indicating that the user input is unknown.
     */
    public void printUnknownInputMessage() {
        System.out.println("Fam I don't know what you are yapping about");
    }

    /**
     * Prints an error message based on the provided exception.
     *
     * @param e The exception whose message is to be printed.
     */
    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    /**
     * Prints the list of tasks currently stored in the task list.
     *
     * @param tasks The TaskList containing tasks to be printed.
     */
    public void printTasksList(TaskList tasks) {
        ArrayList<Task> tasksList = tasks.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks added.");
        } else {
            System.out.println("These are the tasks:");
            for (int i = 0; i < tasksList.size(); i++) {
                System.out.println((i + 1) + "." + tasks.getTaskAt(i));
            }
        }
    }

    /**
     * Prints a list of tasks found based on a search query.
     *
     * @param tasks The list of tasks that match the search criteria.
     */
    public void printFoundTasks(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}
