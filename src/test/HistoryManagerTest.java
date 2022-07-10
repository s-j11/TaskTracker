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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private Managers managers = new Managers();
    private TaskManager inMemoryTaskManager = managers.getDefault();
    private  HistoryManager historyManager =  inMemoryTaskManager.getHistoryManager();
    private List<Node> historyList = new ArrayList<>();
    private  Node<Task> node;

    @BeforeEach
    public void shouldPreparedTestEnvironment(){
        Task task = inMemoryTaskManager.makeTask("Проектирование","Проектирование ПО");
        EpicTask epicTask = inMemoryTaskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inMemoryTaskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2);
        SubTask subTask1 = inMemoryTaskManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 2);
        SubTask subTask2 = inMemoryTaskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 2);
    }
    @Test
    void shouldAdd() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        Assertions.assertEquals(historyManager.getHistory(), inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.add(task);
        inMemoryTaskManager.getTaskById(1);
        Assertions.assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory());
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    void shouldRemove() {
        Task task = new Task("Проектирование", "Проектирование ПО", 1, Status.NEW);
        node = new Node<Task>(task);
        historyManager.add(task);
        inMemoryTaskManager.getTaskById(1);
        Assertions.assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory());
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
        historyManager.remove(node);
        Assertions.assertEquals(historyManager.getHistory(),inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    void shouldGetHistory() {
    }

    @Test
    void shouldClearHistory() {
    }

    @Test
    void shouldRemoveAllNode() {
    }
}