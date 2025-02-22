package nova.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import nova.exceptions.NovaException;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;

/**
 * Handles the storage and retrieval of tasks from a file.
 * This class provides methods to save tasks to a file and load tasks from a saved file.
 * Tasks are stored in "taskData.txt" and retrieved when the program starts.
 */
public class Storage {
    private static final String FILE_NAME = "taskData.txt";

    /**
     * Saves the list of tasks to a file.
     * Each task is written in a format that can be later parsed when loading.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void saveTask(ArrayList<Task> tasks) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writeToFile(tasks, bufferedWriter);
        } catch (IOException e) {
            System.out.println("Saving error: " + e.getMessage());
        }
    }

    /**
     * Writes the save data of each task in the provided list to the given BufferedWriter.
     *
     * @param tasks the list of tasks whose data will be written to the file
     * @param bufferedWriter the writer used to output the task data
     * @throws IOException if an I/O error occurs during writing
     */
    private void writeToFile(ArrayList<Task> tasks, BufferedWriter bufferedWriter) throws IOException {
        for (Task task : tasks) {
            bufferedWriter.write(task.getSaveData() + "\n");
        }
    }

    /**
     * Loads tasks from the file and reconstructs them into an ArrayList.
     * If the file does not exist or cannot be read, an empty list is returned.
     *
     * @return An ArrayList containing task objects loaded from the file.
     */
    public ArrayList<Task> loadTask() {
        String line;
        ArrayList<Task> tasks = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            while ((line = bufferedReader.readLine()) != null) {
                boolean isDone = line.charAt(4) == 'X';

                switch (line.charAt(1)) {
                case 'T' -> loadTodoTask(tasks, line, isDone);
                case 'D' -> loadDeadlineTask(line, isDone, tasks);
                case 'E' -> loadEventTask(line, tasks, isDone);
                default -> {
                    //do nothing
                }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    /**
     * Loads an event task from the given line and adds it to the specified task list.
     *
     * @param line the line containing the event task data
     * @param tasks the list of tasks to which the event will be added
     * @param isDone the completion status of the event task
     */
    private void loadEventTask(String line, ArrayList<Task> tasks, boolean isDone) {
        int startBracketIndex = line.indexOf("(from: ");
        int endBracketIndex = line.indexOf(')', startBracketIndex);
        int toIndex = line.indexOf("to: ");

        String desc = getTaskDesc(line, startBracketIndex);
        String from = getEventFrom(line, startBracketIndex, toIndex);
        String to = getEventTo(line, toIndex, endBracketIndex);

        try {
            tasks.add(new Event(desc, from, to, isDone));
        } catch (NovaException e) {
            System.out.println("This should not happen");
        }
    }

    /**
     * Extracts the start time for an event from the given line.
     *
     * @param line the line containing the event details
     * @param startBracketIndex the index where the event detail block starts (containing "from:")
     * @param toIndex the index where the "to:" substring begins
     * @return the extracted start time for the event
     */
    private String getEventFrom(String line, int startBracketIndex, int toIndex) {
        return line.substring(startBracketIndex + 1, toIndex).replace("from:", "").trim();
    }

    /**
     * Extracts the end time for an event from the given line.
     *
     * @param line the line containing the event details
     * @param toIndex the index where the "to:" substring starts
     * @param endBracketIndex the index of the closing bracket for the event details
     * @return the extracted end time for the event
     */
    private String getEventTo(String line, int toIndex, int endBracketIndex) {
        return line.substring(toIndex, endBracketIndex).replace("to:", "").trim();
    }

    /**
     * Loads a deadline task from the given line and adds it to the specified task list.
     *
     * @param line the line containing the deadline task data
     * @param isDone the completion status of the deadline task
     * @param tasks the list of tasks to which the deadline will be added
     */
    private static void loadDeadlineTask(String line, boolean isDone, ArrayList<Task> tasks) {
        int bracketIndex = line.indexOf("(by:");
        int endBracketIndex = line.indexOf(')');

        String desc = getTaskDesc(line, bracketIndex);
        String by = getDeadlineBy(line, bracketIndex, endBracketIndex);

        Deadline deadline = new Deadline(desc, by, isDone);
        tasks.add(deadline);
    }

    /**
     * Extracts the deadline time from a deadline task line.
     *
     * @param line the line containing the deadline task data
     * @param bracketIndex the index where the deadline details start (containing "by:")
     * @param endBracketIndex the index of the closing bracket for the deadline details
     * @return the extracted deadline time
     */
    private static String getDeadlineBy(String line, int bracketIndex, int endBracketIndex) {
        return line.substring(bracketIndex + 1, endBracketIndex).replace("by:", "").trim();
    }

    /**
     * Extracts the task description from the given line, stopping at the specified bracket index.
     *
     * @param line the line containing the task data
     * @param bracketIndex the index where task details (e.g., date/time info) begin
     * @return the extracted task description
     */
    private static String getTaskDesc(String line, int bracketIndex) {
        return line.substring(7, bracketIndex).trim();
    }


    /**
     * Loads a to-do task from the given line and adds it to the specified task list.
     *
     * @param tasks the list of tasks to which the to-do task will be added
     * @param line the line containing the to-do task data
     * @param isDone the completion status of the to-do task
     */
    private static void loadTodoTask(ArrayList<Task> tasks, String line, boolean isDone) {
        tasks.add(new Todo(line.substring(7), isDone));
    }
}
