package nova.tasklist;

import java.util.ArrayList;

import nova.exceptions.NovaException;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;

/**
 * Manages a list of taskArrayList, providing methods to add, delete, and modify taskArrayList.
 * This class supports different task types such as To-do, Deadline, and Event.
 * It also allows marking and unmarking taskArrayList as completed.
 */
public class TaskList {
    private final ArrayList<Task> taskArrayList;

    /**
     * Constructs a TaskList with an existing list of taskArrayList.
     *
     * @param taskArrayList The list of taskArrayList to be managed.
     */
    public TaskList(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if there are no taskArrayList, false otherwise.
     */
    public boolean isEmpty() {
        return taskArrayList.isEmpty();
    }

    /**
     * Returns the number of taskArrayList in the list.
     *
     * @return The total number of taskArrayList.
     */
    public int size() {
        return taskArrayList.size();
    }

    /**
     * Returns the list of taskArrayList.
     *
     * @return An ArrayList of taskArrayList.
     */
    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    /**
     * Returns all taskArrayList stored as a string
     *
     * @return String format of all taskArrayList stored
     */
    public String getTaskListString() {
        if (taskArrayList.isEmpty()) {
            return "No taskArrayList added";
        }

        StringBuilder response = new StringBuilder();

        for (int i = 0; i < taskArrayList.size(); i++) {
            response.append(i + 1).append(". ").append(taskArrayList.get(i)).append("\n");
        }

        return response.toString();
    }

    /**
     * Marks a task as completed.
     *
     * @param index The index of the task.
     * @throws NovaException If the index is out of range or the task is already marked as done.
     */
    public void markTask(int index) throws NovaException {
        Task task = taskArrayList.get(index - 1);

        if (task.isDone()) {
            throw new NovaException("ERROR: task is already done");
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
        Task task = taskArrayList.get(index - 1);

        if (!task.isDone()) {
            throw new NovaException("ERROR: task is already unmarked");
        }

        task.setNotDone();
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task.
     */
    public void deleteTask(int index) {
        taskArrayList.remove(index - 1);
    }

    /**
     * Adds a new To-do task to the list.
     *
     * @param desc The description of the To-do task.
     */
    public void addToDo(String desc) {
        Todo todo = new Todo(desc, false);
        taskArrayList.add(todo);
    }

    /**
     * Adds a new Deadline task to the list.
     */
    public void addDeadline(String description, String deadlineDate) {
        Deadline deadline = new Deadline(description, deadlineDate.replace("by ", ""));
        taskArrayList.add(deadline);
    }

    /**
     * Adds a new Event task to the list.
     */
    public void addEvent(String description, String from, String to) {
        String fromDate = from.replace("from ", "");
        String toDate = to.replace("to ", "");

        Event event = new Event(description, fromDate, toDate);

        taskArrayList.add(event);
    }

    /**
     * Finds all taskArrayList that matches the specified description
     *
     * @param description String used to match with taskArrayList descriptions
     */
    public String findTask(String description) {
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < taskArrayList.size(); i++) {
            addTaskIfMatch(description, i, response);
        }

        return response.toString();
    }

    private void addTaskIfMatch(String description, int i, StringBuilder response) {
        Task task = taskArrayList.get(i);
        if (task.descriptionContains(description)) {
            response.append(i + 1).append(". ").append(task).append("\n");
        }
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskArrayList.clear();
        this.taskArrayList.addAll(taskArrayList);
    }
}
