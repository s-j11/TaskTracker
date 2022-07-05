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

import java.util.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    private Managers managers = new Managers();
    private TaskManager inFileBackedTaksManager = managers.getDefaultFileBackedManager("src/store/test.csv");
    private HistoryManager historyManager = inFileBackedTaksManager.getHistoryManager();


    @BeforeEach
    public void shouldPreparedTestEnvironment() {
        Task task = inFileBackedTaksManager.makeTask("Проектирование", "Проектирование ПО");
        EpicTask epicTask = inFileBackedTaksManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inFileBackedTaksManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2);
        SubTask subTask1 = inFileBackedTaksManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2);
        SubTask subTask2 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2);
    }

    @Test
        public void shouldExistEpicTask() {
        SubTask sT = inFileBackedTaksManager.getSubTaskMap().get(3);
        Assertions.assertEquals(2, sT.getEpicTaskNumber());
    }

    @Test
        public void shouldCountStatusEpicTask() {
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
    public void shouldGetTaskMap() {
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Assertions.assertEquals(map, inFileBackedTaksManager.getTaskMap());
        Assertions.assertNotEquals(null, inFileBackedTaksManager.getTaskMap());
        inFileBackedTaksManager.deleteAllTask();
        Assertions.assertNotEquals(map,inFileBackedTaksManager.getTaskMap());
        map.clear();
        Assertions.assertEquals(map,inFileBackedTaksManager.getTaskMap());
    }

    @Test
    public void shouldGetEpicTaskMap() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Assertions.assertEquals(map, inFileBackedTaksManager.getEpicTaskMap());
        Assertions.assertEquals(Status.NEW,inFileBackedTaksManager.getEpicTaskMap().get(2).getStatus());
        Assertions.assertNotNull(inFileBackedTaksManager.getEpicTaskMap());
        inFileBackedTaksManager.deleteAllEpic();
        Assertions.assertNotEquals(map,inFileBackedTaksManager.getEpicTaskMap());
        map.clear();
        Assertions.assertEquals(map,inFileBackedTaksManager.getEpicTaskMap());
    }

    @Test
    public void shouldGetSubTaskMap() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        map.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        map.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        Assertions.assertEquals(map, inFileBackedTaksManager.getSubTaskMap());
        Assertions.assertEquals(2,inFileBackedTaksManager.getSubTaskMap().get(3).getEpicTaskNumber());
        Assertions.assertNotNull(inFileBackedTaksManager.getSubTaskMap());
        inFileBackedTaksManager.deleteAllSubTask();
        Assertions.assertNotEquals(map,inFileBackedTaksManager.getSubTaskMap());
        map.clear();
        Assertions.assertEquals(map,inFileBackedTaksManager.getSubTaskMap());
    }

    @Test
    public void shouldGetListTasks(){
        Collection list = new ArrayList();
        list.add(new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Map map = inFileBackedTaksManager.getTaskMap();
        Assertions.assertEquals(list, inFileBackedTaksManager.getListTasks(map));
        Assertions.assertNotNull(inFileBackedTaksManager.getListTasks(map));
        inFileBackedTaksManager.deleteAllTask();
        map = inFileBackedTaksManager.getTaskMap();
        Assertions.assertNotEquals(list,inFileBackedTaksManager.getListTasks(map));
        list.clear();
        Assertions.assertEquals(list,inFileBackedTaksManager.getListTasks(map));
    }

    @Test
    public void shouldGetListEpicTasks(){
        Collection list = new ArrayList();
        List<Integer> listSubTasks = new ArrayList<>();
        listSubTasks.add(3);
        listSubTasks.add(4);
        listSubTasks.add(5);
        list.add(new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                listSubTasks));
        Map map = inFileBackedTaksManager.getEpicTaskMap();
        Assertions.assertEquals(list, inFileBackedTaksManager.getListEpicTasks(map));
        Assertions.assertNotNull(inFileBackedTaksManager.getListEpicTasks(map));
        inFileBackedTaksManager.deleteAllEpic();
        map = inFileBackedTaksManager.getEpicTaskMap();
        Assertions.assertNotEquals(list,inFileBackedTaksManager.getListTasks(map));
        list.clear();
        Assertions.assertEquals(list,inFileBackedTaksManager.getListEpicTasks(map));
    }
    @Test
    public void shouldGetListSubTasks(){
        Collection list = new ArrayList();
        list.add(new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        list.add(new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        list.add(new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        Map map = inFileBackedTaksManager.getSubTaskMap();
        Assertions.assertEquals(list, inFileBackedTaksManager.getListSubTasks(map));
        Assertions.assertNotNull(inFileBackedTaksManager.getListSubTasks(map));
        inFileBackedTaksManager.deleteAllSubTask();
        map = inFileBackedTaksManager.getSubTaskMap();
        Assertions.assertNotEquals(list,inFileBackedTaksManager.getListSubTasks(map));
        list.clear();
        Assertions.assertEquals(list,inFileBackedTaksManager.getListSubTasks(map));
    }

    @Test
    public void shouldGetHistoryManager(){

    };


    @Test
    public void shouldDeleteAllTask() {
        Map<Integer, Task> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllTask();
        Assertions.assertEquals(map, inFileBackedTaksManager.getTaskMap());
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Assertions.assertNotEquals(map,inFileBackedTaksManager.getTaskMap());
        Assertions.assertNotNull(inFileBackedTaksManager.getTaskMap());
    }

    @Test
    public void shouldDeleteAllEpicTask() {
        Map<Integer, EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllEpic();
        Assertions.assertEquals(map, inFileBackedTaksManager.getEpicTaskMap());
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Assertions.assertNotEquals(map, inFileBackedTaksManager.getEpicTaskMap());
        Assertions.assertNotNull(inFileBackedTaksManager.getEpicTaskMap());
    }

    @Test
    public void shouldDeleteAllSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteAllSubTask();
        Assertions.assertEquals(map, inFileBackedTaksManager.getSubTaskMap());
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        Assertions.assertNotEquals(map,inFileBackedTaksManager.getSubTaskMap());
        Assertions.assertNotNull(inFileBackedTaksManager.getSubTaskMap());
    }

    @Test
    public void shouldGetTaskByID() {
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Assertions.assertEquals(map.get(1), inFileBackedTaksManager.getTaskMap().get(1));
        inFileBackedTaksManager.deleteAllTask();
        Assertions.assertNotEquals(map.get(1),inFileBackedTaksManager.getTaskMap().get(1));
        Assertions.assertNull(inFileBackedTaksManager.getTaskMap().get(1));
    }

    @Test
    public void shouldGetEpicTaskByID() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Assertions.assertEquals(map.get(2), inFileBackedTaksManager.getEpicTaskMap().get(2));
        inFileBackedTaksManager.deleteAllEpic();
        Assertions.assertNotEquals(map.get(2),inFileBackedTaksManager.getEpicTaskMap().get(2));
        Assertions.assertNull(inFileBackedTaksManager.getEpicTaskMap().get(2));
    }

    @Test
    public void shouldGetSubTaskByID() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        Assertions.assertEquals(map.get(3), inFileBackedTaksManager.getSubTaskMap().get(3));
        inFileBackedTaksManager.deleteAllSubTask();
        Assertions.assertNotEquals(map.get(3),inFileBackedTaksManager.getSubTaskMap().get(3));
        Assertions.assertNull(inFileBackedTaksManager.getSubTaskMap().get(3));
    }

    @Test
    public void shouldMakeTask() {
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Assertions.assertEquals(map.get(1), inFileBackedTaksManager.getTaskMap().get(1));
        Assertions.assertNull(inFileBackedTaksManager.getTaskMap().get(2));
        Assertions.assertNotNull(inFileBackedTaksManager.getTaskMap().get(1));
        Assertions.assertNotEquals(map.get(1),inFileBackedTaksManager.getTaskMap().get(0));
    }

    @Test
    public void shouldMakeEpicTask() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Assertions.assertEquals(map.get(2), inFileBackedTaksManager.getEpicTaskMap().get(2));
        Assertions.assertNull(inFileBackedTaksManager.getEpicTaskMap().get(1));
        Assertions.assertNotNull(inFileBackedTaksManager.getEpicTaskMap().get(2));
        Assertions.assertNotEquals(map.get(2),inFileBackedTaksManager.getEpicTaskMap().get(1));
    }

    @Test
    public void shouldMakeSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        Assertions.assertEquals(map.get(3), inFileBackedTaksManager.getSubTaskMap().get(3));
        Assertions.assertNull(inFileBackedTaksManager.getSubTaskMap().get(7));
        Assertions.assertNotNull(inFileBackedTaksManager.getSubTaskMap().get(3));
        Assertions.assertNotEquals(map.get(3),inFileBackedTaksManager.getSubTaskMap().get(4));

    }

    @Test
    public void shouldDeleteTaskByID() {
        Map<Integer, EpicTask> map = new HashMap<>();
        inFileBackedTaksManager.deleteTaskById(1);
        Assertions.assertEquals(map, inFileBackedTaksManager.getTaskMap());
        Assertions.assertNull(inFileBackedTaksManager.getTaskMap().get(1));

    }

    @Test
    public void shouldDeleteEpicTaskByID() {
        Map<Integer, EpicTask> epicMap = new HashMap<>();
        Map<Integer, SubTask> subMap = new HashMap<>();
        inFileBackedTaksManager.deleteEpicTaskById(2);
        Assertions.assertEquals(epicMap, inFileBackedTaksManager.getEpicTaskMap());
        Assertions.assertEquals(subMap, inFileBackedTaksManager.getSubTaskMap());
        Assertions.assertNull(inFileBackedTaksManager.getEpicTaskMap().get(2));
    }

    @Test
    public void shouldDeleteSubTaskByID() {
        Map<Integer, SubTask> subMap = new HashMap<>();
        subMap.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        inFileBackedTaksManager.deleteSubTaskById(3);
        Assertions.assertEquals(subMap, inFileBackedTaksManager.getSubTaskMap());
        Assertions.assertNull(inFileBackedTaksManager.getSubTaskMap().get(3));
    }

    @Test
    public void shouldDeleteSubTaskByIDIDEpicTask() {
        Map<Integer, SubTask> subMap = new HashMap<>();
        subMap.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        inFileBackedTaksManager.deleteSubTaskById(3);

        Map<Integer, EpicTask> epicMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        epicMap.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        Assertions.assertEquals(epicMap, inFileBackedTaksManager.getEpicTaskMap());

    }

    @Test
    public void shouldUpdateTaskById(){
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.DONE));
        Assertions.assertNotEquals(map.get(1), inFileBackedTaksManager.getTaskMap().get(1));
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.DONE);
        inFileBackedTaksManager.updateTaskById(task);
        Assertions.assertEquals(map.get(1),inFileBackedTaksManager.getTaskMap().get(1));
        Assertions.assertNotNull(inFileBackedTaksManager.getTaskMap().get(1));
    }

    @Test
    public void shouldUpdateEpicTaskById(){
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Unit тесты", "Разработка unit тестов", 2, Status.NEW, list));
        Assertions.assertNotEquals(map.get(2),inFileBackedTaksManager.getEpicTaskMap().get(2));
        EpicTask epicTask= inFileBackedTaksManager.getEpicTaskMap().get(2);
        epicTask.setName("Unit тесты");
        epicTask.setDescription("Разработка unit тестов");
        inFileBackedTaksManager.updateEpicTaskById(epicTask);
        Assertions.assertEquals(map.get(2), inFileBackedTaksManager.getEpicTaskMap().get(2));
        Assertions.assertNotNull(inFileBackedTaksManager.getEpicTaskMap().get(2));
    }
    @Test
    public void shouldUpdateSubTaskById(){
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка консоли", "Разработка меню консоли", 3, Status.NEW,
                2));
        Assertions.assertNotEquals(map.get(3),inFileBackedTaksManager.getSubTaskMap().get(3));
        SubTask subTask= inFileBackedTaksManager.getSubTaskMap().get(3);
        subTask.setName("Разработка консоли");
        subTask.setDescription("Разработка меню консоли");
        inFileBackedTaksManager.updateSubTaskById(subTask);
        Assertions.assertEquals(map.get(3), inFileBackedTaksManager.getSubTaskMap().get(3));
        Assertions.assertNotNull(inFileBackedTaksManager.getSubTaskMap().get(3));
    }

    @Test
    public void shouldGetAllSubTaskInEpic(){
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = inFileBackedTaksManager.getEpicTaskMap().get(2);
        Assertions.assertEquals(list,epicTask.getListSubtask());
        Assertions.assertNotNull(epicTask.getListSubtask());
    }
    @Test
    public void shouldGetFromFie(){

    }



}
