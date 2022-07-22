package bussinesslogic;

import error.ManagerSaveException;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private String path;

    public FileBackedTasksManager(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
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
            saveToFile();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
            saveToFile();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
            saveToFile();
    }

    @Override
    public void getTaskById(int key) {
        super.getTaskById(key);
            saveToFile();
    }

    @Override
    public void getEpicTaskById(int key) {
        super.getEpicTaskById(key);
            saveToFile();
    }

    @Override
    public void getSubTaskById(int key) {
        super.getSubTaskById(key);
            saveToFile();
    }

    @Override
    public Task makeTask(String name, String description, Optional<LocalDateTime> startTime, int duration) {
        Task task = super.makeTask(name, description,startTime,duration);
            saveToFile();
        return task;
    }

    @Override
    public EpicTask makeEpic(String name, String description) {
        EpicTask epicTask = super.makeEpic(name,description);
            saveToFile();
        return epicTask;
    }

    @Override
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration) {
        SubTask subTask = super.makeSubTask(name, description, id, startTime, duration);
            saveToFile();
        return subTask;
    }

    @Override
    public void deleteTaskById(int key) {
        super.deleteTaskById(key);
            saveToFile();
    }

    @Override
    public void deleteEpicTaskById(int key) {
        super.deleteEpicTaskById(key);
            saveToFile();
    }

    @Override
    public void deleteSubTaskById(int key) {
        super.deleteSubTaskById(key);
            saveToFile();
    }

    @Override
    public Map<Integer, Task> updateTaskById(Task taskUpdate) {
        Map<Integer, Task> map = super.updateTaskById(taskUpdate);
            saveToFile();
        return map;
    }

    @Override
    public void updateEpicTaskById(EpicTask taskUpdate) {
    super.updateEpicTaskById(taskUpdate);
            saveToFile();
    }

    @Override
    public void updateSubTaskById(SubTask taskUpdate) {
        super.updateSubTaskById(taskUpdate);
            saveToFile();
    }

    @Override
    public void getAllSubTaskInEpic(int key) {
        super.getAllSubTaskInEpic(key);
    }

    public String toString(Task task) {
        TypeTask typeTask = null;
        String numberEpic = "no";
        String listSubtasks = "no";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");
        String timeTostring;
        String endTimeToString;

        if(task.getStartTime().isEmpty()){
            timeTostring = "no time";
        }else{
            timeTostring = task.getStartTime().get().format(formatter);
        }

        if(task.getEndTime().isEmpty()){
            endTimeToString = "no time";
        }else{
            endTimeToString = task.getEndTime().get().format(formatter);
        }

        String duration;
        if (task.getDuration()==0){
            duration = "0";
        }else {
            duration = String.valueOf(task.getDuration());
        }


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
                task.getStatus().toString(), task.getDescription(), numberEpic, listSubtasks, timeTostring,
                endTimeToString, duration);
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
        Optional<LocalDateTime> localDateTime = Optional.empty();
        Optional<LocalDateTime> endTime = Optional.empty();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");
        int duration;
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

        if(split[7].trim().equals("no time")){
         localDateTime = Optional.empty();
        }else {
            localDateTime = Optional.of(LocalDateTime.parse(split[7].trim(), formatter));
        }

        if(split[8].trim().equals("no time")){
            endTime = Optional.empty();
        }else {
            endTime = Optional.of(LocalDateTime.parse(split[8].trim(), formatter));
        }

        duration = Integer.parseInt(split[9].trim());



        if (typeTask.equals(TypeTask.TASK)) {
            task = new Task(name, description, id, status,localDateTime, duration);
        }   else if (typeTask.equals(TypeTask.SUBTASK)) {
            epicTask = Integer.parseInt(split[5].trim());
            task = new SubTask(name, description, id, status, localDateTime,duration, epicTask);
        } else if (typeTask.equals(TypeTask.EPIC)) {
                String subString = split[6].trim().substring(1, split[6].length() - 1);
                if(subString.equals("")){
                    list = new ArrayList<>();
                    task = new EpicTask(name, description, id, status, list);
                }else{
                String[] subStringNumbers = subString.split(";");
            for (String str : subStringNumbers) {
                list.add(Integer.parseInt(str.trim()));
            }
            task = new EpicTask(name, description, id, status, localDateTime, duration, list, endTime);
            }
        }
        return task;
    }

    public void fromFile() throws IOException {
        Map<Integer, Task> map = getTaskMap();
        Map<Integer, EpicTask> mapEpic = getEpicTaskMap();
        Map<Integer, SubTask> mapSubTask = getSubTaskMap();
        Integer conterId = getCounterID();
        HistoryManager historyManager = this.getHistoryManager();
        if(Paths.get(path).toFile().isFile()) {
            Reader reader = new FileReader(path);
            BufferedReader bf = new BufferedReader(reader);
            bf.readLine();
            System.out.println("Задачи загруженные из файла *.csv");
            while (bf.ready()) {
                String line = bf.readLine();
                if (!line.isBlank()) {
                    Task task = fromString(line);
                    if (task.getClass().equals(Task.class)) {
                        System.out.println("Task" + task);
                        map.put(task.getId(), task);
                    } else if (task.getClass().equals(EpicTask.class)) {
                        System.out.println("Epic" + task);
                        mapEpic.put(task.getId(), (EpicTask) task);
                    } else if (task.getClass().equals(SubTask.class)) {
                        System.out.println("SubTask" + task);
                        mapSubTask.put(task.getId(), (SubTask) task);
                    }
                    setCounterID(++conterId);
                } else {
                    System.out.println();
                    System.out.println("Загрузка данных в HistoryManager");
                    break;
                }
            }
            if (!bf.ready()) {
                bf.close();
                System.out.println("В файле отсутвует запись о historyManager");
                return;
            } else {
                String string = bf.readLine();
                if (string.isBlank()) {
                    System.out.println("Просмотра задач еще не было");
                } else {
                    historyManager = fromFile(string);
                    setHistoryManager(historyManager);
                }
                bf.close();
                System.out.println();
            }
        }else{
                System.out.println("Файл с даннмым не обноружен \n");
            }
        }

    public String toString(HistoryManager historyManager) {
        Collection<Task> list = historyManager.getHistory();
        Collection<Integer> number = new ArrayList<>();
        String string = null;
        if (list.size() == 0) {
            return string = "Просмотра задач еще не было";
        }else{
                for (Task task : list) {
                    Integer id = task.getId();
                    number.add(id);
                }
                string = number.toString();
                string = string.substring(1, string.length() - 1);
                return string;
            }
        }

    public HistoryManager fromFile(String value){
        HistoryManager historyManager = getHistoryManager();
        Map<Integer,Task> map = getTaskMap();
        Map<Integer,EpicTask> mapEpic = getEpicTaskMap();
        Map<Integer,SubTask> mapSubTask = getSubTaskMap();
        String[] split = value.split(", ");
        Collection<Integer> list= new ArrayList();
        Integer id = null;
            for (String str : split) {
                if (!str.equals("Просмотра задач еще не было")) {
                    list.add(Integer.parseInt(str));
                } else {
                    return historyManager = new InMemoryHistoryManager();
                }
            }
                for (int i : list){
                    if (map.containsKey(i)){
                        System.out.println("Задача добавлена в historyManager" + map.get(i));
                        historyManager.add(map.get(i));
                    } else if (mapEpic.containsKey(i)) {
                        System.out.println("Epic добавлена в historyManager" + mapEpic.get(i));
                        historyManager.add(mapEpic.get(i));
                    } else if (mapSubTask.containsKey(i)) {
                        System.out.println("SubTask добавлена в historyManager" + mapSubTask.get(i));
                        historyManager.add(mapSubTask.get(i));
                    }
                }return historyManager;
            }

    public void saveToFile() {
            String sting;
            String historyManager = toString(getHistoryManager());
        try {
            Path pathToFile = Paths.get(path).toAbsolutePath();
            if (!Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks,data,duration\n");
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
                    writer.write( "\n");
                writer.write(historyManager);
                writer.close();
            } else if (Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks,data,duration\n");
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
                writer.write( "\n");
                writer.write(historyManager);
                writer.close();
            }
        } catch (Exception e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }
}
