package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager{
    private Integer counterID = 1;
    private Map<Integer, Task> taskMap = new HashMap<>();
    private Map<Integer, EpicTask> epicTaskMap = new HashMap<>();
    private Map<Integer, SubTask> subTaskMap = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();

    private TreeSet<Task> prioritizedTasks = new TreeSet<>();
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

    //Получение задач по приоретету времени
    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
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
        for (Map.Entry<Integer,Task> entry : taskMap.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
        }
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
        for (Map.Entry<Integer,SubTask> entry : subTaskMap.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
        }
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
            epicTask.setStartTime(Optional.empty());
            epicTask.setEndTime(Optional.empty());
            epicTask.setDuration(0);
        }
        for (Map.Entry<Integer,SubTask> entry : subTaskMap.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
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
        TreeSet<Task> treeSet = getPrioritizedTasks();
        int result = freeTime(treeSet,task);
        if ( result != 0) {
            System.out.println("Задача пересекается по времени с другими задачами и не будет добавлена");
        } else {
            taskMap.put(task.getId(), task);
            treeSet.add(task);
            System.out.println("Номер вашей задачи " + task.getId() + "\n");
            return task;
        }
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
    public SubTask makeSubTask(String name, String description, int id, Optional<LocalDateTime> startTime, int duration)
    {
        SubTask subTask = new SubTask();
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setEpicTaskNumber(id);
        subTask.setStatus(Status.NEW);
        subTask.setDuration(duration);
        subTask.setStartTime(startTime);
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        TreeSet<Task> treeSet = getPrioritizedTasks();
        if (freeTime(treeSet,subTask) == 0) {
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
                    if (epicTask.getListSubtask().isEmpty()) {
                        epicTask.setStartTime(subTask.getStartTime());
                        epicTask.setEndTime(subTask.getEndTime());
                    } else {
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
                    treeSet.add(subTask);
                    System.out.println("Номер вашей подзадачи " + subTask.getId() + "\n"
                            + "Она входит в Эпик задачу " + subTask.getEpicTaskNumber() + "\n");
                }
            }
        }
        } else {
                System.out.println("Подзадача пересекается по времени с другими задачами и не будет добавлена");
        }
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
            prioritizedTasks.remove(taskMap.get(key));
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
                prioritizedTasks.remove(subTaskMap.get(i));
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
        }
    }

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
            prioritizedTasks.remove(subTaskMap.get(key));
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
            TreeSet<Task> treeSet = getPrioritizedTasks();
            treeSet.remove(taskMap.get(taskUpdate.getId()));
            if (freeTime(treeSet,taskUpdate) == 0 ) {
                taskMap.put(taskUpdate.getId(), taskUpdate);
                treeSet.add(taskUpdate);
                System.out.println("Номер вашей задачи " + taskUpdate.getId() + "\n");
                System.out.println("Задача обновлена");
                return taskMap;
            } else {
                System.out.println("Задача пересекается по времени с другими задачами и не будет добавлена");
            }
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
            taskUpdate.setStartTime(epicTask.getStartTime());
            taskUpdate.setEndTime(epicTask.getEndTime());
            taskUpdate.setDuration(epicTask.getDuration());
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
            TreeSet<Task> treeSet = (TreeSet<Task>) this.getPrioritizedTasks();
            treeSet.remove(subTaskMap.get(taskUpdate.getId()));
            if (freeTime(treeSet,taskUpdate) == 0 ) {
            SubTask subTask = subTaskMap.get(taskUpdate.getId());
            taskUpdate.setEpicTaskNumber(subTask.getEpicTaskNumber());
            subTaskMap.put(taskUpdate.getId(), taskUpdate);
            treeSet.add(taskUpdate);
            int numberOfEpicTask = subTask.getEpicTaskNumber();
            int counterNew = 0;
            int counterInProcess = 0;
            int counterDone = 0;
            int duration = 0;
            EpicTask epicTask = epicTaskMap.get(numberOfEpicTask);
            List<Integer> listSubTask = epicTask.getListSubtask();
            Optional<LocalDateTime> start = epicTask.getStartTime();
            Optional<LocalDateTime> end = epicTask.getEndTime();
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

                if(subTask1.getStartTime().get().isBefore(start.get())){
                    start = subTask1.getStartTime();
                }

                if(subTask1.getEndTime().get().isAfter(end.get())){
                    end = subTask1.getEndTime();
                }

            }
            epicTask.setStartTime(start);
            epicTask.setDuration(duration);
            epicTask.setEndTime(end);

            if (counterNew == epicTask.getListSubtask().size()) {
            } else if (counterInProcess > 0) {
                epicTask.setStatus(Status.IN_PROGRESS);
            } else if (counterDone == epicTask.getListSubtask().size()) {
                epicTask.setStatus(Status.DONE);
            }
            System.out.println("Подзадача обновлена");
        } else {
                System.out.println("Подзадача пересекается по времени с другими задачами и не будет добавлена");
            }
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

    public int freeTime(TreeSet<Task> treeSet, Task task){
        int count = 0;
        for (Task task1 : treeSet) {
            if (task.getStartTime().get().isBefore(task1.getStartTime().get()) && task.getEndTime().get()
                    .isAfter(task1.getEndTime().get())) {
                count++;
            }else if(task.getStartTime().get().isBefore(task1.getStartTime().get()) && task.getEndTime().get()
                    .isAfter(task1.getStartTime().get())) {
                count++;
            } else if (task.getStartTime().get().isAfter(task1.getStartTime().get()) && task.getEndTime().get()
                    .isBefore(task1.getStartTime().get())) {
                count++;
            }else if (task.getStartTime().get().isAfter(task1.getStartTime().get()) && task.getStartTime().get()
                    .isBefore(task1.getEndTime().get())) {
                count++;
            }else if ((task.getStartTime().get().isAfter(task1.getStartTime().get())) && task.getEndTime()
                    .equals(task1.getEndTime())){
                count++;
            } else if (task.getStartTime().get().isEqual(task1.getStartTime().get()) && task.getEndTime().get()
                    .isEqual(task1.getEndTime().get())){
                count++;
            } else if (task.getStartTime().get().isEqual(task1.getStartTime().get()) && task.getEndTime().get()
                    .isBefore(task1.getEndTime().get())) {
                count++;
            } else if (task.getStartTime().get().isEqual(task1.getStartTime().get()) && task.getEndTime().get()
                    .isAfter(task1.getEndTime().get())) {
                count++;
            } else if (task.getEndTime().get().isEqual(task1.getStartTime().get())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void fromFile() throws IOException {
    }
}