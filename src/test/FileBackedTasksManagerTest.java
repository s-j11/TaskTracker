package test;

import bussinesslogic.FileBackedTasksManager;
import bussinesslogic.InMemoryHistoryManager;
import maketbussinesslogic.HistoryManager;
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
import java.time.LocalDateTime;
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
    public void shouldToString(){
        String task = "1,TASK,Проектирование,NEW,Проектирование ПО,no,no,2022.01.01.12:10,15";
        String epic = "2,EPIC,Тестирование,NEW,Разработка тестирования,no,[3; 4; 5],2022.01.05.14:20,440";
        String subtask ="3,SUBTASK,Разработка меню,NEW,Разработка класса меню,2,no,2022.01.05.14:20,50";
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        String taskFromFile = fileBackedTasksManager.toString(taskManager.getTaskMap().get(1));
        String epicFromFile = fileBackedTasksManager.toString(taskManager.getEpicTaskMap().get(2));
        String subFromFile = fileBackedTasksManager.toString(taskManager.getSubTaskMap().get(3));
        Assertions.assertEquals(task, taskFromFile);
        Assertions.assertEquals(epic, epicFromFile);
        Assertions.assertEquals(subtask, subFromFile);
    }
    @Test
    public void shouldFromString()throws IOException {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
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
                    break;
                }
            }
            if (!bf.ready()) {
                bf.close();
                return;
                }
        }
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
    public void shouldFromFile() {
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
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW, 2));
        try {
            taskManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(map,taskManager.getTaskMap());
        Assertions.assertEquals(epicMap,taskManager.getEpicTaskMap());
        Assertions.assertEquals(subMap,taskManager.getSubTaskMap());
    }
    @Test
    public void shouldToStringHistoryManager(){
        String str = "2, 4, 5, 1, 3";
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        String strHistoryMangager = fileBackedTasksManager.toString(taskManager.getHistoryManager());
        Assertions.assertEquals(str,strHistoryMangager);
    }
    @Test
    public void shouldHistoryManagerFromFile() throws IOException{
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
        SubTask subTask = new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2);
        SubTask subTask1 = new SubTask("Разработка логики", "Разработка класса логики", 4,
                Status.NEW,2);
        SubTask subTask2 = new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW,2);
        historyManager.add(epicTask);
        historyManager.add(subTask1);
        historyManager.add(subTask2);
        historyManager.add(task);
        historyManager.add(subTask);
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        fileBackedTasksManager.fromFile();
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
                    break;
                }
            }
            if (!bf.ready()) {
                bf.close();
                return;
            } else {
            String string = bf.readLine();
            if (string.isBlank()) {
            } else {
                listString.add(string);
            }
            bf.close();
            System.out.println();
        }
        }
        String str = listString.get(6);
        HistoryManager historyManagerFromString = fileBackedTasksManager.fromFile(str);
        Assertions.assertEquals(historyManager.getHistory(),historyManagerFromString.getHistory());
    }
    @Test
    public void shouldSaveToFile() throws IOException{
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        Optional<LocalDateTime> startTime1 = Optional.of(LocalDateTime.of(2022,1,5,15,
                30));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,15,
                30));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        fileBackedTasksManager.fromFile();
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager("src/store/test2.csv");
        fileBackedTasksManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
        fileBackedTasksManager2.makeEpic("Тестирование","Разработка тестирования");
        fileBackedTasksManager2.makeSubTask("Разработка меню","Разработка класса меню",2,startTime1,
                30);
        fileBackedTasksManager2.makeSubTask("Разработка логики","Разработка класса логики",2,
                startTime2,30);
        fileBackedTasksManager2.makeSubTask("Класс тестирования","Разработка класа тестирования",2,
                startTime3,30);
        fileBackedTasksManager2.getEpicTaskById(2);
        fileBackedTasksManager2.getSubTaskById(4);
        fileBackedTasksManager2.getSubTaskById(5);
        fileBackedTasksManager2.getTaskById(1);
        fileBackedTasksManager2.getSubTaskById(3);
        fileBackedTasksManager2.saveToFile();
        fileBackedTasksManager2.fromFile();
        Assertions.assertEquals(fileBackedTasksManager2.getTaskMap(),fileBackedTasksManager.getTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getEpicTaskMap(),fileBackedTasksManager.getEpicTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getSubTaskMap(),fileBackedTasksManager.getSubTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getHistoryManager().getHistory(),
                fileBackedTasksManager.getHistoryManager().getHistory());
    }
    @Test
    public void shouldEmptyTaskList() throws IOException{
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        Optional<LocalDateTime> startTime1 = Optional.of(LocalDateTime.of(2022,1,5,15,
                30));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,15,
                30));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        taskManager.getEpicTaskById(2);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getTaskById(1);
        taskManager.getSubTaskById(3);
        taskManager.deleteAllTask();
        fileBackedTasksManager.fromFile();
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager("src/store/test2.csv");
        fileBackedTasksManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
        fileBackedTasksManager2.makeEpic("Тестирование","Разработка тестирования");
        fileBackedTasksManager2.makeSubTask("Разработка меню","Разработка класса меню",2,startTime1,
                30);
        fileBackedTasksManager2.makeSubTask("Разработка логики","Разработка класса логики",2,
                startTime2,30);
        fileBackedTasksManager2.makeSubTask("Класс тестирования","Разработка класа тестирования",2,
                startTime3,30);
        fileBackedTasksManager2.getEpicTaskById(2);
        fileBackedTasksManager2.getSubTaskById(4);
        fileBackedTasksManager2.getSubTaskById(5);
        fileBackedTasksManager2.getTaskById(1);
        fileBackedTasksManager2.getSubTaskById(3);
        fileBackedTasksManager2.deleteAllTask();
        fileBackedTasksManager2.fromFile();
        Assertions.assertEquals(fileBackedTasksManager2.getTaskMap(),fileBackedTasksManager.getTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getEpicTaskMap(),fileBackedTasksManager.getEpicTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getSubTaskMap(),fileBackedTasksManager.getSubTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getHistoryManager().getHistory(),
                fileBackedTasksManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldEpicTaskWithEmptyListTasks() throws IOException {
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        taskManager.getEpicTaskById(2);
        taskManager.getTaskById(1);
        taskManager.deleteAllSubTask();
        fileBackedTasksManager.fromFile();
    FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager("src/store/test2.csv");
        fileBackedTasksManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
        fileBackedTasksManager2.makeEpic("Тестирование","Разработка тестирования");
        fileBackedTasksManager2.getEpicTaskById(2);
        fileBackedTasksManager2.getTaskById(1);
        fileBackedTasksManager2.fromFile();
        Assertions.assertEquals(fileBackedTasksManager2.getTaskMap(),fileBackedTasksManager.getTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getEpicTaskMap(),fileBackedTasksManager.getEpicTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getSubTaskMap(),fileBackedTasksManager.getSubTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getHistoryManager().getHistory(),
                fileBackedTasksManager.getHistoryManager().getHistory());
}


    @Test
    public void shouldEmptyHistoryList() throws IOException {
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/test.csv");
        fileBackedTasksManager.fromFile();
        fileBackedTasksManager.deleteAllSubTask();
        fileBackedTasksManager.getHistoryManager().removeAllNode();
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager("src/store/test2.csv");
        fileBackedTasksManager2.makeTask("Проектирование","Проектирование ПО",startTime,30);
        fileBackedTasksManager2.makeEpic("Тестирование","Разработка тестирования");
        fileBackedTasksManager2.fromFile();
        Assertions.assertEquals(fileBackedTasksManager2.getTaskMap(),fileBackedTasksManager.getTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getEpicTaskMap(),fileBackedTasksManager.getEpicTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getSubTaskMap(),fileBackedTasksManager.getSubTaskMap());
        Assertions.assertEquals(fileBackedTasksManager2.getHistoryManager().getHistory(),
                fileBackedTasksManager.getHistoryManager().getHistory());
    }

}