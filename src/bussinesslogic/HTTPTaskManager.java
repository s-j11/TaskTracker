package bussinesslogic;

import com.google.gson.*;
import maketbussinesslogic.HistoryManager;
import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class HTTPTaskManager extends FileBackedTasksManager{

   private final Gson gson = new Gson();
   private final KVTaskClient kvTaskClient = new KVTaskClient();


    public HTTPTaskManager(String path) {
        super(path);
    }

    String token;

    public String getToken() {
        kvTaskClient.getTokenFromServer(super.getPath());
        return kvTaskClient.getToken();
    }

    @Override
    public Map<Integer, Task> getTasks() {

        return super.getTasks();
    }

    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        return super.getEpicTasks();
    }

    @Override
    public Map<Integer, SubTask> getSubTasks() {
        return super.getSubTasks();
    }

    @Override
    public Collection getTasksСatalogue(Map<Integer, Task> tasks) {
        return super.getTasksСatalogue(tasks);
    }

    @Override
    public Collection getEpicTasksСatalogue(Map<Integer, EpicTask> epicTasks) {
        return super.getEpicTasksСatalogue(epicTasks);
    }

    @Override
    public List<SubTask> getSubTasksСatalogue(Map<Integer, SubTask> subTasks) {
        return super.getSubTasksСatalogue(subTasks);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
    }

    @Override
    public void deleteAllEpicTasks() {
        super.deleteAllEpicTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
    }

    @Override
    public void getTaskById(int key) {
        super.getTaskById(key);
    }

    @Override
    public void getEpicTaskById(int key) {
        super.getEpicTaskById(key);
    }

    @Override
    public void getSubTaskById(int key) {
        super.getSubTaskById(key);
    }

    @Override
    public Task makeTask(String name, String description, Optional<LocalDateTime> startTime, int duration) {
        return super.makeTask(name, description, startTime, duration);
    }

    @Override
    public EpicTask makeEpic(String name, String description) {
        return super.makeEpic(name, description);
    }

    @Override
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration)
    {
        return super.makeSubTask(name, description, id, startTime, duration);
    }

    @Override
    public void deleteTaskById(int key) {
        super.deleteTaskById(key);
    }

    @Override
    public void deleteEpicTaskById(int key) {
        super.deleteEpicTaskById(key);
    }

    @Override
    public void deleteSubTaskById(int key) {
        super.deleteSubTaskById(key);
    }

    @Override
    public Map<Integer, Task> updateTaskById(Task taskUpdate) {
        return super.updateTaskById(taskUpdate);
    }

    @Override
    public void updateEpicTaskById(EpicTask taskUpdate) {
        super.updateEpicTaskById(taskUpdate);
    }

    @Override
    public void updateSubTaskById(SubTask taskUpdate) {
        super.updateSubTaskById(taskUpdate);
    }

    @Override
    public void getAllSubTaskInEpic(int key) {
        super.getAllSubTaskInEpic(key);
    }

    @Override
    public String ConvertToString(Task task) {
        return super.ConvertToString(task);
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    //Метод переписан под работу с сервером хранилищем
    @Override
    public void saveToFile() {

        Collection listTask = this.getTasksСatalogue(this.getTasks());
        Collection listEpicTask = this.getEpicTasksСatalogue(this.getEpicTasks());
        Collection listSubTask = getSubTasksСatalogue(this.getSubTasks());
        Collection listHistoryManager = getHistoryManager().getHistory();
        String task = gson.toJson(listTask);
        String epicTask = gson.toJson(listEpicTask);
        String subTask = gson.toJson(listSubTask);
        String history = gson.toJson(listHistoryManager);

        Collection allTasks = new ArrayList<>();
        allTasks.add(task);
        allTasks.add(epicTask);
        allTasks.add(subTask);
        allTasks.add(history);
        String response = allTasks.toString();
       kvTaskClient.put(getToken(),response);
    }

    //Метод переписан под работу с сервером хранилищем
    @Override
    public void fromFile() throws IOException {

        Map<Integer, Task> tasks = this.getTasks();
        Map<Integer, EpicTask> epicTasks = this.getEpicTasks();
        Map<Integer, SubTask> subTasks = this.getSubTasks();
        HistoryManager historyManager = getHistoryManager();

    try {
        String str = kvTaskClient.load(getToken());

            JsonElement jsonElement = JsonParser.parseString(str);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            JsonArray jsonArrayTask = jsonArray.get(0).getAsJsonArray();
            JsonArray jsonArrayEpicTask = jsonArray.get(1).getAsJsonArray();
            JsonArray jsonArraySubTask = jsonArray.get(2).getAsJsonArray();
            JsonArray jsonArrayHistory = jsonArray.get(3).getAsJsonArray();

            for (JsonElement element : jsonArrayTask) {
                Task task = gson.fromJson(element, Task.class);
                tasks.put(task.getId(), task);
            }

            for (JsonElement element : jsonArrayEpicTask) {
                EpicTask task = gson.fromJson(element, EpicTask.class);
                epicTasks.put(task.getId(), task);
            }

            for (JsonElement element : jsonArraySubTask) {
                SubTask task = gson.fromJson(element, SubTask.class);
                subTasks.put(task.getId(), task);
            }

            for (JsonElement element : jsonArrayHistory) {
                Task task = gson.fromJson(element, Task.class);
                historyManager.add(task);
            }
                } catch (NullPointerException e){
            System.out.println();
            System.out.println("Данных еще нет в хранилище");
            System.out.println();
        }
    }
}
