package test;

import bussinesslogic.InMemoryHistoryManager;
import bussinesslogic.InMemoryTaskManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;
    public abstract T сreateTaskManager();
    @BeforeEach
    public void updateTaskManager(){
        taskManager = сreateTaskManager();
    }
    @Test
        public void shouldExistEpicTask() {
        SubTask sT = taskManager.getSubTaskMap().get(3);
        assertEquals(2, sT.getEpicTaskNumber());
    }
    @Test
        public void shouldCountStatusEpicTask() {
        SubTask subTask = taskManager.getSubTaskMap().get(3);
        SubTask subTask1 = taskManager.getSubTaskMap().get(4);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        taskManager.updateSubTaskById(subTask);
        taskManager.updateSubTaskById(subTask1);
        EpicTask eT = taskManager.getEpicTaskMap().get(2);
        assertEquals(Status.IN_PROGRESS, eT.getStatus(), "Статус не IN_PROGRESS");
    }
    @Test
    public void shouldGetTaskMap() {
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertEquals(map, taskManager.getTaskMap()),
                ()->assertNotEquals(null, taskManager.getTaskMap())
        );
        taskManager.deleteAllTask();
        assertNotEquals(map,taskManager.getTaskMap());
        map.clear();
        assertEquals(map,taskManager.getTaskMap());
    }
    @Test
    public void shouldGetEpicTaskMap() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        assertAll(
                ()->assertEquals(map, taskManager.getEpicTaskMap()),
                ()->assertEquals(Status.NEW,taskManager.getEpicTaskMap().get(2).getStatus())
        );
        Assertions.assertNotNull(taskManager.getEpicTaskMap());
        taskManager.deleteAllEpic();
        assertNotEquals(map,taskManager.getEpicTaskMap());
        map.clear();
        assertEquals(map,taskManager.getEpicTaskMap());
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
        assertAll(
                ()->assertEquals(map, taskManager.getSubTaskMap()),
                ()->assertEquals(2,taskManager.getSubTaskMap().get(3).getEpicTaskNumber()),
                ()->assertNotNull(taskManager.getSubTaskMap())
        );
        taskManager.deleteAllSubTask();
        assertNotEquals(map,taskManager.getSubTaskMap());
        map.clear();
        assertEquals(map,taskManager.getSubTaskMap());
    }
    @Test
    public void shouldGetListTasks(){
        Collection list = new ArrayList();
        list.add(new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Map map = taskManager.getTaskMap();
        assertEquals(list, taskManager.getListTasks(map));
        Assertions.assertNotNull(taskManager.getListTasks(map));
        taskManager.deleteAllTask();
        map = taskManager.getTaskMap();
        assertNotEquals(list,taskManager.getListTasks(map));
        list.clear();
        assertEquals(list,taskManager.getListTasks(map));
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
        Map map = taskManager.getEpicTaskMap();
        assertEquals(list, taskManager.getListEpicTasks(map));
        Assertions.assertNotNull(taskManager.getListEpicTasks(map));
        taskManager.deleteAllEpic();
        map = taskManager.getEpicTaskMap();
        assertNotEquals(list,taskManager.getListTasks(map));
        list.clear();
        assertEquals(list,taskManager.getListEpicTasks(map));
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
        Map map = taskManager.getSubTaskMap();
        assertEquals(list, taskManager.getListSubTasks(map));
        Assertions.assertNotNull(taskManager.getListSubTasks(map));
        taskManager.deleteAllSubTask();
        map = taskManager.getSubTaskMap();
        assertNotEquals(list,taskManager.getListSubTasks(map));
        list.clear();
        assertEquals(list,taskManager.getListSubTasks(map));
    }
    @Test
    public void shouldGetHistoryManager(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        assertEquals(inMemoryHistoryManager.getHistory(), taskManager.getHistoryManager().getHistory());
        inMemoryHistoryManager.add(task);
        taskManager.getTaskById(1);
        assertAll(
                ()->assertNotNull(taskManager.getHistoryManager().getHistory()),
                ()->assertEquals(inMemoryHistoryManager.getHistory(),taskManager.getHistoryManager().getHistory())
        );

    };
    @Test
    public void shouldDeleteAllTask() {
        Map<Integer, Task> map = new HashMap<>();
        taskManager.deleteAllTask();
        assertEquals(map, taskManager.getTaskMap());
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertNotEquals(map,taskManager.getTaskMap()),
                ()->assertNotNull(taskManager.getTaskMap())
        );
    }
    @Test
    public void shouldDeleteAllEpicTask() {
        Map<Integer, EpicTask> map = new HashMap<>();
        taskManager.deleteAllEpic();
        assertEquals(map, taskManager.getEpicTaskMap());
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        assertAll(
                ()->assertNotEquals(map, taskManager.getEpicTaskMap()),
                ()->assertNotNull(taskManager.getEpicTaskMap())
        );
    }
    @Test
    public void shouldDeleteAllSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        taskManager.deleteAllSubTask();
        assertEquals(map, taskManager.getSubTaskMap());
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertNotEquals(map,taskManager.getSubTaskMap());
        Assertions.assertNotNull(taskManager.getSubTaskMap());
    }
    @Test
    public void shouldGetTaskByID() {
        Map<Integer, Task> map = new HashMap<>();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW,
                startTime,15));
        assertEquals(map.get(1), taskManager.getTaskMap().get(1));
        taskManager.deleteAllTask();
        assertAll(
                ()->assertNotEquals(map.get(1),taskManager.getTaskMap().get(1)),
                ()->assertNull(taskManager.getTaskMap().get(1))
        );
    }
    @Test
    public void shouldGetEpicTaskByID() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        assertEquals(map.get(2), taskManager.getEpicTaskMap().get(2));
        taskManager.deleteAllEpic();
        assertAll(
                ()->assertNotEquals(map.get(2),taskManager.getEpicTaskMap().get(2)),
                ()->assertNull(taskManager.getEpicTaskMap().get(2))
        );
    }
    @Test
    public void shouldGetSubTaskByID() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertEquals(map.get(3), taskManager.getSubTaskMap().get(3));
        taskManager.deleteAllSubTask();
        assertAll(
                ()->assertNotEquals(map.get(3),taskManager.getSubTaskMap().get(3)),
                ()->assertNull(taskManager.getSubTaskMap().get(3))
        );
    }
    @Test
    public void shouldMakeTask() {
        Map<Integer, Task> map = new HashMap<>();
        map.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertEquals(map.get(1), taskManager.getTaskMap().get(1)),
                ()->assertNull(taskManager.getTaskMap().get(2)),
                ()->assertNotNull(taskManager.getTaskMap().get(1)),
                ()->assertNotEquals(map.get(1),taskManager.getTaskMap().get(0))
        );
    }
    @Test
    public void shouldMakeEpicTask() {
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        assertAll(
                ()->assertEquals(map.get(2), taskManager.getEpicTaskMap().get(2)),
                ()->assertNull(taskManager.getEpicTaskMap().get(1)),
                ()->assertNotNull(taskManager.getEpicTaskMap().get(2)),
                ()->assertNotEquals(map.get(2),taskManager.getEpicTaskMap().get(1))
        );
    }
    @Test
    public void shouldMakeSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertAll(
                ()->assertEquals(map.get(3), taskManager.getSubTaskMap().get(3)),
                ()->assertNull(taskManager.getSubTaskMap().get(7)),
                ()->assertNotNull(taskManager.getSubTaskMap().get(3)),
                ()->assertNotEquals(map.get(3),taskManager.getSubTaskMap().get(4))
        );
    }
    @Test
    public void shouldDeleteTaskByID() {
        Map<Integer, EpicTask> map = new HashMap<>();
        taskManager.deleteTaskById(1);
        assertAll(
                ()->assertEquals(map, taskManager.getTaskMap()),
                ()->assertNull(taskManager.getTaskMap().get(1))
        );
    }
    @Test
    public void shouldDeleteEpicTaskByID() {
        Map<Integer, EpicTask> epicMap = new HashMap<>();
        Map<Integer, SubTask> subMap = new HashMap<>();
        taskManager.deleteEpicTaskById(2);
        assertAll(
                ()->assertEquals(epicMap, taskManager.getEpicTaskMap()),
                ()->assertEquals(subMap, taskManager.getSubTaskMap()),
                ()->assertNull(taskManager.getEpicTaskMap().get(2))
        );
    }
    @Test
    public void shouldDeleteSubTaskByID() {
        Map<Integer, SubTask> subMap = new HashMap<>();
        subMap.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW, 2));
        taskManager.deleteSubTaskById(3);
        assertAll(
                ()->assertEquals(subMap, taskManager.getSubTaskMap()),
                ()->assertNull(taskManager.getSubTaskMap().get(3))
        );
    }
    @Test
    public void shouldDeleteSubTaskByIDIDEpicTask() {
        Map<Integer, SubTask> subMap = new HashMap<>();
        subMap.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subMap.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW, 2));
        taskManager.deleteSubTaskById(3);

        Map<Integer, EpicTask> epicMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        epicMap.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, list));
        assertEquals(epicMap, taskManager.getEpicTaskMap());
    }
    @Test
    public void shouldUpdateTaskById(){
        Map<Integer, Task> map = new HashMap<>();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                10));
        map.put(1, new Task("Проектирование", "Проектирование ПО",1, Status.DONE, startTime,
                15));
        assertNotEquals(map.get(1), taskManager.getTaskMap().get(1));
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.DONE, startTime,
                15);
        taskManager.updateTaskById(task);
        assertAll(
                ()->assertEquals(map.get(1),taskManager.getTaskMap().get(1)),
                ()->assertNotNull(taskManager.getTaskMap().get(1))
        );
    }
    @Test
    public void shouldUpdateEpicTaskById(){
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        map.put(2, new EpicTask("Unit тесты", "Разработка unit тестов", 2, Status.NEW, list));
        assertNotEquals(map.get(2),taskManager.getEpicTaskMap().get(2));
        EpicTask epicTask= taskManager.getEpicTaskMap().get(2);
        epicTask.setName("Unit тесты");
        epicTask.setDescription("Разработка unit тестов");
        taskManager.updateEpicTaskById(epicTask);
        assertAll(
                ()->assertEquals(map.get(2), taskManager.getEpicTaskMap().get(2)),
                ()->assertNotNull(taskManager.getEpicTaskMap().get(2))
        );
    }
    @Test
    public void shouldUpdateSubTaskById(){
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка консоли", "Разработка меню консоли", 3, Status.NEW,
                2));
        assertNotEquals(map.get(3),taskManager.getSubTaskMap().get(3));
        SubTask subTask= taskManager.getSubTaskMap().get(3);
        subTask.setName("Разработка консоли");
        subTask.setDescription("Разработка меню консоли");
        taskManager.updateSubTaskById(subTask);
        assertAll(
                ()->assertEquals(map.get(3), taskManager.getSubTaskMap().get(3)),
                ()->assertNotNull(taskManager.getSubTaskMap().get(3))
        );
    }
    @Test
    public void shouldGetAllSubTaskInEpic(){
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = taskManager.getEpicTaskMap().get(2);
        assertAll(
                ()->assertEquals(list,epicTask.getListSubtask()),
                ()->assertNotNull(epicTask.getListSubtask())
        );
    }
    @Test
    public void shouldGetTime(){
        assertAll(
                ()->assertEquals("2022-01-01T12:10", taskManager.getTaskMap().get(1).getStartTime().get()
                .toString()),
                ()->assertEquals("2022-01-05T14:20", taskManager.getEpicTaskMap().get(2).getStartTime().get()
                .toString()),
                ()->assertEquals("2022-01-15T20:40", taskManager.getEpicTaskMap().get(2).getEndTime().get()
                .toString()),
                ()->assertEquals("2022-01-05T14:20", taskManager.getSubTaskMap().get(3).getStartTime().get()
                .toString())
        );
    }

    @Test
    public void shouldGetDuration(){
        assertAll(
                ()->assertEquals(15, taskManager.getTaskMap().get(1).getDuration()),
        ()->assertEquals(440, taskManager.getEpicTaskMap().get(2).getDuration()),
        ()->assertEquals(50, taskManager.getSubTaskMap().get(3).getDuration())
        );
    }

    @Test
    public void getPrioritizedTasks(){
        LinkedList<Task> list = new LinkedList<>();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                10));
        Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,14,
                20));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,16,
                40));
        TaskManager taskManager1 = new InMemoryTaskManager();

        Task task =  taskManager1.makeTask("Проектирование", "Проектирование ПО", startTime,
                15);
        EpicTask epicTask = taskManager1.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask =  taskManager1.makeSubTask("Разработка меню",
                "Разработка класса меню", 2, startTime1,50);
        SubTask subTask1 =  taskManager1.makeSubTask("Разработка логики",
                "Разработка класса логики", 2,startTime2,150);
        SubTask subTask2 =  taskManager1.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2,startTime3,240);
        assertEquals(taskManager1.getPrioritizedTasks(),taskManager.getPrioritizedTasks());
    }
    @Test
    public void shouldGetFromFie(){
    }



}
