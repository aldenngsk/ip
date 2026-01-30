package sixseven;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void addTask_getSize_increases() {
        assertEquals(0, taskList.getSize());
        taskList.addTask(new Todo("task 1"));
        assertEquals(1, taskList.getSize());
        taskList.addTask(new Todo("task 2"));
        assertEquals(2, taskList.getSize());
    }

    @Test
    public void getTask_validIndex_returnsTask() throws DukeException {
        Todo t = new Todo("read book");
        taskList.addTask(t);
        assertEquals(t, taskList.getTask(0));
    }

    @Test
    public void getTask_negativeIndex_throwsDukeException() {
        taskList.addTask(new Todo("task"));
        assertThrows(DukeException.class, () -> taskList.getTask(-1));
    }

    @Test
    public void getTask_indexEqualToSize_throwsDukeException() {
        taskList.addTask(new Todo("task"));
        assertThrows(DukeException.class, () -> taskList.getTask(1));
    }

    @Test
    public void getTask_indexGreaterThanSize_throwsDukeException() {
        taskList.addTask(new Todo("task"));
        assertThrows(DukeException.class, () -> taskList.getTask(5));
    }

    @Test
    public void removeTask_validIndex_returnsTaskAndDecreasesSize() throws DukeException {
        Todo t = new Todo("read book");
        taskList.addTask(t);
        taskList.addTask(new Todo("another"));
        assertEquals(2, taskList.getSize());
        Task removed = taskList.removeTask(0);
        assertEquals(t, removed);
        assertEquals(1, taskList.getSize());
        assertEquals("another", taskList.getTask(0).toString().substring(6).trim());
    }

    @Test
    public void removeTask_negativeIndex_throwsDukeException() {
        taskList.addTask(new Todo("task"));
        assertThrows(DukeException.class, () -> taskList.removeTask(-1));
    }

    @Test
    public void removeTask_indexOutOfRange_throwsDukeException() {
        taskList.addTask(new Todo("task"));
        assertThrows(DukeException.class, () -> taskList.removeTask(1));
    }

    @Test
    public void getTasks_returnsCopy() {
        taskList.addTask(new Todo("task"));
        List<Task> first = taskList.getTasks();
        List<Task> second = taskList.getTasks();
        assertEquals(1, first.size());
        assertEquals(1, second.size());
        assertEquals(first.get(0), second.get(0));
    }
}
