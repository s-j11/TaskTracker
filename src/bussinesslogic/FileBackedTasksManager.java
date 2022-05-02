package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.awt.*;
import java.awt.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, String> stringHashMap = new HashMap<>();

    public void setStringHashMap(HashMap<Integer, String> stringHashMap) {
        this.stringHashMap = stringHashMap;
    }

    private String path;

    private Reader reader;
//    {
//        try {
//            reader = new FileReader(path);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//


    public FileBackedTasksManager(String path) {
        this.path = path;
    }

    @Override
    public Map<Integer, Task> getTaskMap() {
       Map map = super.getTaskMap();
        return map;
    }

    @Override
    public Map<Integer, EpicTask> getEpicTaskMap() {
        Map map = super.getEpicTaskMap();
        return map;
    }

    @Override
    public Map<Integer, SubTask> getSubTaskMap() {
        Map map = super.getSubTaskMap();
        return map;
    }

    @Override
    public Collection getListTasks(Map<Integer, Task> mapTask) {
        Collection listTasks = super.getListTasks(mapTask);
        return listTasks;
    }

    @Override
    public Collection getListEpicTasks(Map<Integer, EpicTask> mapEpicTask) {
        Collection listEpicTask = super.getListEpicTasks(mapEpicTask);
        return listEpicTask;
    }

    @Override
    public Collection getListSubTasks(Map<Integer, SubTask> mapSubTask) {
        Collection listSubTasks = super.getListSubTasks(mapSubTask);
        return listSubTasks;
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
    public Task makeTask(String name, String description) {
        Task task = super.makeTask(name, description);
        String string = toString(task);
        writeStingHashMap(task.getId(), string);

        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return task;

    }

    @Override
    public EpicTask makeEpic(String name, String description) {
        EpicTask epicTask = super.makeEpic(name,description);
        String string = toString(epicTask);
        writeStingHashMap(epicTask.getId(), string);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return epicTask;
    }

    @Override
    public SubTask makeSubTask(String name, String description, int id) {
        SubTask subTask = super.makeSubTask(name,description,id);
        String string = toString(subTask);
        writeStingHashMap(subTask.getId(), string);
        writeStingHashMap(id,stringHashMap.get(id));
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return subTask;
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


    public String toString(Task task) {
        TypeTask typeTask = null;
        Integer numberEpic = 0;
//        Collection listSubtasks = null;
        String listSubtasks = "0";
        if (task.getClass() == Task.class) {
            typeTask = TypeTask.TASK;
        } else if (task.getClass() == EpicTask.class) {
            typeTask = TypeTask.EPIC;
            listSubtasks = ((EpicTask) task).getListSubtask().toString();
        } else if (task.getClass() == SubTask.class) {
            typeTask = TypeTask.SUBTASK;
            numberEpic = ((SubTask) task).getEpicTaskNumber();
        }
        String string = String.join(",", String.valueOf(task.getId()), typeTask.toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), numberEpic.toString(), listSubtasks.toString());
        return string;
    }

    public Task fromString(String value) {
        // Поля для преобразование задачи из текста
        int id = 0;
        TypeTask typeTask = null;
        String name = null;
        Status status = null;
        String description = null;
        Integer epicTask = null;
        java.util.List<Integer> list = new ArrayList<>();
        Task task = null;

        String[] split = value.split(",");
        try {
            id = Integer.parseInt(split[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Нуверное значенике ID задачи: " + e.getMessage());
        }

        if (split[1].trim() == "TASK") {
            typeTask = TypeTask.TASK;
        } else if (split[1].trim() == "EPIC") {
            typeTask = TypeTask.EPIC;
        } else if (split[1].trim() == "SUBTASK") {
            typeTask = TypeTask.SUBTASK;
        } else {
            System.out.println("Не верное значение типа задачи");
        }

        name = split[2].trim();

        if (split[3].trim() == "NEW") {
            status = Status.NEW;
        } else if (split[3].trim() == "IN_PROGRESS") {
            status = Status.IN_PROGRESS;
        } else if (split[3].trim() == "DONE") {
            status = Status.DONE;
        } else {
            System.out.println("Не верное значение статуса задачи");
        }

        description = split[4].trim();

        epicTask = Integer.parseInt(split[5].trim());

        String subString = split[6].trim().substring(1, split[6].length() - 1);

        String[] subStringNumbers = subString.split(", ");

        for (String str : subStringNumbers) {
            list.add(Integer.parseInt(str.trim()));
        }

        if (typeTask == TypeTask.TASK) {
            task = new Task(name, description, id, status);
        } else if (typeTask == TypeTask.EPIC) {
            task = new EpicTask(name, description, id, status, list);
        } else if (typeTask == TypeTask.SUBTASK) {
            task = new SubTask(name, description, id, status, epicTask);
        }
        return task;
    }

//    public static  String toString(HistoryManager historyManager){
//
//    }
//
//    public static List<Integer> fromString(String value){
//
//    }

    public void writeStingHashMap(Integer id, String string) {
        stringHashMap.put(id, string);
    }


    public void saveToFile() throws IOException {

        try {
            Path pathToFile = Paths.get(path).toAbsolutePath();
            if (!Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks\n");
                for (Map.Entry entry : stringHashMap.entrySet()) {
                    writer.write((String) entry.getValue() + "\n");
                }
                writer.close();
            } else if (Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks\n");
                for (Map.Entry entry : stringHashMap.entrySet()) {
                    writer.write((String) entry.getValue() + "\n");
                }
                writer.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
