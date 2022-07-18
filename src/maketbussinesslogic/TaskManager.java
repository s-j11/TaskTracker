package maketbussinesslogic;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    //Получение map Tasks
    Map<Integer, Task> getTaskMap();

    //Получение map EpicTasks
    Map<Integer, EpicTask> getEpicTaskMap();

    //Получение map SubTasks
    Map<Integer, SubTask> getSubTaskMap();

    //Получение списка всех задач model.Task.
    Collection getListTasks(Map<Integer, Task> mapTask);

    //Получение списка всех Эпик задач model.EpicTask.
    Collection getListEpicTasks(Map<Integer, EpicTask> mapEpicTask);

    //Получение списка всех подзадач model.SubTask.
    List<SubTask> getListSubTasks(Map<Integer, SubTask> mapSubTask);

    //Получение historyManagera
    HistoryManager getHistoryManager();

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
    Task makeTask(String name, String description, LocalDateTime startTime, int duration);

    //Создание задачи model.EpicTask
    EpicTask makeEpic(String name, String description);

    //Создание задачи model.SubTask
    SubTask makeSubTask(String name, String description, int id, LocalDateTime startTime, int duration);

    //Удаление задачи model.Task по идентификатору.
    void deleteTaskById(int key);

    //Удаление задачи model.EpicTask по идентификатору.
    void deleteEpicTaskById(int key);

    //Удаление задачи model.SubTask по идентификатору.
    void deleteSubTaskById(int key);

    //Обнавление задачи model.Task
    Map<Integer, Task> updateTaskById(Task taskUpdate);

    //Обнавление задачи model.EpicTask
    void updateEpicTaskById(EpicTask taskUpdate);

    //Обнавление задачи model.SubTask
    void updateSubTaskById(SubTask taskUpdate);

    //Получение всех задач Эпик задачи
    void getAllSubTaskInEpic(int key);

    //Чтение данных из файла.
    void fromFile() throws IOException;
}
