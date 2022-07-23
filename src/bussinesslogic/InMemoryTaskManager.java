package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTaskManager implements TaskManager{
    private Integer counterID = 1;
    private Map<Integer, Task> taskMap = new HashMap<>();
    private Map<Integer, EpicTask> epicTaskMap = new HashMap<>();
    private Map<Integer, SubTask> subTaskMap = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();

    public InMemoryTaskManager() {
    }


    //Получение значение счетчика
    public Integer getCounterID() {
        return counterID;
    }

    public void setCounterID(Integer counterID) {
        this.counterID = counterID;
    }

    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    //Получение map Tasks
    @Override
    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    //Получение map EpicTasks
    @Override
    public Map<Integer, EpicTask> getEpicTaskMap() {
        return epicTaskMap;
    }
    //Получение map SubTasks
    @Override
    public Map<Integer, SubTask> getSubTaskMap() {
        return subTaskMap;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    //Получение списка всех задач model.Task.
    @Override
    public Collection getListTasks(Map<Integer, Task> mapTask) {
        Collection listTask = new ArrayList<>();
        Set<Integer> setKeys = mapTask.keySet();
        for (int i : setKeys) {
            Task task = mapTask.get(i);
            listTask.add(task);
        }
        return listTask;
    }

    //Получение списка всех Эпик задач model.EpicTask.
    @Override
    public Collection getListEpicTasks(Map<Integer, EpicTask> mapEpicTask) {
        Collection listEpicTask = new ArrayList<>();
        Set<Integer> setKeys = mapEpicTask.keySet();
        for (int i : setKeys) {
            EpicTask epicTask = mapEpicTask.get(i);
            listEpicTask.add(epicTask);
        }
        return listEpicTask;
    }

    //Получение списка всех подзадач model.SubTask.
    @Override
    public List<SubTask> getListSubTasks(Map<Integer, SubTask> mapSubTask) {
        List<SubTask> listSubTask = new ArrayList<>();
        Set<Integer> setKeys = mapSubTask.keySet();
        for (int i : setKeys) {
            SubTask subTask = mapSubTask.get(i);
            listSubTask.add(subTask);
        }
        return listSubTask;
    }


    //Удаление всех задач model.Task.
    @Override
    public void deleteAllTask() {
        for (Map.Entry<Integer,Task> entry: taskMap.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty()){
            historyManager.remove(task);
        }}
        taskMap.clear();

    }

    //Удаление всех эпик задач model.EpicTask.
    @Override
    public void deleteAllEpic() {
        for (Map.Entry<Integer,EpicTask> entry: epicTaskMap.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty() && historyManager.getHistory().contains(task)){
                List<Integer> numberSubtask = entry.getValue().getListSubtask();
                for(int number : numberSubtask){
                    Node subTask = new Node(subTaskMap.get(number));
                    historyManager.remove(subTask);
                }
            historyManager.remove(task);
        }}
        epicTaskMap.clear();
        subTaskMap.clear();

    }

    //Удаление всех подзадач subTask.
    @Override
    public void deleteAllSubTask() {
        for (Map.Entry<Integer,SubTask> entry: subTaskMap.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty() && historyManager.getHistory().contains(task)) {
                historyManager.remove(task);
            }
        }
        Set<Integer> setKeys = epicTaskMap.keySet();
        for (int i : setKeys) {
            EpicTask epicTask = epicTaskMap.get(i);
            epicTask.getListSubtask().clear();
        }
        subTaskMap.clear();
    }

    //Получение задачи model.Task по идентификатору.
    @Override
    public void getTaskById(int key) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            System.out.println(taskMap.get(key));
            historyManager.add(taskMap.get(key));
        }
    }


    //Получение задачи model.EpicTask по идентификатору.
    @Override
    public void getEpicTaskById(int key) {
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            System.out.println(epicTaskMap.get(key));
            historyManager.add(epicTaskMap.get(key));
        }
    }

    //Получение задачи model.SubTask по идентификатору.
    @Override
    public void getSubTaskById(int key) {
        if (subTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            System.out.println(subTaskMap.get(key));
            historyManager.add(subTaskMap.get(key));
        }
    }

    //Создание задачи model.Task
    @Override
    public Task makeTask(String name, String description, Optional<LocalDateTime> startTime, int duration) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setId(counterID++);
        task.setStatus(Status.NEW);
        task.setStartTime(startTime);
        task.setDuration(duration);
        taskMap.put(task.getId(), task);
        System.out.println("Номер вашей задачи " + task.getId() + "\n");
        return task;
    }

    //Создание задачи model.EpicTask
    @Override
    public EpicTask makeEpic(String name, String description) {
        EpicTask epicTask = new EpicTask();
        epicTask.setName(name);
        epicTask.setDescription(description);
        epicTask.setId(counterID++);
        epicTask.setStatus(Status.NEW);
        epicTaskMap.put(epicTask.getId(), epicTask);
        System.out.println("Номер вашей Эпик задачи " + epicTask.getId() + "\n");
        return epicTask;
    }

    //Создание задачи model.SubTask
    @Override
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration) {
        SubTask subTask = new SubTask();
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setEpicTaskNumber(id);
        subTask.setStatus(Status.NEW);
        subTask.setDuration(duration);
        subTask.setStartTime(startTime);
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        Optional<LocalDateTime> start = null;
        Optional<LocalDateTime> end = null;
        int time; 
        if (setKeysTask.size() == 0) {
            System.out.println("Tакого id в списке Эпик задач - нет");
        } else {
            for (int i : setKeysTask) {
                EpicTask epicTask = epicTaskMap.get(i);
                if (id != epicTask.getId()) {
                    System.out.println("Tакого id в списке Эпик задач - нет");
                } else {
                    subTask.setId(counterID++);
                    epicTask.setDuration(epicTask.getDuration() + subTask.getDuration());
                    if(epicTask.getListSubtask().isEmpty()) {
                        epicTask.setStartTime(subTask.getStartTime());
                        epicTask.setEndTime(subTask.getEndTime());
                    }else{
                        start = epicTask.getStartTime();
                        end = epicTask.getEndTime();
                        for (int j : epicTask.getListSubtask()) {
                            SubTask subTaskBuffer = subTaskMap.get(j);
                            if (subTask.getStartTime().get().isBefore(start.get()) &&
                                    subTask.getStartTime().get().isBefore(subTaskBuffer.getStartTime().get())) {
                                epicTask.setStartTime(subTask.getStartTime());
                            } else {
                                epicTask.setStartTime(start);
                            }
                            if (subTask.getEndTime().get().isAfter(end.get()) &&
                                    subTask.getEndTime().get().isAfter(subTaskBuffer.getEndTime().get())) {
                                epicTask.setEndTime(subTask.getEndTime());
                            } else {
                                epicTask.setEndTime(end);
                            }
                        }

                    }
                    epicTask.addSubTask(subTask.getId());
                    subTaskMap.put(subTask.getId(), subTask);
                    System.out.println("Номер вашей подзадачи " + subTask.getId() + "\n"
                            + "Она входит в Эпик задачу " + subTask.getEpicTaskNumber() + "\n");
        }
            }}
        return subTask;
    }

    //Удаление задачи model.Task по идентификатору.
    @Override
    public void deleteTaskById(int key) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            taskMap.remove(key);
            System.out.println("Задача удалена");
        }
        Node<Task> taskNode = new Node<>(taskMap.get(key));
        if(!historyManager.getHistory().isEmpty()) {
        historyManager.remove(taskNode);
    }
    }

    //Удаление задачи model.EpicTask по идентификатору.
    @Override
    public void deleteEpicTaskById(int key) {
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTaskMap.get(key);
            Collection listSubTask = epicTask.getListSubtask();
            if(!listSubTask.isEmpty()){
            for (Object i : listSubTask) {
                subTaskMap.remove(i);
                Node<Task> taskNode = new Node<>(subTaskMap.get(i));
                if(!historyManager.getHistory().isEmpty()&&historyManager.getHistory().contains(taskNode)) {
                    historyManager.remove(taskNode);
                } }
            }
            epicTaskMap.remove(key);
            System.out.println("Эпик задача удалена вмести с подзадачами");
        }
        Node<Task> taskNode = new Node<>(epicTaskMap.get(key));
        if(!historyManager.getHistory().isEmpty()) {
        historyManager.remove(taskNode);
    }}

    //Удаление задачи model.SubTask по идентификатору.
    @Override
    public void deleteSubTaskById(int key) {
        Node<Task> taskNode = new Node<>(subTaskMap.get(key));
        if(!historyManager.getHistory().isEmpty()) {
            historyManager.remove(taskNode);
        }
        if (subTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            subTaskMap.remove(key);
            System.out.println("Подзадача удалена");
            List<Integer> idSubTasks;
            Set<Integer> setKeys = epicTaskMap.keySet();
            for (int k : setKeys) {
                EpicTask epicTask = epicTaskMap.get(k);
                idSubTasks = epicTask.getListSubtask();
                if (idSubTasks.size() == 0) {
                    continue;
                } else {
                    for (int j = 0; j < idSubTasks.size(); j++) {
                        int idSubTask = idSubTasks.get(j);
                        if (key == idSubTask) {
                            idSubTasks.remove(j);
                            epicTask.setListSubtask(idSubTasks);
                            break;
                        }
                    }
                }
            }
        }
        }

    //Обнавление задачи model.Task
    @Override
    public Map<Integer, Task> updateTaskById(Task taskUpdate) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            taskMap.put(taskUpdate.getId(), taskUpdate);
            System.out.println("Задача обновлена");
        }
        return taskMap;
    }

    //Обнавление задачи model.EpicTask
    @Override
    public void updateEpicTaskById(EpicTask taskUpdate) {
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTaskMap.get(taskUpdate.getId());
            taskUpdate.setListSubtask(epicTask.getListSubtask());
            taskUpdate.setStatus(epicTask.getStatus());
            epicTaskMap.put(taskUpdate.getId(), taskUpdate);
            System.out.println("Задача обновлена");
        }
    }


    //Обнавление задачи model.SubTask
    @Override
    public void updateSubTaskById(SubTask taskUpdate) {
        if (subTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTaskMap.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            SubTask subTask = subTaskMap.get(taskUpdate.getId());
            taskUpdate.setEpicTaskNumber(subTask.getEpicTaskNumber());
            subTaskMap.put(taskUpdate.getId(), taskUpdate);
            int numberOfEpicTask = subTask.getEpicTaskNumber();
            int counterNew = 0;
            int counterInProcess = 0;
            int counterDone = 0;
            int duration = 0;
            EpicTask epicTask = epicTaskMap.get(numberOfEpicTask);
            List<Integer> listSubTask = epicTask.getListSubtask();
            Optional<LocalDateTime> start = Optional.empty();
            Optional<LocalDateTime> end = Optional.empty();
            for (int j : listSubTask) {
                SubTask subTask1 = subTaskMap.get(j);
                Status status = subTask1.getStatus();
                if (status == Status.NEW) {
                    counterNew++;
                } else if (status == Status.IN_PROGRESS) {
                    counterInProcess++;
                } else {
                    counterDone++;
                }

                duration = duration + subTask1.getDuration();

                if (taskUpdate.getStartTime().get().isBefore(subTask1.getStartTime().get())) {
                    epicTask.setStartTime(taskUpdate.getStartTime());
                } else {
                    epicTask.setStartTime(subTask1.getStartTime());
                }
                if (taskUpdate.getEndTime().get().isAfter(subTask1.getEndTime().get())) {
                    epicTask.setEndTime(taskUpdate.getEndTime());
                } else {
                    epicTask.setEndTime(subTask1.getEndTime());
                }
            }

            epicTask.setDuration(duration);

            if (counterNew == epicTask.getListSubtask().size()) {
            } else if (counterInProcess > 0) {
                epicTask.setStatus(Status.IN_PROGRESS);
            } else if (counterDone == epicTask.getListSubtask().size()) {
                epicTask.setStatus(Status.DONE);
            }
            System.out.println("Подзадача обновлена");
        }
    }

    //Получение всех задач Эпик задачи
    @Override
    public void getAllSubTaskInEpic(int key) {
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTaskMap.get(key);
            Collection listSubTask = epicTask.getListSubtask();
            if (listSubTask.size() == 0) {
                System.out.println("У Эпик задачи нет подзадач");
            } else {
                System.out.println("Список id подзадач " + listSubTask + " у Эпик задачи id " + epicTask.getId());
            }
        }
    }

    //Получение задач по приоретету времени
    @Override
    public LinkedList<Task> getPrioritizedTasks() {
        LinkedList<Task> listTask = new LinkedList<>();
        Optional<LocalDateTime> start = Optional.empty();
        for (Map.Entry<Integer,Task> entry:taskMap.entrySet()) {
            if (listTask.isEmpty()){
                listTask.add(entry.getValue());
                start = entry.getValue().getStartTime();
        }else if (entry.getValue().getStartTime().get().isBefore(start.get())){
           listTask.addFirst(entry.getValue());
        }else if(entry.getValue().getStartTime().get().isAfter(start.get())){
                listTask.add(entry.getValue());
            }else if (entry.getValue().getStartTime().isEmpty()){
                listTask.addLast(entry.getValue());
            }
        }
        for (Map.Entry<Integer,SubTask> entry:subTaskMap.entrySet()) {
            if (listTask.isEmpty()){
                listTask.add(entry.getValue());
                start = entry.getValue().getStartTime();
            }else if (entry.getValue().getStartTime().get().isBefore(start.get())){
                listTask.addFirst(entry.getValue());
            }else if(entry.getValue().getStartTime().get().isAfter(start.get())){
                listTask.add(entry.getValue());
            }else if (entry.getValue().getStartTime().isEmpty()){
                listTask.addLast(entry.getValue());
            }
        }

        return listTask;
    }

    @Override
    public void fromFile() throws IOException {
    }
}