package test;

import bussinesslogic.InMemoryHistoryManager;
import bussinesslogic.InMemoryTaskManager;
import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private Managers managers = new Managers();
    private TaskManager inMemoryTaskManager = managers.getDefault();
    private  HistoryManager historyManager =  new InMemoryHistoryManager();
    private List<Node> historyList = new ArrayList<>();
    private  Node<Task> node;

    @BeforeEach
    public void shouldPreparedTestEnvironment(){
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        Optional<LocalDateTime> startTime1 = Optional.of(LocalDateTime.of(2022,1,5,15,
                30));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        Optional<LocalDateTime> startTime3 = Optional.of(LocalDateTime.of(2022,1,15,15,
                30));
        Task task = inMemoryTaskManager.makeTask("Проектирование","Проектирование ПО",
                        startTime, 15);
        EpicTask epicTask = inMemoryTaskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inMemoryTaskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2,startTime1,30);
        SubTask subTask1 = inMemoryTaskManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2,startTime2,30);
        SubTask subTask2 = inMemoryTaskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2,startTime3, 30);
    }
    @Test
    public void shouldAdd() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        inMemoryTaskManager.getTaskById(1);
        assertAll(
                ()-> assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory()),
                ()->assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory())
        );
    }

    @Test
    public void shouldRemove() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        node = new Node<Task>(task);
        historyManager.add(task);
        inMemoryTaskManager.getTaskById(1);
        assertAll(
                ()->assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory()),
                ()->assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory())
        );
        historyManager.remove(node);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().remove(node);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldGetHistory() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        Assertions.assertNotEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getTaskById(1);
        assertAll(
                ()->assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory()),
                ()->assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory())
        );
    }

    @Test
    public void shouldClearHistory() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Map<Integer, EpicTask> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        Assertions.assertNotEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getTaskById(1);
        assertAll(
                ()->assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory()),
        ()->assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory())
        );
        historyManager.add(epicTask);
        inMemoryTaskManager.getEpicTaskById(2);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.removeAllNode();
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().removeAllNode();
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldRemoveAllNode() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        Assertions.assertNotEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getTaskById(1);
        assertAll(
                ()->assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory()),
                ()->assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory())
        );
        historyManager.add(epicTask);
        inMemoryTaskManager.getEpicTaskById(2);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.removeAllNode();
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().removeAllNode();
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldEmptyHistory(){
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldDuplicate(){
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
        historyManager.add(task);
        historyManager.add(epicTask);
        inMemoryTaskManager.getTaskById(1);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getEpicTaskById(2);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getTaskById(1);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void shouldDeleteBeginMiddleEnd(){
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        EpicTask epicTask = new EpicTask("Тестирование", "Разработка тестирования", 2, Status.NEW,
                list);
        SubTask subTask = new SubTask("Разработка меню", "Разработка класса меню", 3, Status.NEW,
                2);
        SubTask subTask1 = new SubTask("Разработка логики", "Разработка класса логики", 4, Status.NEW,
                2);
        SubTask subTask2 = new SubTask("Класс тестирования", "Разработка класа тестирования", 5, Status.NEW,
                2);

        historyManager.add(task);
        historyManager.add(epicTask);
        historyManager.add(subTask);
        historyManager.add(subTask1);
        historyManager.add(subTask2);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getEpicTaskById(2);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getSubTaskById(3);
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node<Task>(task);
        historyManager.remove(node);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().remove(node);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node<Task>(subTask);
        historyManager.remove(node);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().remove(node);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node<Task>(subTask2);
        historyManager.remove(node);
        Assertions.assertNotEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        inMemoryTaskManager.getHistoryManager().remove(node);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }


}