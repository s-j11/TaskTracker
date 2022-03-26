package test;

import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTaskTraker {

    private Managers managers = new Managers();
    private TaskManager inMemoryTaskManager = managers.getDefault();
    private HistoryManager historyManager = managers.getDefaultHistory();
    private Map<Integer, Task> taskHashMap;
    private Map<Integer, EpicTask> epicTaskMap;
    private Map<Integer, SubTask> subTaskMap;
    private Node node = null;
    private List<Node> history = new ArrayList<Node>();
    private Map<Integer, Node> indexMap = new HashMap<Integer, Node>() ;


    public TestTaskTraker(Map<Integer, Task> taskHashMap, Map<Integer, EpicTask> epicTaskMap, Map<Integer, SubTask> subTaskMap) {
        this.taskHashMap = taskHashMap;
        this.epicTaskMap = epicTaskMap;
        this.subTaskMap = subTaskMap;
    }

    public void testEpicTask () {
        System.out.println("Добавление задач");
        EpicTask epicTask = inMemoryTaskManager.makeEpic("Разработка", "Разработка приложения");
        EpicTask epicTask1 = inMemoryTaskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inMemoryTaskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 1);
        SubTask subTask1 = inMemoryTaskManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 1);
        SubTask subTask2 = inMemoryTaskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);
        System.out.println("=======================================================================================");
        System.out.println("Просмотр задач по ID и проверка функции history ");
        inMemoryTaskManager.getEpicTaskById(1);
        inMemoryTaskManager.getEpicTaskById(2);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
        inMemoryTaskManager.getSubTaskById(3);
        inMemoryTaskManager.getSubTaskById(4);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
        inMemoryTaskManager.getEpicTaskById(2);
        inMemoryTaskManager.getEpicTaskById(1);
        inMemoryTaskManager.getSubTaskById(5);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(3);
        inMemoryTaskManager.getSubTaskById(5);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getSubTaskById(3);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println("=======================================================================================");
        System.out.println("Проверка удаления ");
        node = new Node(epicTask1);
        inMemoryTaskManager.getHistoryManager().remove(node);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
        System.out.println("=======================================================================================");
        System.out.println("Проверка удаления группы ");
        node = new Node(epicTask);
        inMemoryTaskManager.getHistoryManager().remove(node);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node(subTask);
        inMemoryTaskManager.getHistoryManager().remove(node);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node(subTask1);
        inMemoryTaskManager.getHistoryManager().remove(node);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        node = new Node(subTask2);
        inMemoryTaskManager.getHistoryManager().remove(node);
        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println();
    }
}
