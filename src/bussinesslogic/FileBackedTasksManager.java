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

    private String path;

    public FileBackedTasksManager(String path) {
        this.path = path;
    }

    @Override
    public void setTaskMap(Map<Integer, Task> taskMap) {
        super.setTaskMap(taskMap);
    }

    @Override
    public void setEpicTaskMap(Map<Integer, EpicTask> epicTaskMap) {
        super.setEpicTaskMap(epicTaskMap);
    }

    @Override
    public void setSubTaskMap(Map<Integer, SubTask> subTaskMap) {
        super.setSubTaskMap(subTaskMap);
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
    public Collection getListSubTasks(Map<Integer, SubTask> mapSubTask) {
        return super.getListSubTasks(mapSubTask);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEpicTaskById(int key) {
        super.deleteEpicTaskById(key);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSubTaskById(int key) {
        super.deleteSubTaskById(key);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Task> updateTaskById(Task taskUpdate) {
        Map<Integer, Task> map = super.updateTaskById(taskUpdate);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public void updateEpicTaskById(EpicTask taskUpdate) {
    super.updateEpicTaskById(taskUpdate);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubTaskById(SubTask taskUpdate) {
        super.updateSubTaskById(taskUpdate);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAllSubTaskInEpic(int key) {
        super.getAllSubTaskInEpic(key);
    }


    public String toString(Task task) {
        TypeTask typeTask = null;
        String numberEpic = "no";
//        Collection listSubtasks = null;
        String listSubtasks = "no";
        if (task.getClass() == Task.class) {
            typeTask = TypeTask.TASK;
        } else if (task.getClass() == EpicTask.class) {
            typeTask = TypeTask.EPIC;
            Collection list = ((EpicTask) task).getListSubtask();
            if(!list.isEmpty()) {
                listSubtasks = ((EpicTask) task).getListSubtask().toString().replace(",",";");
            }
        } else if (task.getClass() == SubTask.class) {
            typeTask = TypeTask.SUBTASK;
            numberEpic = String.valueOf(((SubTask) task).getEpicTaskNumber());
        }
        String string = String.join(",", String.valueOf(task.getId()), typeTask.toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), numberEpic, listSubtasks);
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
            System.out.println("Неверное значенике ID задачи: " + e.getMessage());
        }

        if (split[1].trim().equals("TASK")) {
            typeTask = TypeTask.TASK;
        } else if (split[1].trim().equals("EPIC")) {
            typeTask = TypeTask.EPIC;
        } else if (split[1].trim().equals("SUBTASK")) {
            typeTask = TypeTask.SUBTASK;
        } else {
            System.out.println("Не верное значение типа задачи");
        }

        name = split[2].trim();

        if (split[3].trim().equals("NEW")) {
            status = Status.NEW;
        } else if (split[3].trim().equals("IN_PROGRESS")) {
            status = Status.IN_PROGRESS;
        } else if (split[3].trim().equals("DONE")) {
            status = Status.DONE;
        } else {
            System.out.println("Не верное значение статуса задачи");
        }

        description = split[4].trim();

        if (typeTask.equals(TypeTask.TASK)) {
            task = new Task(name, description, id, status);
        }   else if (typeTask.equals(TypeTask.SUBTASK)) {
            epicTask = Integer.parseInt(split[5].trim());
            task = new SubTask(name, description, id, status, epicTask);
        } else if (typeTask.equals(TypeTask.EPIC)) {
            if (split[6].length() == 1) {
                String subString = split[6].trim().substring(1, split[6].length() - 1);
            } else if(split[6].length() > 1){
                String subString = split[6].trim().substring(1, split[6].length() - 1);
                String[] subStringNumbers = subString.split(";");
            for (String str : subStringNumbers) {
                list.add(Integer.parseInt(str.trim()));
            }
            task = new EpicTask(name, description, id, status, list);
        }
//            else if (typeTask.equals(TypeTask.SUBTASK)) {
//            epicTask = Integer.parseInt(split[5].trim());
//            task = new SubTask(name, description, id, status, epicTask);
//        }

//        epicTask = Integer.parseInt(split[5].trim());

//        String subString = split[6].trim().substring(1, split[6].length() - 1);
//
//        String[] subStringNumbers = subString.split(", ");
//
//        for (String str : subStringNumbers) {
//            list.add(Integer.parseInt(str.trim()));
        }


        return task;
    }

    public void fromFile() throws IOException{
        Map<Integer,Task> map = getTaskMap();
        Map<Integer,EpicTask> mapEpic = getEpicTaskMap();
        Map<Integer,SubTask> mapSubTask = getSubTaskMap();

        try(Reader reader = new FileReader(path)){
            BufferedReader bf = new BufferedReader(reader);
           bf.readLine();
            System.out.println("Задачи загруженные из файла *.csv");
            while (bf.ready()){
                String line = bf.readLine();
                if(bf.equals("")){
                    System.out.println("Пустая строка");
                    break;
                }else {
                Task task =  fromString(line);
                if (task.getClass().equals(Task.class)) {
                    System.out.println("Task" + task);
                    map.put(task.getId(), task);
                    setTaskMap(map);
                } else if (task.getClass().equals(EpicTask.class)) {
                    System.out.println("Epic" + task);
                    mapEpic.put(task.getId(),(EpicTask)task);
                    setEpicTaskMap(mapEpic);
                } else if (task.getClass().equals(SubTask.class)) {
                    System.out.println("SubTask" + task);
                    mapSubTask.put(task.getId(), (SubTask) task);
                    setSubTaskMap(mapSubTask);
                    }
                }
            }
            bf.close();
            System.out.println();
        }catch (FileNotFoundException e){
            System.out.println("Файл с даннмым не обноружен \n");
        }
    }

    public static  String toString(HistoryManager historyManager){

        return null;
    }

//    public static List<Integer> fromString(String value){
//    return null;
//    }



    public void saveToFile() throws IOException, RuntimeException {
            String sting;
        try {
            Path pathToFile = Paths.get(path).toAbsolutePath();
            if (!Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks\n");
                for (Map.Entry entry : super.getTaskMap().entrySet()) {
                    sting = toString((Task) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getEpicTaskMap().entrySet()) {
                    sting = toString((EpicTask) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getSubTaskMap().entrySet()) {
                    sting = toString((SubTask) entry.getValue());
                    writer.write(sting + "\n");
                }
//                    writer.write( "\n");
                writer.close();
            } else if (Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks\n");
                for (Map.Entry entry : super.getTaskMap().entrySet()) {
                    sting = toString((Task) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getEpicTaskMap().entrySet()) {
                    sting = toString((EpicTask) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getSubTaskMap().entrySet()) {
                    sting = toString((SubTask) entry.getValue());
                    writer.write(sting + "\n");
                }
//                writer.write( "\n");
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
