package bussinesslogic;

import error.ManagerSaveException;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            saveToFile();
    }
    @Override
    public void deleteAllEpicTasks() {
        super.deleteAllEpicTasks();
            saveToFile();
    }
    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
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
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration)
    {
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
    public String ConvertToString(Task task) {
        TaskType taskType = null;
        String numberEpic = "no";
        String subtasksCatalogue = "no";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");
        String timeConvertToString;
        String endTimeToString;
        if(task.getStartTime().isEmpty()){
            timeConvertToString = "no time";
        }else{
            timeConvertToString = task.getStartTime().get().format(formatter);
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
            taskType = TaskType.TASK;
        } else if (task.getClass() == EpicTask.class) {
            taskType = TaskType.EPIC;
            Collection catalogue = ((EpicTask) task).getSubtaskCatalogue();
            if(!catalogue.isEmpty()) {
                subtasksCatalogue = ((EpicTask) task).getSubtaskCatalogue().toString().replace(",",";");
            }
        } else if (task.getClass() == SubTask.class) {
            taskType = TaskType.SUBTASK;
            numberEpic = String.valueOf(((SubTask) task).getEpicTaskNumber());
        }
        String string = String.join(",", String.valueOf(task.getId()), taskType.toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), numberEpic, subtasksCatalogue, timeConvertToString,
                endTimeToString, duration);
        return string;
    }
    public Task fromString(String value) {
        // Поля для преобразование задачи из текста
        int id = 0;
        TaskType taskType = null;
        String name = null;
        Status status = null;
        String description = null;
        Integer epicTask = null;
        java.util.List<Integer> catalogue = new ArrayList<>();
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
            taskType = TaskType.TASK;
        } else if (split[1].trim().equals("EPIC")) {
            taskType = TaskType.EPIC;
        } else if (split[1].trim().equals("SUBTASK")) {
            taskType = TaskType.SUBTASK;
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
        if (taskType.equals(TaskType.TASK)) {
            task = new Task(name, description, id, status,localDateTime, duration);
        }   else if (taskType.equals(TaskType.SUBTASK)) {
            epicTask = Integer.parseInt(split[5].trim());
            task = new SubTask(name, description, id, status, localDateTime,duration, epicTask);
        } else if (taskType.equals(TaskType.EPIC)) {
                String subString = split[6].trim().substring(1, split[6].length() - 1);
                if(subString.equals("")){
                    catalogue = new ArrayList<>();
                    task = new EpicTask(name, description, id, status, catalogue);
                }else{
                String[] subStringNumbers = subString.split(";");
            for (String str : subStringNumbers) {
                catalogue.add(Integer.parseInt(str.trim()));
            }
            task = new EpicTask(name, description, id, status, localDateTime, duration, catalogue, endTime);
            }
        }
        return task;
    }
    public void fromFile() throws IOException {
        Map<Integer, Task> tasks = this.getTasks();
        Map<Integer, EpicTask> epicTasks = getEpicTasks();
        Map<Integer, SubTask> subTasks = this.getSubTasks();
        Integer conterId = getCounterID();
        HistoryManager historyManager = this.getHistoryManager();
        TreeSet<Task> treeSet = getPrioritizedTasks();
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
                        tasks.put(task.getId(), task);
                        treeSet.add(task);
                    } else if (task.getClass().equals(EpicTask.class)) {
                        System.out.println("Epic" + task);
                        epicTasks.put(task.getId(), (EpicTask) task);
                    } else if (task.getClass().equals(SubTask.class)) {
                        System.out.println("SubTask" + task);
                        subTasks.put(task.getId(), (SubTask) task);
                        treeSet.add(task);
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
    public String ConvertToString(HistoryManager historyManager) {
        Collection<Task> history = historyManager.getHistory();
        Collection<Integer> number = new ArrayList<>();
        String string = null;
        if (history.size() == 0) {
            return string = "Просмотра задач еще не было";
        }else{
                for (Task task : history) {
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
        Map<Integer,Task> tasks = this.getTasks();
        Map<Integer,EpicTask> epicTasks = getEpicTasks();
        Map<Integer,SubTask> SubTasks = this.getSubTasks();
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
                    if (tasks.containsKey(i)){
                        System.out.println("Задача добавлена в historyManager" + tasks.get(i));
                        historyManager.add(tasks.get(i));
                    } else if (epicTasks.containsKey(i)) {
                        System.out.println("Epic добавлена в historyManager" + epicTasks.get(i));
                        historyManager.add(epicTasks.get(i));
                    } else if (SubTasks.containsKey(i)) {
                        System.out.println("SubTask добавлена в historyManager" + SubTasks.get(i));
                        historyManager.add(SubTasks.get(i));
                    }
                }return historyManager;
            }
    public void saveToFile() {
            String sting;
            String historyManager = ConvertToString(getHistoryManager());
        try {
            Path pathToFile = Paths.get(path).toAbsolutePath();
            if (!Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks,data,duration\n");
                for (Map.Entry entry : super.getTasks().entrySet()) {
                    sting = ConvertToString((Task) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getEpicTasks().entrySet()) {
                    sting = ConvertToString((EpicTask) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getSubTasks().entrySet()) {
                    sting = ConvertToString((SubTask) entry.getValue());
                    writer.write(sting + "\n");
                }
                    writer.write( "\n");
                writer.write(historyManager);
                writer.close();
            } else if (Files.exists(pathToFile)) {
                Writer writer = new FileWriter(pathToFile.toString());
                writer.write("id,type,name,status,description,epic,subtasks,data,duration\n");
                for (Map.Entry entry : super.getTasks().entrySet()) {
                    sting = ConvertToString((Task) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getEpicTasks().entrySet()) {
                    sting = ConvertToString((EpicTask) entry.getValue());
                    writer.write(sting + "\n");
                }
                for (Map.Entry entry : super.getSubTasks().entrySet()) {
                    sting = ConvertToString((SubTask) entry.getValue());
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
    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }
}
