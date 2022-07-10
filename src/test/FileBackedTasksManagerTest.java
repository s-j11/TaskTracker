package test;

import bussinesslogic.FileBackedTasksManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest{

    @Override
    public TaskManager сreateTaskManager() {
        return new FileBackedTasksManager("src/store/test.csv");
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