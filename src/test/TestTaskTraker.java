package test;

import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.HashMap;

public class TestTaskTraker {

    Managers managers = new Managers();
    TaskManager inMemoryTaskManager = managers.getDefault();
    HistoryManager historyManager = managers.getDefaultHistory();
    private HashMap<Integer, Task> taskHashMap;
    private HashMap<Integer, EpicTask> epicTaskMap;
    private HashMap<Integer, SubTask> subTaskMap;

    public TestTaskTraker(HashMap<Integer, Task> taskHashMap, HashMap<Integer, EpicTask> epicTaskMap,
                          HashMap<Integer, SubTask> subTaskMap) {
        this.taskHashMap = taskHashMap;
        this.epicTaskMap = epicTaskMap;
        this.subTaskMap = subTaskMap;
    }

    public void testEpicTask (){
        System.out.println("Добавление задач");
        Task task = inMemoryTaskManager.makeTask("Планирование","Разработка плана");
        EpicTask epicTask = inMemoryTaskManager.makeEpic("Разработка", "Разработка приложения");
        EpicTask epicTask1 = inMemoryTaskManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inMemoryTaskManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 2);
        SubTask subTask1 = inMemoryTaskManager.makeSubTask("Разработка логики",
                "Разработка класса логики",2);
        SubTask subTask2 = inMemoryTaskManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 3);
        System.out.println("=======================================================================================");
        System.out.println("Просмотр задач по ID и проверка функции history ");
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getEpicTaskById(2);
        inMemoryTaskManager.getEpicTaskById(3);
        historyManager.getHistory();
        System.out.println();
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getSubTaskById(6);
        historyManager.getHistory();
        System.out.println();
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getEpicTaskById(2);
        inMemoryTaskManager.getEpicTaskById(3);
        inMemoryTaskManager.getSubTaskById(4);
        historyManager.getHistory();
        System.out.println();
        inMemoryTaskManager.getSubTaskById(1);
        inMemoryTaskManager.getSubTaskById(2);
        inMemoryTaskManager.getSubTaskById(3);
        historyManager.getHistory();
        System.out.println();
        inMemoryTaskManager.getSubTaskById(4);
        inMemoryTaskManager.getSubTaskById(5);
        inMemoryTaskManager.getSubTaskById(6);
        historyManager.getHistory();
        System.out.println("=======================================================================================");
        System.out.println("Изменение статусов");
        task = new Task("Планирование 2", "Разработака плана2", task.getId(), Status.IN_PROGRESS);
        taskHashMap = inMemoryTaskManager.updateTaskById(task);
        inMemoryTaskManager.updateEpicTaskById(epicTask1);
        subTask = new SubTask("Разработка меню 2", "Разработка класса меню 2", subTask.getId(),
                Status.IN_PROGRESS,subTask.getEpicTaskNumber());
        inMemoryTaskManager.updateSubTaskById(subTask);
        subTask1 = new SubTask("Разработка логики 2", "Разработка класса логики 2", subTask1.getId(),
                Status.DONE, subTask1.getEpicTaskNumber());
        inMemoryTaskManager.updateSubTaskById(subTask1);
        subTask2 = new SubTask("Класс тестирования 2", "Разработка класа тестирования 2",
                subTask2.getId(), Status.IN_PROGRESS, subTask2.getEpicTaskNumber());
        inMemoryTaskManager.updateSubTaskById(subTask2);
        epicTask = new EpicTask("Разработка 2","Разработка приложение 2", epicTask.getId(),
                epicTask.getStatus(),epicTask.getListSubtask());
        inMemoryTaskManager.updateEpicTaskById(epicTask);
        epicTask1 = new EpicTask("Тестирование 2","Разработка тестирования 2", epicTask1.getId(),
                epicTask1.getStatus(),epicTask1.getListSubtask());
        inMemoryTaskManager.updateEpicTaskById(epicTask1);
        System.out.println("=======================================================================================");
        System.out.println("Проверка статуса");
        System.out.println(task);
        System.out.println(subTask);
        System.out.println(subTask1);
        System.out.println(subTask2);
        System.out.println(epicTask);
        System.out.println(epicTask1);
        System.out.println("=======================================================================================");
        System.out.println("Изменение статусов");
        subTask = new SubTask("Разработка меню 3", "Разработка класса меню 3", subTask.getId(),
                Status.DONE,subTask.getEpicTaskNumber());
        inMemoryTaskManager.updateSubTaskById(subTask);
        subTask2 = new SubTask("Класс тестирования 3", "Разработка класа тестирования 3",
                subTask2.getId(), Status.DONE, subTask2.getEpicTaskNumber());
        inMemoryTaskManager.updateSubTaskById(subTask2);
        System.out.println("=======================================================================================");
        System.out.println("Проверка статуса");
        System.out.println(subTask);
        System.out.println(subTask2);
        System.out.println(epicTask);
        System.out.println(epicTask1);
        System.out.println("=======================================================================================");
        System.out.println("Удаление задач");
        inMemoryTaskManager.deleteTaskById(1);
        inMemoryTaskManager.deleteSubTaskById(4);
        inMemoryTaskManager.deleteEpicTaskById(3);
        System.out.println("=======================================================================================");
        System.out.println("Проверка изменение списка задач");
        System.out.println(inMemoryTaskManager.getTaskMap());
        System.out.println(inMemoryTaskManager.getEpicTaskMap());
        System.out.println(inMemoryTaskManager.getSubTaskMap());
        System.out.println("=======================================================================================");
        System.out.println("Очистка теситирования");
        taskHashMap.clear();
        epicTaskMap.clear();
        subTaskMap.clear();
        historyManager.clearHistory();
        System.out.println();
    }

}
