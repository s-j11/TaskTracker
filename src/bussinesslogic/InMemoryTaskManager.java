package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager{
    private Integer counterID = 1;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, EpicTask> epicTasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
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
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    //Получение map EpicTasks
    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        return epicTasks;
    }
    //Получение map SubTasks
    @Override
    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    //Получение списка всех задач model.Task.
    @Override
    public Collection getTasksСatalogue(Map<Integer, Task> tasks) {
        Collection tasksСatalogue = new ArrayList<>();
        Set<Integer> setKeys = tasks.keySet();
        for (int i : setKeys) {
            Task task = tasks.get(i);
            tasksСatalogue.add(task);
        }
        return tasksСatalogue;
    }

    //Получение списка всех Эпик задач model.EpicTask.
    @Override
    public Collection getEpicTasksСatalogue(Map<Integer, EpicTask> epicTasks) {
        Collection epicTaskСatalogue = new ArrayList<>();
        Set<Integer> setKeys = epicTasks.keySet();
        for (int i : setKeys) {
            EpicTask epicTask = epicTasks.get(i);
            epicTaskСatalogue.add(epicTask);
        }
        return epicTaskСatalogue;
    }

    //Получение списка всех подзадач model.SubTask.
    @Override
    public List<SubTask> getSubTasksСatalogue(Map<Integer, SubTask> subTasks) {
        List<SubTask> subTaskСatalogue = new ArrayList<>();
        Set<Integer> setKeys = subTasks.keySet();
        for (int i : setKeys) {
            SubTask subTask = subTasks.get(i);
            subTaskСatalogue.add(subTask);
        }
        return subTaskСatalogue;
    }

    //Удаление всех задач model.Task.
    @Override
    public void deleteAllTasks() {
        for (Map.Entry<Integer,Task> entry: tasks.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty()){
            historyManager.remove(task);
        }}
        for (Map.Entry<Integer,Task> entry : tasks.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
        }
        tasks.clear();
    }

    //Удаление всех эпик задач model.EpicTask.
    @Override
    public void deleteAllEpicTasks() {
        for (Map.Entry<Integer,EpicTask> entry: epicTasks.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty() && historyManager.getHistory().contains(task)){
                List<Integer> numberSubtask = entry.getValue().getSubtaskCatalogue();
                for(int number : numberSubtask){
                    Node subTask = new Node(subTasks.get(number));
                    historyManager.remove(subTask);
                }
            historyManager.remove(task);
        }}
        for (Map.Entry<Integer,SubTask> entry : subTasks.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
        }
        epicTasks.clear();
        subTasks.clear();
    }

    //Удаление всех подзадач subTask.
    @Override
    public void deleteAllSubTasks() {
        for (Map.Entry<Integer,SubTask> entry: subTasks.entrySet()) {
            Node task = new Node(entry.getValue());
            if(!historyManager.getHistory().isEmpty() && historyManager.getHistory().contains(task)) {
                historyManager.remove(task);
            }
        }
        Set<Integer> keysEpicTask = epicTasks.keySet();
        for (int i : keysEpicTask) {
            EpicTask epicTask = epicTasks.get(i);
            epicTask.getSubtaskCatalogue().clear();
            epicTask.setStartTime(Optional.empty());
            epicTask.setEndTime(Optional.empty());
            epicTask.setDuration(0);
        }
        for (Map.Entry<Integer,SubTask> entry : subTasks.entrySet()) {
            prioritizedTasks.remove(entry.getValue());
        }
        subTasks.clear();
    }

    //Получение задачи model.Task по идентификатору.
    @Override
    public void getTaskById(int key) {
        if (tasks.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!tasks.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            System.out.println(tasks.get(key));
            historyManager.add(tasks.get(key));
        }
    }

    //Получение задачи model.EpicTask по идентификатору.
    @Override
    public void getEpicTaskById(int key) {
        if (epicTasks.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTasks.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            System.out.println(epicTasks.get(key));
            historyManager.add(epicTasks.get(key));
        }
    }

    //Получение задачи model.SubTask по идентификатору.
    @Override
    public void getSubTaskById(int key) {
        if (subTasks.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTasks.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            System.out.println(subTasks.get(key));
            historyManager.add(subTasks.get(key));
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
        TreeSet<Task> taskPrioritized = getPrioritizedTasks();
        int result = freeTime(taskPrioritized,task);
        if ( result != 0) {
            System.out.println("Задача пересекается по времени с другими задачами и не будет добавлена");
        } else {
            tasks.put(task.getId(), task);
            taskPrioritized.add(task);
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
        epicTasks.put(epicTask.getId(), epicTask);
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
        Set<Integer> keysTask = epicTasks.keySet();
        TreeSet<Task> treeSet = getPrioritizedTasks();
        if (freeTime(treeSet,subTask) == 0) {
        Optional<LocalDateTime> start = null;
        Optional<LocalDateTime> end = null;
        int time; 
        if (keysTask.size() == 0) {
            System.out.println("Tакого id в списке Эпик задач - нет");
        } else {
            for (int i : keysTask) {
                EpicTask epicTask = epicTasks.get(i);
                if (id != epicTask.getId()) {
                    System.out.println("Tакого id в списке Эпик задач - нет");
                } else {
                    subTask.setId(counterID++);
                    epicTask.setDuration(epicTask.getDuration() + subTask.getDuration());
                    if (epicTask.getSubtaskCatalogue().isEmpty()) {
                        epicTask.setStartTime(subTask.getStartTime());
                        epicTask.setEndTime(subTask.getEndTime());
                    } else {
                        start = epicTask.getStartTime();
                        end = epicTask.getEndTime();
                        for (int j : epicTask.getSubtaskCatalogue()) {
                            SubTask subTaskBuffer = subTasks.get(j);
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
                    subTasks.put(subTask.getId(), subTask);
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
        if (tasks.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!tasks.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            prioritizedTasks.remove(tasks.get(key));
            tasks.remove(key);
            System.out.println("Задача удалена");
        }
        Node<Task> taskNode = new Node<>(tasks.get(key));
        if(!historyManager.getHistory().isEmpty()) {
        historyManager.remove(taskNode);
    }
    }

    //Удаление задачи model.EpicTask по идентификатору.
    @Override
    public void deleteEpicTaskById(int key) {
        if (epicTasks.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTasks.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTasks.get(key);
            Collection subTasks = epicTask.getSubtaskCatalogue();
            if(!subTasks.isEmpty()){
            for (Object i : subTasks) {
                prioritizedTasks.remove(this.subTasks.get(i));
                this.subTasks.remove(i);
                Node<Task> taskNode = new Node<>(this.subTasks.get(i));
                if(!historyManager.getHistory().isEmpty()&&historyManager.getHistory().contains(taskNode)) {
                    historyManager.remove(taskNode);
                } }
            }
            epicTasks.remove(key);
            System.out.println("Эпик задача удалена вмести с подзадачами");
        }
        Node<Task> taskNode = new Node<>(epicTasks.get(key));
        if(!historyManager.getHistory().isEmpty()) {
        historyManager.remove(taskNode);
        }
    }

    //Удаление задачи model.SubTask по идентификатору.
    @Override
    public void deleteSubTaskById(int key) {
        Node<Task> taskNode = new Node<>(subTasks.get(key));
        if(!historyManager.getHistory().isEmpty()) {
            historyManager.remove(taskNode);
        }
        if (subTasks.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTasks.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            prioritizedTasks.remove(subTasks.get(key));
            subTasks.remove(key);
            System.out.println("Подзадача удалена");
            List<Integer> idSubTasks;
            Set<Integer> setKeys = epicTasks.keySet();
            for (int k : setKeys) {
                EpicTask epicTask = epicTasks.get(k);
                idSubTasks = epicTask.getSubtaskCatalogue();
                if (idSubTasks.size() == 0) {
                    continue;
                } else {
                    for (int j = 0; j < idSubTasks.size(); j++) {
                        int idSubTask = idSubTasks.get(j);
                        if (key == idSubTask) {
                            idSubTasks.remove(j);
                            epicTask.setSubTaskСatalogue(idSubTasks);
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
        if (tasks.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!tasks.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            TreeSet<Task> treeSet = getPrioritizedTasks();
            treeSet.remove(tasks.get(taskUpdate.getId()));
            if (freeTime(treeSet,taskUpdate) == 0 ) {
                tasks.put(taskUpdate.getId(), taskUpdate);
                treeSet.add(taskUpdate);
                System.out.println("Номер вашей задачи " + taskUpdate.getId() + "\n");
                System.out.println("Задача обновлена");
                return tasks;
            } else {
                System.out.println("Задача пересекается по времени с другими задачами и не будет добавлена");
            }
        }
        return tasks;
    }

    //Обнавление задачи model.EpicTask
    @Override
    public void updateEpicTaskById(EpicTask taskUpdate) {
        if (epicTasks.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTasks.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTasks.get(taskUpdate.getId());
            taskUpdate.setSubTaskСatalogue(epicTask.getSubtaskCatalogue());
            taskUpdate.setStatus(epicTask.getStatus());
            taskUpdate.setStartTime(epicTask.getStartTime());
            taskUpdate.setEndTime(epicTask.getEndTime());
            taskUpdate.setDuration(epicTask.getDuration());
            epicTasks.put(taskUpdate.getId(), taskUpdate);
            System.out.println("Задача обновлена");
        }
    }


    //Обнавление задачи model.SubTask
    @Override
    public void updateSubTaskById(SubTask taskUpdate) {
        if (subTasks.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTasks.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            TreeSet<Task> tasksPrioritized = (TreeSet<Task>) this.getPrioritizedTasks();
            tasksPrioritized.remove(subTasks.get(taskUpdate.getId()));
            if (freeTime(tasksPrioritized,taskUpdate) == 0 ) {
            SubTask subTask = subTasks.get(taskUpdate.getId());
            taskUpdate.setEpicTaskNumber(subTask.getEpicTaskNumber());
            subTasks.put(taskUpdate.getId(), taskUpdate);
            tasksPrioritized.add(taskUpdate);
            int numberOfEpicTask = subTask.getEpicTaskNumber();
            int counterNew = 0;
            int counterInProcess = 0;
            int counterDone = 0;
            int duration = 0;
            EpicTask epicTask = epicTasks.get(numberOfEpicTask);
            List<Integer> SubTasksCatalogue = epicTask.getSubtaskCatalogue();
            Optional<LocalDateTime> start = epicTask.getStartTime();
            Optional<LocalDateTime> end = epicTask.getEndTime();
            for (int j : SubTasksCatalogue) {
                SubTask subTask1 = subTasks.get(j);
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

            if (counterNew == epicTask.getSubtaskCatalogue().size()) {
            } else if (counterInProcess > 0) {
                epicTask.setStatus(Status.IN_PROGRESS);
            } else if (counterDone == epicTask.getSubtaskCatalogue().size()) {
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
        if (epicTasks.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTasks.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTasks.get(key);
            Collection listSubTask = epicTask.getSubtaskCatalogue();
            if (listSubTask.size() == 0) {
                System.out.println("У Эпик задачи нет подзадач");
            } else {
                System.out.println("Список id подзадач " + listSubTask + " у Эпик задачи id " + epicTask.getId());
            }
        }
    }

    public int freeTime(TreeSet<Task> tasksPrioritized, Task task){
        int count = 0;
        for (Task task1 : tasksPrioritized) {
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