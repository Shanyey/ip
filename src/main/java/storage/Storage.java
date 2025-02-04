package storage;

import java.io.*;
import java.util.ArrayList;

import classes.Deadline;
import classes.Event;
import classes.Task;
import classes.Todo;

public class Storage {
    private static final String fileName = "taskData.txt";

    public void saveTask(ArrayList<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Task task : tasks) {
                bufferedWriter.write(task.getSaveData() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            FileReader fileReader = new FileReader(fileName);
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
