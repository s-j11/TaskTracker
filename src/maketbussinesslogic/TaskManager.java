package maketbussinesslogic;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskManager {

    //Получение map Tasks
    Map<Integer, Task> getTasks();

    //Получение map EpicTasks
    Map<Integer, EpicTask> getEpicTasks();

    //Получение map SubTasks
    Map<Integer, SubTask> getSubTasks();

    //Получение списка всех задач model.Task.
    Collection getTasksСatalogue(Map<Integer, Task> mapTask);

    //Получение списка всех Эпик задач model.EpicTask.
    Collection getEpicTasksСatalogue(Map<Integer, EpicTask> mapEpicTask);

    //Получение списка всех подзадач model.SubTask.
    List<SubTask> getSubTasksСatalogue(Map<Integer, SubTask> mapSubTask);

    //Получение historyManagera
    HistoryManager getHistoryManager();

    //Удаление всех задач model.Task.
    void deleteAllTasks();

    //Удаление всех эпик задач model.EpicTask.
    void deleteAllEpicTasks();

    //Удаление всех подзадач subTask.
    void deleteAllSubTasks();

    //Получение задачи model.Task по идентификатору.
    void getTaskById(int key);

    //Получение задачи model.EpicTask по идентификатору.
    void getEpicTaskById(int key);

    //Получение задачи model.SubTask по идентификатору.
    void getSubTaskById(int key);

    //Создание задачи model.Task
    Task makeTask(String name, String description, Optional<LocalDateTime> startTime, int duration);

    //Создание задачи model.EpicTask
    EpicTask makeEpic(String name, String description);

    //Создание задачи model.SubTask
    SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration);

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

    //Получение задач по приоретету времени
    Collection<Task> getPrioritizedTasks();

    //Чтение данных из файла.
    void fromFile() throws IOException;
}
