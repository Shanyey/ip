package nova.storage;

<<<<<<< HEAD

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
=======
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

>>>>>>> A-CodingStandard
import java.util.ArrayList;

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
<<<<<<< HEAD
//        try {
//            FileWriter fileWriter = new FileWriter(fileName);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            for (Task task : tasks) {
//                bufferedWriter.write(task.getSaveData() + "\n");
//            }
//            bufferedWriter.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
=======
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
>>>>>>> A-CodingStandard
            for (Task task : tasks) {
                bufferedWriter.write(task.getSaveData() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
<<<<<<< HEAD
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
=======
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
>>>>>>> A-CodingStandard
            while ((line = bufferedReader.readLine()) != null) {
                boolean isDone = line.charAt(4) == 'X';
                switch (line.charAt(1)) {
                case 'T' -> tasks.add(new Todo(line.substring(7), isDone));
                case 'D' -> {
                    int bracketIndex = line.indexOf("(by:");
                    String desc = line.substring(7, bracketIndex).trim();
                    int endBracketIndex = line.indexOf(')');
                    String by = line.substring(bracketIndex + 1, endBracketIndex).replace("by:", "").trim();
                    Deadline deadline = new Deadline(desc, by, isDone);
                    tasks.add(deadline);
                }
                case 'E' -> {
                    int startBracketIndex = line.indexOf('(');
                    int endBracketIndex = line.indexOf(')');
                    int toIndex = line.indexOf("to:");
                    String desc = line.substring(7, startBracketIndex).trim();
                    String from = line.substring(startBracketIndex + 1, toIndex).replace("from:", "").trim();
                    String to = line.substring(toIndex, endBracketIndex).replace("to:", "").trim();
                    tasks.add(new Event(desc, from, to, isDone));
                }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        try {
//            FileReader fileReader = new FileReader(fileName);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            while ((line = bufferedReader.readLine()) != null) {
//                boolean isDone = line.charAt(4) == 'X';
//                switch (line.charAt(1)) {
//                case 'T' -> tasks.add(new Todo(line.substring(7), isDone));
//                case 'D' -> {
//                    int bracketIndex = line.indexOf("(by:");
//                    String desc = line.substring(7, bracketIndex).trim();
//                    int endBracketIndex = line.indexOf(')');
//                    String by = line.substring(bracketIndex + 1, endBracketIndex).replace("by:", "").trim();
//                    Deadline deadline = new Deadline(desc, by, isDone);
//                    tasks.add(deadline);
//                }
//                case 'E' -> {
//                    int startBracketIndex = line.indexOf('(');
//                    int endBracketIndex = line.indexOf(')');
//                    int toIndex = line.indexOf("to:");
//                    String desc = line.substring(7, startBracketIndex).trim();
//                    String from = line.substring(startBracketIndex + 1, toIndex).replace("from:", "").trim();
//                    String to = line.substring(toIndex, endBracketIndex).replace("to:", "").trim();
//                    tasks.add(new Event(desc, from, to, isDone));
//                }
//                }
//            }
//            bufferedReader.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return tasks;
    }
}
