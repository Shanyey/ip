package nova.tasklist;

import nova.tasks.Task;
import nova.exceptions.NovaException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TaskListTest {
    private final String[] arr = {"lecture", "from 2019-01-01 19:30"};
    private final String[] arrMissingFrom = {"lecture", "2019-01-01 19:30", "to 2019-01-01 20:30"};

    @Test
    public void addTodo_success() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasksList = new TaskList(tasks);
        tasksList.addToDo("read book");
        assertEquals(1, tasks.size());
    }

    @Test
    public void addEvent_missingByDate_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasksList = new TaskList(tasks);
        try {
            tasksList.addEvent(arr);
        } catch (NovaException e) {
            assertEquals("too little arguments", e.getMessage());
        }
    }

    @Test
    public void addEvent_missingFrom_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasksList = new TaskList(tasks);
        try {
            tasksList.addEvent(arrMissingFrom);
        } catch (NovaException e) {
            assertEquals("invalid format", e.getMessage());
        }
    }
}
