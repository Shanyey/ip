package nova.tasklist;

import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;
import nova.exceptions.NovaException;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

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

    public void deleteTask(int index) throws NovaException {
        if (index > tasks.size() || index <= 0) {
            throw new NovaException("invalid task number");
        }
        Task task = tasks.remove(index - 1);
        System.out.println("I knew you wouldn't do this task" + "\n" + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    public void addToDo(String desc) {
        Todo todo = new Todo(desc, false);
        tasks.add(todo);
        //todo print added msg
    }

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
        //todo print added task
    }

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
        //todo print added msg
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }


}
