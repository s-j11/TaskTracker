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
        SubTask sT = taskManager.getSubTasks().get(3);
        assertEquals(2, sT.getEpicTaskNumber());
    }
    @Test
        public void shouldCountStatusEpicTask() {
        SubTask subTask = taskManager.getSubTasks().get(3);
        SubTask subTask1 = taskManager.getSubTasks().get(4);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        taskManager.updateSubTaskById(subTask);
        taskManager.updateSubTaskById(subTask1);
        EpicTask eT = taskManager.getEpicTasks().get(2);
        assertEquals(Status.IN_PROGRESS, eT.getStatus(), "Статус не IN_PROGRESS");
    }
    @Test
    public void shouldGetTaskMap() {
        Map<Integer, Task> tasks = new HashMap<>();
        tasks.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertEquals(tasks, taskManager.getTasks()),
                ()->assertNotEquals(null, taskManager.getTasks())
        );
        taskManager.deleteAllTasks();
        assertNotEquals(tasks,taskManager.getTasks());
        tasks.clear();
        assertEquals(tasks,taskManager.getTasks());
    }
    @Test
    public void shouldGetEpicTaskMap() {
        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, catalogue));
        assertAll(
                ()->assertEquals(epicTasks, taskManager.getEpicTasks()),
                ()->assertEquals(Status.NEW,taskManager.getEpicTasks().get(2).getStatus())
        );
        Assertions.assertNotNull(taskManager.getEpicTasks());
        taskManager.deleteAllEpicTasks();
        assertNotEquals(epicTasks,taskManager.getEpicTasks());
        epicTasks.clear();
        assertEquals(epicTasks,taskManager.getEpicTasks());
    }
    @Test
    public void shouldGetSubTaskMap() {
        Map<Integer, SubTask> subTasks = new HashMap<>();
        subTasks.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        subTasks.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subTasks.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        assertAll(
                ()->assertEquals(subTasks, taskManager.getSubTasks()),
                ()->assertEquals(2,taskManager.getSubTasks().get(3).getEpicTaskNumber()),
                ()->assertNotNull(taskManager.getSubTasks())
        );
        taskManager.deleteAllSubTasks();
        assertNotEquals(subTasks,taskManager.getSubTasks());
        subTasks.clear();
        assertEquals(subTasks,taskManager.getSubTasks());
    }
    @Test
    public void shouldGetListTasks(){
        Collection catalogue = new ArrayList();
        catalogue.add(new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        Map tasks = taskManager.getTasks();
        assertEquals(catalogue, taskManager.getTasksСatalogue(tasks));
        Assertions.assertNotNull(taskManager.getTasksСatalogue(tasks));
        taskManager.deleteAllTasks();
        tasks = taskManager.getTasks();
        assertNotEquals(catalogue,taskManager.getTasksСatalogue(tasks));
        catalogue.clear();
        assertEquals(catalogue,taskManager.getTasksСatalogue(tasks));
    }
    @Test
    public void shouldGetListEpicTasks(){
        Collection catalogue = new ArrayList();
        List<Integer> subTasksCatalogue = new ArrayList<>();
        subTasksCatalogue.add(3);
        subTasksCatalogue.add(4);
        subTasksCatalogue.add(5);
        catalogue.add(new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                subTasksCatalogue));
        Map epicTasks = taskManager.getEpicTasks();
        assertEquals(catalogue, taskManager.getEpicTasksСatalogue(epicTasks));
        Assertions.assertNotNull(taskManager.getEpicTasksСatalogue(epicTasks));
        taskManager.deleteAllEpicTasks();
        epicTasks = taskManager.getEpicTasks();
        assertNotEquals(catalogue,taskManager.getTasksСatalogue(epicTasks));
        catalogue.clear();
        assertEquals(catalogue,taskManager.getEpicTasksСatalogue(epicTasks));
    }
    @Test
    public void shouldGetListSubTasks(){
        Collection catalogue = new ArrayList();
        catalogue.add(new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        catalogue.add(new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        catalogue.add(new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2));
        Map subTasks = taskManager.getSubTasks();
        assertEquals(catalogue, taskManager.getSubTasksСatalogue(subTasks));
        Assertions.assertNotNull(taskManager.getSubTasksСatalogue(subTasks));
        taskManager.deleteAllSubTasks();
        subTasks = taskManager.getSubTasks();
        assertNotEquals(catalogue,taskManager.getSubTasksСatalogue(subTasks));
        catalogue.clear();
        assertEquals(catalogue,taskManager.getSubTasksСatalogue(subTasks));
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
        Map<Integer, Task> tasks = new HashMap<>();
        taskManager.deleteAllTasks();
        assertEquals(tasks, taskManager.getTasks());
        tasks.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertNotEquals(tasks,taskManager.getTasks()),
                ()->assertNotNull(taskManager.getTasks())
        );
    }
    @Test
    public void shouldDeleteAllEpicTask() {
        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        taskManager.deleteAllEpicTasks();
        assertEquals(epicTasks, taskManager.getEpicTasks());
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, catalogue));
        assertAll(
                ()->assertNotEquals(epicTasks, taskManager.getEpicTasks()),
                ()->assertNotNull(taskManager.getEpicTasks())
        );
    }
    @Test
    public void shouldDeleteAllSubTask() {
        Map<Integer, SubTask> subTasks = new HashMap<>();
        taskManager.deleteAllSubTasks();
        assertEquals(subTasks, taskManager.getSubTasks());
        subTasks.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertNotEquals(subTasks,taskManager.getSubTasks());
        Assertions.assertNotNull(taskManager.getSubTasks());
    }
    @Test
    public void shouldGetTaskByID() {
        Map<Integer, Task> tasks = new HashMap<>();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        tasks.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW,
                startTime,15));
        assertEquals(tasks.get(1), taskManager.getTasks().get(1));
        taskManager.deleteAllTasks();
        assertAll(
                ()->assertNotEquals(tasks.get(1),taskManager.getTasks().get(1)),
                ()->assertNull(taskManager.getTasks().get(1))
        );
    }
    @Test
    public void shouldGetEpicTaskByID() {
        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, catalogue));
        assertEquals(epicTasks.get(2), taskManager.getEpicTasks().get(2));
        taskManager.deleteAllEpicTasks();
        assertAll(
                ()->assertNotEquals(epicTasks.get(2),taskManager.getEpicTasks().get(2)),
                ()->assertNull(taskManager.getEpicTasks().get(2))
        );
    }
    @Test
    public void shouldGetSubTaskByID() {
        Map<Integer, SubTask> subTaks = new HashMap<>();
        subTaks.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertEquals(subTaks.get(3), taskManager.getSubTasks().get(3));
        taskManager.deleteAllSubTasks();
        assertAll(
                ()->assertNotEquals(subTaks.get(3),taskManager.getSubTasks().get(3)),
                ()->assertNull(taskManager.getSubTasks().get(3))
        );
    }
    @Test
    public void shouldMakeTask() {
        Map<Integer, Task> tasks = new HashMap<>();
        tasks.put(1, new Task("Проектирование", "Проектирование ПО", 1, Status.NEW));
        assertAll(
                ()->assertEquals(tasks.get(1), taskManager.getTasks().get(1)),
                ()->assertNull(taskManager.getTasks().get(2)),
                ()->assertNotNull(taskManager.getTasks().get(1)),
                ()->assertNotEquals(tasks.get(1),taskManager.getTasks().get(0))
        );
    }
    @Test
    public void shouldMakeEpicTask() {
        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, catalogue));
        assertAll(
                ()->assertEquals(epicTasks.get(2), taskManager.getEpicTasks().get(2)),
                ()->assertNull(taskManager.getEpicTasks().get(1)),
                ()->assertNotNull(taskManager.getEpicTasks().get(2)),
                ()->assertNotEquals(epicTasks.get(2),taskManager.getEpicTasks().get(1))
        );
    }
    @Test
    public void shouldMakeSubTask() {
        Map<Integer, SubTask> map = new HashMap<>();
        map.put(3, new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2));
        assertAll(
                ()->assertEquals(map.get(3), taskManager.getSubTasks().get(3)),
                ()->assertNull(taskManager.getSubTasks().get(7)),
                ()->assertNotNull(taskManager.getSubTasks().get(3)),
                ()->assertNotEquals(map.get(3),taskManager.getSubTasks().get(4))
        );
    }
    @Test
    public void shouldDeleteTaskByID() {
        Map<Integer, EpicTask> map = new HashMap<>();
        taskManager.deleteTaskById(1);
        assertAll(
                ()->assertEquals(map, taskManager.getTasks()),
                ()->assertNull(taskManager.getTasks().get(1))
        );
    }
    @Test
    public void shouldDeleteEpicTaskByID() {
        Map<Integer, EpicTask> epicTasks= new HashMap<>();
        Map<Integer, SubTask> subTasks = new HashMap<>();
        taskManager.deleteEpicTaskById(2);
        assertAll(
                ()->assertEquals(epicTasks, taskManager.getEpicTasks()),
                ()->assertEquals(subTasks, taskManager.getSubTasks()),
                ()->assertNull(taskManager.getEpicTasks().get(2))
        );
    }
    @Test
    public void shouldDeleteSubTaskByID() {
        Map<Integer, SubTask> subTasks = new HashMap<>();
        subTasks.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subTasks.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW, 2));
        taskManager.deleteSubTaskById(3);
        assertAll(
                ()->assertEquals(subTasks, taskManager.getSubTasks()),
                ()->assertNull(taskManager.getSubTasks().get(3))
        );
    }
    @Test
    public void shouldDeleteSubTaskByIDIDEpicTask() {
        Map<Integer, SubTask> subTasks = new HashMap<>();
        subTasks.put(4, new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2));
        subTasks.put(5, new SubTask("Класс тестирования", "Разработка класа тестирования", 5,
                Status.NEW, 2));
        taskManager.deleteSubTaskById(3);

        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW, catalogue));
        assertEquals(epicTasks, taskManager.getEpicTasks());
    }
    @Test
    public void shouldUpdateTaskById(){
        Map<Integer, Task> tasks = new HashMap<>();
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,12,
                10));
        tasks.put(1, new Task("Проектирование", "Проектирование ПО",1, Status.DONE, startTime,
                15));
        assertNotEquals(tasks.get(1), taskManager.getTasks().get(1));
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.DONE, startTime,
                15);
        taskManager.updateTaskById(task);
        assertAll(
                ()->assertEquals(tasks.get(1),taskManager.getTasks().get(1)),
                ()->assertNotNull(taskManager.getTasks().get(1))
        );
    }
    @Test
    public void shouldUpdateEpicTaskById(){
        Map<Integer, EpicTask> epicTasks = new HashMap<>();
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        epicTasks.put(2, new EpicTask("Unit тесты", "Разработка unit тестов", 2, Status.NEW, catalogue));
        assertNotEquals(epicTasks.get(2),taskManager.getEpicTasks().get(2));
        EpicTask epicTask= taskManager.getEpicTasks().get(2);
        epicTask.setName("Unit тесты");
        epicTask.setDescription("Разработка unit тестов");
        taskManager.updateEpicTaskById(epicTask);
        assertAll(
                ()->assertEquals(epicTasks.get(2), taskManager.getEpicTasks().get(2)),
                ()->assertNotNull(taskManager.getEpicTasks().get(2))
        );
    }
    @Test
    public void shouldUpdateSubTaskById(){
        Map<Integer, SubTask> subTasks = new HashMap<>();
        subTasks.put(3, new SubTask("Разработка консоли", "Разработка меню консоли", 3, Status.NEW,
                2));
        assertNotEquals(subTasks.get(3),taskManager.getSubTasks().get(3));
        SubTask subTask= taskManager.getSubTasks().get(3);
        subTask.setName("Разработка консоли");
        subTask.setDescription("Разработка меню консоли");
        taskManager.updateSubTaskById(subTask);
        assertAll(
                ()->assertEquals(subTasks.get(3), taskManager.getSubTasks().get(3)),
                ()->assertNotNull(taskManager.getSubTasks().get(3))
        );
    }
    @Test
    public void shouldGetAllSubTaskInEpic(){
        List<Integer> catalogue = new ArrayList<>();
        catalogue.add(3);
        catalogue.add(4);
        catalogue.add(5);
        EpicTask epicTask = taskManager.getEpicTasks().get(2);
        assertAll(
                ()->assertEquals(catalogue,epicTask.getSubtaskCatalogue()),
                ()->assertNotNull(epicTask.getSubtaskCatalogue())
        );
    }
    @Test
    public void shouldGetTime(){
        assertAll(
                ()->assertEquals("2022-01-01T12:10", taskManager.getTasks().get(1).getStartTime().get()
                .toString()),
                ()->assertEquals("2022-01-05T14:20", taskManager.getEpicTasks().get(2).getStartTime().get()
                .toString()),
                ()->assertEquals("2022-01-15T20:40", taskManager.getEpicTasks().get(2).getEndTime().get()
                .toString()),
                ()->assertEquals("2022-01-05T14:20", taskManager.getSubTasks().get(3).getStartTime().get()
                .toString())
        );
    }

    @Test
    public void shouldGetDuration(){
        assertAll(
                ()->assertEquals(15, taskManager.getTasks().get(1).getDuration()),
        ()->assertEquals(440, taskManager.getEpicTasks().get(2).getDuration()),
        ()->assertEquals(50, taskManager.getSubTasks().get(3).getDuration())
        );
    }

    @Test
    public void getPrioritizedTasks(){
        LinkedList<Task> catalogue = new LinkedList<>();
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
