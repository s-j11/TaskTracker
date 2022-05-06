package test;

import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTaskTraker {

    private Managers managers = new Managers();
    private TaskManager inFileBackedTaksManager = managers.getDefaultFileBackedManager("src/store/test.csv");
    private HistoryManager historyManager = managers.getDefaultHistory();
    private Map<Integer, Task> taskHashMap;
    private Map<Integer, EpicTask> epicTaskMap;
    private Map<Integer, SubTask> subTaskMap;
    private Node node = null;
    private List<Node> history = new ArrayList<Node>();
    private Map<Integer, Node> indexMap = new HashMap<Integer, Node>() ;

    public TestTaskTraker(Map<Integer, Task> taskHashMap, Map<Integer, EpicTask> epicTaskMap,
                          Map<Integer, SubTask> subTaskMap) {
        this.taskHashMap = taskHashMap;
        this.epicTaskMap = epicTaskMap;
        this.subTaskMap = subTaskMap;
    }

    public void testEpicTask () {
        System.out.println("Добавление задач");
        EpicTask epicTask = inFileBackedTaksManager.makeEpic("Разработка", "Разработка приложения");
        EpicTask epicTask1 = inFileBackedTaksManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inFileBackedTaksManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 1);
        SubTask subTask1 = inFileBackedTaksManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 1);
        SubTask subTask2 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);
        System.out.println("=======================================================================================");
        System.out.println("Просмотр задач по ID и проверка функции history ");
        inFileBackedTaksManager.getEpicTaskById(1);
        inFileBackedTaksManager.getEpicTaskById(2);
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println();
        inFileBackedTaksManager.getSubTaskById(3);
        inFileBackedTaksManager.getSubTaskById(4);
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println();
        inFileBackedTaksManager.getEpicTaskById(2);
        inFileBackedTaksManager.getEpicTaskById(1);
        inFileBackedTaksManager.getSubTaskById(5);
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println();
        inFileBackedTaksManager.getSubTaskById(4);
        inFileBackedTaksManager.getSubTaskById(3);
        inFileBackedTaksManager.getSubTaskById(5);
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println();
        inFileBackedTaksManager.getSubTaskById(5);
        inFileBackedTaksManager.getSubTaskById(3);
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println("=======================================================================================");
        System.out.println("Очикстра временной памяти ");
        System.out.println(inFileBackedTaksManager.getTaskMap());
        System.out.println(inFileBackedTaksManager.getEpicTaskMap());
        System.out.println(inFileBackedTaksManager.getSubTaskMap());
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        inFileBackedTaksManager.getTaskMap().clear();
        inFileBackedTaksManager.getEpicTaskMap().clear();
        inFileBackedTaksManager.getSubTaskMap().clear();
        inFileBackedTaksManager.getHistoryManager().clearHistory();
        System.out.println(inFileBackedTaksManager.getTaskMap());
        System.out.println(inFileBackedTaksManager.getEpicTaskMap());
        System.out.println(inFileBackedTaksManager.getSubTaskMap());
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println("=======================================================================================");
        System.out.println("Восстановление из файла ");
        try {
            inFileBackedTaksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(inFileBackedTaksManager.getTaskMap());
        System.out.println(inFileBackedTaksManager.getEpicTaskMap());
        System.out.println(inFileBackedTaksManager.getSubTaskMap());
        System.out.println(inFileBackedTaksManager.getHistoryManager().getHistory());
        System.out.println();
    }
}
