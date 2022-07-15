package test;

import bussinesslogic.FileBackedTasksManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

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
    public void shouldGetSubTaskMap() {
        super.shouldGetSubTaskMap();
    }
    @Test
    public void shouldGetHistoryManager() {
        super.shouldGetHistoryManager();
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
    void shouldToString(){
        String task = "1,TASK,Проектирование,NEW,Проектирование ПО,no,no";
        String epic = "2,EPIC,Тестирование,NEW,Разработка тестирования,no,[3; 4; 5]";
        String subtask ="3,SUBTASK,Разработка меню,NEW,Разработка класса меню,2,no";
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        String taskFromFile = fileBackedTasksManager.toString(taskManager.getTaskMap().get(1));
        String epicFromFile = fileBackedTasksManager.toString(taskManager.getEpicTaskMap().get(2));
        String subFromFile = fileBackedTasksManager.toString(taskManager.getSubTaskMap().get(3));
        Assertions.assertEquals(task, taskFromFile);
        Assertions.assertEquals(epic, epicFromFile);
        Assertions.assertEquals(subtask, subFromFile);
    }
    @Test
    void shouldFromString()throws IOException {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list);
        SubTask subTask = new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        List<String> listString = new ArrayList();
        if(Paths.get(fileBackedTasksManager.getPath()).toFile().isFile()) {
            Reader reader = new FileReader(fileBackedTasksManager.getPath());
            BufferedReader bf = new BufferedReader(reader);
            bf.readLine();
            while (bf.ready()) {
                String line = bf.readLine();
                listString.add(line);
                if (!line.isBlank()) {

                } else {
                    System.out.println();
                    System.out.println("Загрузка данных в HistoryManager");
                    break;
                }
            }
            if (!bf.ready()) {
                bf.close();
                return;
                }
        }
        System.out.println(listString);
            Task taskFromString = fileBackedTasksManager.fromString(listString.get(0));
            Task epicFromString = fileBackedTasksManager.fromString(listString.get(1));
            Task subTaskFromString = fileBackedTasksManager.fromString(listString.get(2));
            Task subTaskFromString1 = fileBackedTasksManager.fromString(listString.get(3));
            Assertions.assertEquals(task, taskFromString);
            Assertions.assertEquals(epicTask, epicFromString);
            Assertions.assertEquals(subTask, subTaskFromString);
            Assertions.assertNotEquals(subTask,subTaskFromString1);
        }

    @Test
    void shouldFromFile() {
        Map<Integer, Task> map = new HashMap<>();
        taskManager.getTaskMap().clear();
        taskManager.getEpicTaskMap().clear();
        taskManager.getSubTaskMap().clear();
        Assertions.assertEquals(map,taskManager.getTaskMap());
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Map<Integer, EpicTask> epicMap = new HashMap<>();
        Assertions.assertEquals(epicMap,taskManager.getEpicTaskMap());
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        epicMap.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Map<Integer, SubTask> subMap = new HashMap<>();
        Assertions.assertEquals(subMap,taskManager.getSubTaskMap());
        subMap.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        subMap.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        try {
            taskManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(map,taskManager.getTaskMap());
        Assertions.assertEquals(epicMap,taskManager.getEpicTaskMap());
        Assertions.assertEquals(subMap,taskManager.getSubTaskMap());
    }
}