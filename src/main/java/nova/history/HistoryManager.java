package nova.history;

import java.util.Stack;
import java.util.ArrayList;

import nova.tasks.Task;
import nova.exceptions.NovaException;

public class HistoryManager {
    private final Stack<ArrayList<Task>> undoStack;
    private final Stack<ArrayList<Task>> redoStack;

    public HistoryManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    //to be run after every action
    public void saveState(ArrayList<Task> currentState) {
        undoStack.push(deepCopy(currentState));
        redoStack.clear();
    }

    public ArrayList<Task> getUndoState(ArrayList<Task> currentState) throws NovaException {
        if (undoStack.isEmpty()) {
            throw new NovaException("ERROR: Nothing to undo");
        }

        redoStack.push(deepCopy(currentState));
        return undoStack.pop();
    }

    public ArrayList<Task> getRedoState(ArrayList<Task> currentState) throws NovaException {
        if (redoStack.isEmpty()) {
            throw new NovaException("ERROR: Nothing to redo");
        }

        undoStack.push(deepCopy(currentState));
        return redoStack.pop();
    }

    private ArrayList<Task> deepCopy(ArrayList<Task> original) {
        ArrayList<Task> copy = new ArrayList<>();

        for (Task t : original) {
            copy.add(t.clone());
        }

        return copy;
    }
}
