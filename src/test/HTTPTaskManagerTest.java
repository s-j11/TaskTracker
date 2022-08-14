package test;

import bussinesslogic.HTTPTaskManager;
import bussinesslogic.KVServer;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPTaskManagerTest extends TaskManagerTest{

    KVServer kvServer;
    {
        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TaskManager сreateTaskManager() {
        return new HTTPTaskManager("http://localhost:8078");
    }

    @BeforeEach
    public void updateTaskManager(){
        super.updateTaskManager();
            kvServer.start();
            Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                    10));
            Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,14,
                    20));
            Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                    30));
            Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,16,
                    40));
            Task task =  taskManager.makeTask("Проектирование", "Проектирование ПО", startTime,
                    15);
            EpicTask epicTask = taskManager.makeEpic("Тестирование", "Разработка тестирования");
            SubTask subTask =  taskManager.makeSubTask("Разработка меню",
                    "Разработка класса меню", 2, startTime1,50);
            SubTask subTask1 =  taskManager.makeSubTask("Разработка логики",
                    "Разработка класса логики", 2,startTime2,150);
            SubTask subTask2 =  taskManager.makeSubTask("Класс тестирования",
                    "Разработка класа тестирования", 2,startTime3,240);
    }
    @AfterEach
    public void stopTaskManager() {
            kvServer.stop();
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
    public void shouldGetPrioritizedTasks() {
        super.getPrioritizedTasks();
    }

    @Test
    public void shouldSaveToServer() throws IOException {
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        taskManager.fromFile();
        kvServer.stop();
        HTTPTaskManager httpTaskManager2 = new HTTPTaskManager("http://localhost:8078");
        KVServer kvServer2;
        {
            try {
                kvServer2 = new KVServer();
                kvServer2.start();
                Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,
                        12, 10));
                Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,
                        14, 20));
                Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,
                        15, 30));
                Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,
                        16, 40));
                httpTaskManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
                httpTaskManager2.makeEpic("Тестирование","Разработка тестирования");
                httpTaskManager2.makeSubTask("Разработка меню","Разработка класса меню",2,startTime1,
                        30);
                httpTaskManager2.makeSubTask("Разработка логики","Разработка класса логики",2,
                        startTime2,30);
                httpTaskManager2.makeSubTask("Класс тестирования","Разработка класа тестирования",2,
                        startTime3,30);
                httpTaskManager2.getEpicTaskById(2);
                httpTaskManager2.getSubTaskById(4);
                httpTaskManager2.getSubTaskById(5);
                httpTaskManager2.getTaskById(1);
                httpTaskManager2.getSubTaskById(3);
                httpTaskManager2.saveToFile();
                httpTaskManager2.fromFile();
                kvServer2.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        assertAll(
                ()->assertEquals(httpTaskManager2.getTaskMap(),taskManager.getTaskMap()),
                ()->assertEquals(httpTaskManager2.getEpicTaskMap(),taskManager.getEpicTaskMap()),
                ()->assertEquals(httpTaskManager2.getSubTaskMap(),taskManager.getSubTaskMap()),
                ()->assertEquals(httpTaskManager2.getHistoryManager().getHistory(),
                        taskManager.getHistoryManager().getHistory()));
    }

    @Test
    public void shouldLoadFromServer() throws IOException{
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        taskManager.fromFile();
        kvServer.stop();
        HTTPTaskManager httpTaskManager2 = new HTTPTaskManager("http://localhost:8078");
      KVServer kvServer2;
    {
        try {
            kvServer2 = new KVServer();
            kvServer2.start();
            Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                    10));
            Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,14,
                    20));
            Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                    30));
            Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,16,
                    40));
            httpTaskManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
            httpTaskManager2.makeEpic("Тестирование","Разработка тестирования");
            httpTaskManager2.makeSubTask("Разработка меню","Разработка класса меню",2,startTime1,
                    30);
            httpTaskManager2.makeSubTask("Разработка логики","Разработка класса логики",2,
                    startTime2,30);
            httpTaskManager2.makeSubTask("Класс тестирования","Разработка класа тестирования",2,
                    startTime3,30);
            httpTaskManager2.getEpicTaskById(2);
            httpTaskManager2.getSubTaskById(4);
            httpTaskManager2.getSubTaskById(5);
            httpTaskManager2.getTaskById(1);
            httpTaskManager2.getSubTaskById(3);
            httpTaskManager2.saveToFile();
            httpTaskManager2.fromFile();
            kvServer2.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        assertAll(
                ()->assertEquals(httpTaskManager2.getTaskMap(),taskManager.getTaskMap()),
                ()->assertEquals(httpTaskManager2.getEpicTaskMap(),taskManager.getEpicTaskMap()),
                ()->assertEquals(httpTaskManager2.getSubTaskMap(),taskManager.getSubTaskMap()),
                ()->assertEquals(httpTaskManager2.getHistoryManager().getHistory(),
                        taskManager.getHistoryManager().getHistory()));
    }
}
