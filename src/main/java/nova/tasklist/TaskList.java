package nova.tasklist;

import java.util.ArrayList;

import nova.exceptions.NovaException;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;

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
     * Returns all tasks stored as a string
     *
     * @return String format of all tasks stored
     */
    public String getTaskListString() {
        if (tasks.isEmpty()) {
            return "No tasks added";
        }

        StringBuilder response = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            response.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }

        return response.toString();
    }

    /**
     * Retrieves a specific task by its index. todo delete
     *
     * @param index The index of the task (0-based).
     * @return The Task at the specified index.
     */
    public Task getTaskAt(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as completed.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range or the task is already marked as done.
     */
    public void markTask(int index) throws NovaException {
        Task task = tasks.get(index - 1);

        if (task.isDone()) {
            throw new NovaException("task is already done");
        }

        task.setDone();
    }

    /**
     * Unmarks a completed task.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range or the task is already unmarked.
     */
    public void unMarkTask(int index) throws NovaException {
        Task task = tasks.get(index - 1);

        if (!task.isDone()) {
            throw new NovaException("task is already unmarked");
        }

        task.setNotDone();
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

        tasks.remove(index - 1);
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
     */
    public void addDeadline(String description, String deadlineDate) {
        Deadline deadline = new Deadline(description, deadlineDate.replace("by ", ""));
        tasks.add(deadline);
    }

    /**
     * Adds a new Event task to the list.
     */
    public void addEvent(String description, String from, String to) {
        String fromDate = from.replace("from ", "");
        String toDate = to.replace("to ", "");

        Event event = new Event(description, fromDate, toDate);

        tasks.add(event);
    }

    /**
     * Finds all tasks that matches the specified description
     *
     * @param description String used to match with tasks descriptions
     */
    public String findTask(String description) {
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task.descriptionContains(description)) {
                response.append(i + 1).append(". ").append(task).append("\n");
            }
        }

        return response.toString();
    }

}
