package nova.history;

import java.util.ArrayList;
import java.util.Stack;

import nova.exceptions.NovaException;
import nova.tasks.Task;

/**
 * Manages the history of task states to enable undo and redo operations.
 */
public class HistoryManager {
    private final Stack<ArrayList<Task>> undoStack;
    private final Stack<ArrayList<Task>> redoStack;

    /**
     * Constructs a new HistoryManager with empty undo and redo stacks.
     */
    public HistoryManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Saves the current state of tasks into the undo stack and clears the redo stack.
     *
     * @param currentState the current list of tasks to be saved as the new state
     */
    public void saveState(ArrayList<Task> currentState) {
        undoStack.push(deepCopy(currentState));
        redoStack.clear();
    }

    /**
     * Retrieves the previous state from the undo stack for an undo operation.
     *
     * @param currentState the current list of tasks before undoing
     * @return the previous state of tasks from the undo stack
     * @throws NovaException if there is no state available to undo
     */
    public ArrayList<Task> getUndoState(ArrayList<Task> currentState) throws NovaException {
        if (undoStack.isEmpty()) {
            throw new NovaException("ERROR: Nothing to undo");
        }

        redoStack.push(deepCopy(currentState));
        return undoStack.pop();
    }

    /**
     * Retrieves the next state from the redo stack for a redo operation.
     *
     * @param currentState the current list of tasks before redoing
     * @return the next state of tasks from the redo stack
     * @throws NovaException if there is no state available to redo
     */
    public ArrayList<Task> getRedoState(ArrayList<Task> currentState) throws NovaException {
        if (redoStack.isEmpty()) {
            throw new NovaException("ERROR: Nothing to redo");
        }

        undoStack.push(deepCopy(currentState));
        return redoStack.pop();
    }

    /**
     * Creates a deep copy of the provided task list.
     *
     * @param original the original list of tasks to copy
     * @return a deep copy of the original task list
     */
    private ArrayList<Task> deepCopy(ArrayList<Task> original) {
        ArrayList<Task> copy = new ArrayList<>();

        for (Task t : original) {
            copy.add(t.clone());
        }

        return copy;
    }
}
