package bussinesslogic;

import com.google.gson.Gson;
import maketbussinesslogic.HistoryManager;
import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

public class HTTPTaskManager extends FileBackedTasksManager{

    Gson gson = new Gson();
    KVTaskClient kvTaskClient = new KVTaskClient();


    public HTTPTaskManager(String path) {
        super(path);
    }

    String token;

    public String getToken() {
        kvTaskClient.getTokenFromServer(super.getPath());
        return kvTaskClient.getToken();
    }

    @Override
    public Map<Integer, Task> getTaskMap() {

        return super.getTaskMap();
    }

    @Override
    public Map<Integer, EpicTask> getEpicTaskMap() {
        return super.getEpicTaskMap();
    }

    @Override
    public Map<Integer, SubTask> getSubTaskMap() {
        return super.getSubTaskMap();
    }

    @Override
    public Collection getListTasks(Map<Integer, Task> mapTask) {
        return super.getListTasks(mapTask);
    }

    @Override
    public Collection getListEpicTasks(Map<Integer, EpicTask> mapEpicTask) {
        return super.getListEpicTasks(mapEpicTask);
    }

    @Override
    public List<SubTask> getListSubTasks(Map<Integer, SubTask> mapSubTask) {
        return super.getListSubTasks(mapSubTask);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
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
//        kvTaskClient.getTokenFromServer(super.getPath());
//        Task task = super.makeTask(name, description, startTime, duration);
        return super.makeTask(name, description, startTime, duration);
    }

    @Override
    public EpicTask makeEpic(String name, String description) {
        return super.makeEpic(name, description);
    }

    @Override
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration) {
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
    public String toString(Task task) {
        return super.toString(task);
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    @Override
    public void saveToFile() {

        Collection listTask = super.getListTasks(super.getTaskMap());
        Collection listEpicTask = super.getListEpicTasks(super.getEpicTaskMap());
        Collection listSubTask = super.getListSubTasks(super.getSubTaskMap());
        Collection listHistoryManager = super.getHistoryManager().getHistory();
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

    @Override
    public void fromFile() throws IOException {

        Map<Integer, Task> taskMap = new HashMap<>();
        Map<Integer, EpicTask> epicTaskMap = this.getEpicTaskMap();
        Map<Integer, SubTask> subTaskMap = this.getSubTaskMap();
        HistoryManager historyManager = this.getHistoryManager();
        String str = kvTaskClient.load(getToken());
        System.out.println(str);
        String[] strBuffer = str.split(", ");
        String strTaskMap = strBuffer[0];
        String strEpicTaskMap = strBuffer[1];
        String strSubTaskMap = strBuffer[2];
        String strHistory = strBuffer[3];


       String jsonTaskMap = gson.toJson(strTaskMap);
        strTaskMap = strTaskMap.replace("},{", "}spl{");
        strTaskMap = strTaskMap.replace("[[","");
        strTaskMap = strTaskMap.replace("}]","}");
        String[] bufferGsonTask = strTaskMap.split("spl");

        List<String> jsonListTaskMap =Arrays.asList(bufferGsonTask);

        for (String sting: jsonListTaskMap){
            System.out.println(sting);
            Task task = gson.fromJson(sting, Task.class);
            taskMap.put(task.getId(),task);
        }
        System.out.println(taskMap);
    }
}
