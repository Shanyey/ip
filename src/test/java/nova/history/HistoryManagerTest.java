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
        // Same empty state
        historyManager.saveState(tasks);

        // Add a task and save the new state.
        tasks.add(new Todo("Task 1", false));
        historyManager.saveState(tasks);

        // Modify tasks further by adding another task so it has 2 tasks
        tasks.add(new Todo("Task 2", false));

        // Undo: get previous state; this should be the state with only "Task 1".
        ArrayList<Task> previousState = historyManager.getUndoState(tasks);
        assertEquals(1, previousState.size(), "Undo state should have one task");
        assertEquals("Task 1", previousState.get(0).toString().substring(7).trim(),
                "The undone state should contain 'Task 1'");

        // The getUndoState call pushes a deep copy of the current state onto the redo stack.
        // So now, calling redo on the previous state should restore the state with 2 tasks.
        ArrayList<Task> redoState = historyManager.getRedoState(previousState);
        assertEquals(2, redoState.size(), "Redo state should have two tasks");
    }
}
