package maketbussinesslogic;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    //Получение map Tasks
    HashMap<Integer, Task> getTaskMap();

    //Получение map EpicTasks
    HashMap<Integer, EpicTask> getEpicTaskMap();

    //Получение map SubTasks
    HashMap<Integer, SubTask> getSubTaskMap();

    //Получение списка всех задач model.Task.
    List<Task> getListTasks(HashMap<Integer, Task> mapTask);

    //Получение списка всех Эпик задач model.EpicTask.
    List<EpicTask> getListEpicTasks(HashMap<Integer, EpicTask> mapEpicTask);

    //Получение списка всех подзадач model.SubTask.
    List<SubTask> getListSubTasks(HashMap<Integer, SubTask> mapSubTask);

    //Удаление всех задач model.Task.
    void deleteAllTask();

    //Удаление всех эпик задач model.EpicTask.
    void deleteAllEpic();

    //Удаление всех подзадач subTask.
    void deleteAllSubTask();

    //Получение задачи model.Task по идентификатору.
    void getTaskById(int key);

    //Получение задачи model.EpicTask по идентификатору.
    void getEpicTaskById(int key);

    //Получение задачи model.SubTask по идентификатору.
    void getSubTaskById(int key);

    //Создание задачи model.Task
    Task makeTask(String name, String description);

    //Создание задачи model.EpicTask
    EpicTask makeEpic(String name, String description);

    //Создание задачи model.SubTask
    SubTask makeSubTask(String name, String description, int id);

    //Удаление задачи model.Task по идентификатору.
    void deleteTaskById(int key);

    //Удаление задачи model.EpicTask по идентификатору.
    void deleteEpicTaskById(int key);

    //Удаление задачи model.SubTask по идентификатору.
    void deleteSubTaskById(int key);

    //Обнавление задачи model.Task
    HashMap<Integer, Task> updateTaskById(Task taskUpdate);

    //Обнавление задачи model.EpicTask
    void updateEpicTaskById(EpicTask taskUpdate);

    //Обнавление задачи model.SubTask
    void updateSubTaskById(SubTask taskUpdate);

    //Получение всех задач Эпик задачи
    void getAllSubTaskInEpic(int key);

    //Вывод содержания maps

    //История последних 10 сообщений
    void history();

    //Очистка Истории после тестирования
    void clearHistory();
}
