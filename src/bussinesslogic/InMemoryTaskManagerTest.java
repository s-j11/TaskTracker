package bussinesslogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TaskManagerTest;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest {

    @Test
    public void shouldGetTaskMap() {
    super.shouldGetTaskMap();
    }

    @Test
    public void shouldGetEpicTaskMap() {
    super.shouldGetEpicTaskMap();
    }

    @Test
    void shouldgetSubTaskMap() {
        super.shouldGetSubTaskMap();
    }

    @Test
    void shouldgetHistoryManager() {

    }

    @Test
    void getListTasks() {
        super.shouldGetListTasks();
    }

    @Test
    void getListEpicTasks() {
        super.shouldGetListEpicTasks();
    }

    @Test
    void getListSubTasks() {
        super.shouldGetListSubTasks();
    }

    @Test
    void deleteAllTask() {
        super.shouldDeleteAllTask();
    }

    @Test
    void deleteAllEpic() {
        super.shouldDeleteAllEpicTask();
    }

    @Test
    void deleteAllSubTask() {
        super.shouldDeleteAllSubTask();
    }

    @Test
    void getTaskById() {
        super.shouldGetTaskByID();
    }

    @Test
    void getEpicTaskById() {
        super.shouldGetEpicTaskByID();

    }

    @Test
    void getSubTaskById() {
        super.shouldGetSubTaskByID();
    }

    @Test
    void makeTask() {
        super.shouldMakeTask();
    }

    @Test
    void makeEpic() {
        super.shouldMakeEpicTask();
    }

    @Test
    void makeSubTask() {
        super.shouldMakeSubTask();
    }

    @Test
    void deleteTaskById() {
        super.shouldDeleteTaskByID();
    }

    @Test
    void deleteEpicTaskById() {
        super.shouldDeleteEpicTaskByID();
    }

    @Test
    void deleteSubTaskById() {
        super.shouldDeleteSubTaskByID();
    }

    @Test
    void updateTaskById() {
        super.shouldUpdateTaskById();
    }

    @Test
    void updateEpicTaskById() {
        super.shouldUpdateEpicTaskById();
    }

    @Test
    void updateSubTaskById() {
        super.shouldUpdateSubTaskById();
    }

    @Test
    void getAllSubTaskInEpic() {
        super.shouldGetAllSubTaskInEpic();
    }

    @Test
    void fromFile() {
    }
}