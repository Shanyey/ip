package nova.storage;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.ArrayList;

import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;

public class Storage {
    private static final String FILE_NAME = "taskData.txt";

    public void saveTask(ArrayList<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Task task : tasks) {
                bufferedWriter.write(task.getSaveData() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("saved tasks");
    }

    /**
     * @return ArrayList containing task objects
     */
    public ArrayList<Task> loadTask() {
        String line;
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
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
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
}
