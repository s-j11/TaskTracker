package test;

import bussinesslogic.HTTPTaskManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public class HTTPTaskManagerTest extends TaskManagerTest{

    @Override
    public TaskManager сreateTaskManager() {
        return new HTTPTaskManager("http://localhost:8078");
    }

    @BeforeEach
    public void updateTaskManager(){
        super.updateTaskManager();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                10));
        Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,14,
                20));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,16,
                40));
        Task task =  taskManager.makeTask("Проектирование", "Проектирование ПО", startTime, 15);
        EpicTask epicTask = taskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask =  taskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2, startTime1,50);
        SubTask subTask1 =  taskManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2,startTime2,150);
        SubTask subTask2 =  taskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2,startTime3,240);
    }


    @Test
    public void shouldExistEpicTask() {
        super.shouldExistEpicTask();
    }

    @Test
    public void shouldCountStatusEpicTask() {
        super.shouldCountStatusEpicTask();
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
    public void shouldGetHistoryManager() {
        super.shouldGetHistoryManager();
    }

    @Test
    public void shouldDeleteAllTask() {
        super.shouldDeleteAllTask();
    }

    @Test
    public void shouldDeleteAllEpicTask() {
        super.shouldDeleteAllEpicTask();
    }

    @Test
    public void shouldDeleteAllSubTask() {
        super.shouldDeleteAllSubTask();
    }

    @Test
    public void shouldGetTaskByID() {
        super.shouldGetTaskByID();
    }

    @Override
    public void shouldGetEpicTaskByID() {
        super.shouldGetEpicTaskByID();
    }

    @Test
    public void shouldGetSubTaskByID() {
        super.shouldGetSubTaskByID();
    }

    @Test
    public void shouldMakeTask() {
        super.shouldMakeTask();
    }

    @Test
    public void shouldMakeEpicTask() {
        super.shouldMakeEpicTask();
    }

    @Test
    public void shouldMakeSubTask() {
        super.shouldMakeSubTask();
    }

    @Test
    public void shouldDeleteTaskByID() {
        super.shouldDeleteTaskByID();
    }

    @Test
    public void shouldDeleteEpicTaskByID() {
        super.shouldDeleteEpicTaskByID();
    }

    @Test
    public void shouldDeleteSubTaskByID() {
        super.shouldDeleteSubTaskByID();
    }

    @Test
    public void shouldDeleteSubTaskByIDIDEpicTask() {
        super.shouldDeleteSubTaskByIDIDEpicTask();
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
    public void shouldGetTime() {
        super.shouldGetTime();
    }

    @Test
    public void shouldGetDuration() {
        super.shouldGetDuration();
    }

    @Test
    public void getPrioritizedTasks() {
        super.getPrioritizedTasks();
    }
}
