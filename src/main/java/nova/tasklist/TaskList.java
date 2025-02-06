package nova.tasklist;

import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;
import nova.exceptions.NovaException;
import nova.ui.TextUi;

import java.util.ArrayList;

/**
 * Manages a list of tasks, providing methods to add, delete, and modify tasks.
 * This class supports different task types such as To-do, Deadline, and Event.
 * It also allows marking and unmarking tasks as completed.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The list of tasks to be managed.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if there are no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of tasks.
     *
     * @return An ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Retrieves a specific task by its index.
     *
     * @param index The index of the task (0-based).
     * @return The Task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as completed.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range or the task is already marked as done.
     */
    public void markTask(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.get(index - 1);
        if (task.isDone()) {
            throw new NovaException("task is already done");
        } else {
            task.setDone();
            System.out.println("Marked " + task);
        }
    }

    /**
     * Unmarks a completed task.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range or the task is already unmarked.
     */
    public void unMarkTask(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.get(index - 1);
        if (!task.isDone()) {
            throw new NovaException("task is already unmarked");
        } else {
            task.setNotDone();
            System.out.println("Unmarked " + task);
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range.
     */
    public void deleteTask(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.remove(index - 1);
        System.out.println("I knew you wouldn't do this task" + "\n" + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Adds a new To-do task to the list.
     *
     * @param desc The description of the To-do task.
     */
    public void addToDo(String desc) {
        Todo todo = new Todo(desc, false);
        tasks.add(todo);
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param slashedAction A string array where the first element is the description
     *                      and the second is the deadline (formatted as "by YYYY-MM-DD HH:mm").
     * @throws NovaException If the format is invalid or missing required arguments.
     */
    public void addDeadline(String[] slashedAction) throws NovaException {
        if (slashedAction.length < 2) {
            throw new NovaException("too little arguments");
        }
        String desc = slashedAction[0].trim();
        String deadlineDate = slashedAction[1].trim();
        if (desc.isEmpty() || !deadlineDate.contains("by ")) {
            throw new NovaException("invalid format");
        }
        Deadline deadline = new Deadline(desc, deadlineDate.replace("by ", ""));
        tasks.add(deadline);
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param slashedAction A string array where:
     *                      - The first element is the description.
     *                      - The second is the start date (formatted as "from YYYY-MM-DD HH:mm").
     *                      - The third is the end date (formatted as "to YYYY-MM-DD HH:mm").
     * @throws NovaException If the format is invalid or missing required arguments.
     */
    public void addEvent(String[] slashedAction) throws NovaException {
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
    }

    /**
     * Finds all tasks that matches the specified description
     *
     * @param description String used to match with tasks descriptions
     */
    public void findTask(String description, TextUi textUi) {
        ArrayList<Task> arr = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(description)) {
                arr.add(task);
            }
        }
        textUi.printFoundTasks(arr);
    }
}
