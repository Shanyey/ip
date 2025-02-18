package nova.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import nova.exceptions.NovaException;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.Todo;

class StorageTest {
    private static final String FILE_NAME = "taskDataTest.txt";

    @AfterEach
    public void cleanUp() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoadTasks() throws NovaException {
        ArrayList<Task> originalTasks = new ArrayList<>();

        // Create sample tasks
        originalTasks.add(new Todo("Read book", false));
        originalTasks.add(new Deadline("Submit assignment", "2025-12-31 23:59", false));
        originalTasks.add(new Event("Party", "2025-12-31 20:00", "2026-01-01 02:00", false));

        Storage storage = new Storage();
        // Save the tasks to file
        storage.saveTask(originalTasks);

        // Load the tasks from file
        ArrayList<Task> loadedTasks = storage.loadTask();

        // Check that the same number of tasks was loaded
        assertEquals(originalTasks.size(), loadedTasks.size(),
                "Loaded task list size should match the original list size.");

        // Optionally compare the saved data representation of each task
        for (int i = 0; i < originalTasks.size(); i++) {
            String expected = originalTasks.get(i).getSaveData();
            String actual = loadedTasks.get(i).getSaveData();
            assertEquals(expected, actual, "Task at index " + i + " should match after loading.");
        }
    }
}
