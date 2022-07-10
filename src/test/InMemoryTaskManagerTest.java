package test;

import bussinesslogic.InMemoryTaskManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest extends TaskManagerTest {

    @Override
    public TaskManager сreateTaskManager() {
        return new InMemoryTaskManager();
    }
    @BeforeEach
    public void updateTaskManager(){
        super.updateTaskManager();
        Task task =  taskManager.makeTask("Проектирование", "Проектирование ПО");
        EpicTask epicTask = taskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask =  taskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2);
        SubTask subTask1 =  taskManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2);
        SubTask subTask2 =  taskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2);

    }

    @Test
    public void shouldGetTaskMap() {
        super.shouldGetTaskMap();
    }
    @Test
    public void shouldGetEpicTaskMap() {
        super.shouldGetEpicTaskMap();
    }
    @Test
    public void shouldGetSubTaskMap() {
        super.shouldGetSubTaskMap();
    }
    @Test
    public void shouldGetHistoryManager() {

    }
    @Test
    public void shouldGetListTasks() {
        super.shouldGetListTasks();
    }
    @Test
    public void shouldGetListEpicTasks() {
        super.shouldGetListEpicTasks();
    }
    @Test
    public void shouldGetListSubTasks() {
        super.shouldGetListSubTasks();
    }
    @Test
    public void shouldDeleteAllTask() {
        super.shouldDeleteAllTask();
    }
    @Test
    void shouldDeleteAllEpic() {
        super.shouldDeleteAllEpicTask();
    }
    @Test
    public void shouldDeleteAllSubTask() {
        super.shouldDeleteAllSubTask();
    }
    @Test
    void shouldGetTaskById() {
        super.shouldGetTaskByID();
    }
    @Test
    void shouldGetEpicTaskById() {
        super.shouldGetEpicTaskByID();
    }
    @Test
    void shouldGetSubTaskById() {
        super.shouldGetSubTaskByID();
    }
    @Test
    public void shouldMakeTask() {
        super.shouldMakeTask();
    }
    @Test
    void shouldMakeEpic() {
        super.shouldMakeEpicTask();
    }
    @Test
    public void shouldMakeSubTask() {
        super.shouldMakeSubTask();
    }
    @Test
    void shouldDeleteTaskById() {
        super.shouldDeleteTaskByID();
    }
    @Test
    void shouldDeleteEpicTaskById() {
        super.shouldDeleteEpicTaskByID();
    }
    @Test
    void shouldDeleteSubTaskById() {
        super.shouldDeleteSubTaskByID();
    }
    @Test
    public void shouldUpdateTaskById() {
        super.shouldUpdateTaskById();
    }
    @Test
    public void shouldUpdateEpicTaskById() {
        super.shouldUpdateEpicTaskById();
    }
    @Test
    public void shouldUpdateSubTaskById() {
        super.shouldUpdateSubTaskById();
    }
    @Test
    public void shouldGetAllSubTaskInEpic() {
        super.shouldGetAllSubTaskInEpic();
    }
    @Test
    void shouldFromFile() {
    }
}