package nova.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import nova.exceptions.NovaException;
import nova.tasks.Task;

public class TaskListTest {

    @Test
    public void testEmptyTaskList() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        assertTrue(taskList.isEmpty(), "TaskList should be empty when initialized with an empty list.");
        assertEquals(0, taskList.size(), "Size of TaskList should be 0.");
        assertEquals("No tasks added", taskList.getTaskListString(),
                "Empty task list string should return no tasks added.");
    }

    @Test
    public void testAddTodo() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        taskList.addToDo("Read book");
        assertFalse(taskList.isEmpty(), "TaskList should not be empty after adding a task.");
        assertEquals(1, taskList.size(), "TaskList size should be 1 after adding one task.");

        String taskListString = taskList.getTaskListString();
        assertTrue(taskListString.contains("Read book"),
                "Task list string should contain the todo description.");
    }

    @Test
    public void testMarkAndUnmarkTask() throws NovaException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        taskList.addToDo("Write report");

        taskList.markTask(1);

        // Marking again should throw an exception
        NovaException markException = assertThrows(NovaException.class, () -> taskList.markTask(1));
        assertEquals("ERROR: task is already done", markException.getMessage(),
                "Expected error when marking an already done task.");

        taskList.unMarkTask(1);

        // Unmarking again should throw an exception
        NovaException unmarkException = assertThrows(NovaException.class, () -> taskList.unMarkTask(1));
        assertEquals("ERROR: task is already unmarked", unmarkException.getMessage(),
                "Expected error when unmarking an already unmarked task.");
    }

    @Test
    public void testDeleteTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);

        taskList.addToDo("Clean room");
        assertEquals(1, taskList.size(), "TaskList should have one task after adding.");

        taskList.deleteTask(1);
        assertEquals(0, taskList.size(), "TaskList should be empty after deleting the task.");
    }

    @Test
    public void testFindTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        taskList.addToDo("Buy milk");
        taskList.addToDo("Buy bread");
        taskList.addToDo("Read book");

        // Should find both "Buy milk" and "Buy bread"
        String result = taskList.findTask("Buy");

        assertTrue(result.contains("Buy milk"), "Find should return tasks containing 'Buy milk'.");
        assertTrue(result.contains("Buy bread"), "Find should return tasks containing 'Buy bread'.");
        assertFalse(result.contains("Read book"), "Find should not return tasks that do not match the query.");
    }

    @Test
    public void testAddDeadline() throws NovaException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);

        taskList.addDeadline("Submit assignment", "by 2025-12-31 23:59");
        assertEquals(1, taskList.size(), "TaskList size should increase after adding a deadline.");

        String listString = taskList.getTaskListString();
        assertTrue(listString.contains("Submit assignment"), "Deadline task should be in the list string.");
    }

    @Test
    public void testAddEvent() throws NovaException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);

        taskList.addEvent("New Year's Party", "from 2025-12-31 20:00", "to 2026-01-01 02:00");
        assertEquals(1, taskList.size(), "TaskList size should increase after adding an event.");

        String listString = taskList.getTaskListString();
        assertTrue(listString.contains("New Year's Party"), "Event task should be in the list string.");
    }
}
