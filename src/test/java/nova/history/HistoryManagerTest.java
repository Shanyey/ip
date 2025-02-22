package nova.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nova.exceptions.NovaException;
import nova.tasks.Task;
import nova.tasks.Todo;

class HistoryManagerTest {

    private HistoryManager historyManager;
    private ArrayList<Task> tasks;

    @BeforeEach
    void setUp() {
        historyManager = new HistoryManager();
        tasks = new ArrayList<>();
    }

    @Test
    public void testSaveStateAndUndoRedo() throws NovaException {
        historyManager.saveState(tasks);

        // Add a task and save the new state.
        tasks.add(new Todo("Task 1", false));
        historyManager.saveState(tasks);

        tasks.add(new Todo("Task 2", false));

        ArrayList<Task> previousState = historyManager.getUndoState(tasks);
        assertEquals(1, previousState.size(), "Undo state should have one task");
        assertEquals("Task 1", previousState.get(0).toString().substring(7).trim(),
                "The undone state should contain 'Task 1'");

        ArrayList<Task> redoState = historyManager.getRedoState(previousState);
        assertEquals(2, redoState.size(), "Redo state should have two tasks");
    }
}
