package test;

import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.*;
import java.util.List;

class TaskManagerTest<T extends TaskManager> {
    private Managers managers = new Managers();
    private TaskManager inFileBackedTaksManager = managers.getDefaultFileBackedManager("src/store/test.csv");
    private HistoryManager historyManager = inFileBackedTaksManager.getHistoryManager();


    @BeforeEach
    public void shouldPreparedTestEnvironment(){
        Task task = inFileBackedTaksManager.makeTask("Проектирование","Проектирование ПО");
        EpicTask epicTask = inFileBackedTaksManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inFileBackedTaksManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2);
        SubTask subTask1 = inFileBackedTaksManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2);
        SubTask subTask2 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2);
                  }

    @Test
    public void shouldExistEpicTask(){
    SubTask sT = inFileBackedTaksManager.getSubTaskMap().get(3);
        Assertions.assertEquals(2, sT.getEpicTaskNumber());
    }

    @Test
    public void shouldCountStatusEpicTask(){
        SubTask subTask = inFileBackedTaksManager.getSubTaskMap().get(3);
        SubTask subTask1 = inFileBackedTaksManager.getSubTaskMap().get(4);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(2);
        Assertions.assertEquals(Status.IN_PROGRESS, eT.getStatus(), "Статус не IN_PROGRESS");
    }

    @Test
    public void shouldGetTaskMap(){
        Map<Integer,Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование","Проектирование ПО",1,Status.NEW));
        Assertions.assertEquals( map, inFileBackedTaksManager.getTaskMap());
    }

    @Test
    @NullSource
    @EmptySource
    public void shouldGetEpicTaskMap(){
        Map<Integer,EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование","Разработка тестирования",2,Status.NEW, list));
        Assertions.assertEquals( map, inFileBackedTaksManager.getEpicTaskMap());
    }

    @Test
    public void shouldGetSubTaskMap(){
        Map<Integer,SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню","Разработка класса меню", 3,Status.NEW,
                2));
        map.put(4, new SubTask("Разработка логики","Разработка класса логики", 4,Status.NEW,
                2));
        map.put(5, new SubTask("Класс тестирования","Разработка класа тестирования", 5,Status.NEW,
                2));

        Assertions.assertEquals(map, inFileBackedTaksManager.getSubTaskMap());
    }

    @Test
    public void shouldDeleteAllTask(){
        Map<Integer,EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllTask();
        Assertions.assertEquals(map,inFileBackedTaksManager.getTaskMap());
    }

    @Test
    public void shouldDeleteAllEpicTask(){
        Map<Integer,EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllEpic();
        Assertions.assertEquals(map,inFileBackedTaksManager.getEpicTaskMap());
    }

    @Test
    public void shouldDeleteAllSubTask(){
        Map<Integer,EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllSubTask();
        Assertions.assertEquals(map,inFileBackedTaksManager.getSubTaskMap());
    }

    @Test
    public void shouldTaskByID(){
        Map<Integer,Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование","Проектирование ПО",1,Status.NEW));
        Assertions.assertEquals(map.get(1), inFileBackedTaksManager.getTaskMap().get(1));
    }

    @Test
    public void shouldEpicTaskByID(){
        Map<Integer,EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование","Разработка тестирования",2,Status.NEW, list));
        Assertions.assertEquals(map.get(2), inFileBackedTaksManager.getEpicTaskMap().get(2));
    }

    @Test
    public void shouldSubTaskByID() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        Assertions.assertEquals(map.get(3), inFileBackedTaksManager.getSubTaskMap().get(3));
    }

    @Test
    public void shouldMakeTask(){
        Map<Integer,Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование","Проектирование ПО",1,Status.NEW));
        Assertions.assertEquals(map.get(1), inFileBackedTaksManager.getTaskMap().get(1));
    }

    @Test
    public void shouldMakeEpicTask(){
        Map<Integer,EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование","Разработка тестирования",2,Status.NEW, list));
        Assertions.assertEquals(map.get(2), inFileBackedTaksManager.getEpicTaskMap().get(2));
    }

    @Test
    public void shouldMakeSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        Assertions.assertEquals(map.get(3), inFileBackedTaksManager.getSubTaskMap().get(3));
    }

    @Test
    public void shouldDeleteTaskByID(){
        Map<Integer,EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteTaskById(1);
        Assertions.assertEquals(map,inFileBackedTaksManager.getTaskMap());
    }

    @Test
    public void shouldDeleteEpicTaskByID(){
        Map<Integer,EpicTask> epicMap = new HashMap<>();
        Map<Integer,SubTask> subMap = new HashMap<>();
        inFileBackedTaksManager.deleteEpicTaskById(2);
        Assertions.assertEquals(epicMap,inFileBackedTaksManager.getEpicTaskMap());
        Assertions.assertEquals(subMap,inFileBackedTaksManager.getSubTaskMap());
    }

    @Test
    public void shouldDeleteSubTaskByID(){
        Map<Integer,SubTask> subMap = new HashMap<>();
        subMap.put(4, new SubTask("Разработка логики","Разработка класса логики", 4,Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования","Разработка класа тестирования", 5,Status.NEW,
                2));
        inFileBackedTaksManager.deleteSubTaskById(3);
        Assertions.assertEquals(subMap,inFileBackedTaksManager.getSubTaskMap());
//        Map<Integer,EpicTask> epicMap = new HashMap<>();
//        List<Integer> list = new ArrayList<>();
//        list.add(4);
//        list.add(5);
//        epicMap.put(2, new EpicTask("Тестирование","Разработка тестирования",2,Status.NEW, list));
//        Assertions.assertEquals(epicMap,inFileBackedTaksManager.getEpicTaskMap());
    }


}

