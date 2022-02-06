import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Manager {
    Integer counterID = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, EpicTask> epicTaskMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public Manager() {
    }

    //Получение списка всех задач Task.
    public  List<Object> getListTasks(HashMap<Integer, Task> mapObjects) {
        List<Object> keysObjects = new ArrayList<>();
        Set<Integer> setKeys = mapObjects.keySet();
        for (Object i: setKeys){
            Object keyTask = mapObjects.get(i);
            keysObjects.add(keyTask);
        } return keysObjects;
    }

    //Получение списка всех Эпик задач EpicTask.
    public  List<Object> getListEpicTasks(HashMap<Integer, EpicTask> mapObjects) {
        List<Object> keysObjects = new ArrayList<>();
        Set<Integer> setKeys = mapObjects.keySet();
        for (Object i: setKeys){
            Object keyTask = mapObjects.get(i);
            keysObjects.add(keyTask);
        } return keysObjects;
    }

    //Получение списка всех подзадач SubTask.
    public  List<Object> getListSubTasks(HashMap<Integer, SubTask> mapObjects) {
        List<Object> keysObjects = new ArrayList<>();
        Set<Integer> setKeys = mapObjects.keySet();
        for (Object i: setKeys){
            Object keyTask = mapObjects.get(i);
            keysObjects.add(keyTask);
        } return keysObjects;
    }

    //Удаление всех задач Task.
    public  void deleteAllTasks()
    {
        taskMap.clear();
    }

    //Удаление всех эпик задач EpicTask.
    public  void deleteAllEpic() {
    epicTaskMap.clear();
    subTaskMap.clear();
    }

    //Удаление всех подзадач subTask.
    public  void deleteAllSubTask() {
        Set<Integer> setKeys = epicTaskMap.keySet();
        for (Object i: setKeys){
            EpicTask epicTask = epicTaskMap.get(i);
            epicTask.listSubtask.clear();
        }
        subTaskMap.clear();
    }

    //Получение задачи Task по идентификатору.
    public void getingAnObjectByIdTask (int key) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            System.out.println(taskMap.get(key));
                }
            }


    //Получение задачи EpicTask по идентификатору.
    public void getingAnObjectByIdEpicTask (int key){
        if (epicTaskMap.isEmpty()) {
                System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            System.out.println(epicTaskMap.get(key));
        }
    }

    //Получение задачи SubTask по идентификатору.
    public void getingAnObjectByIdSubTask (int key){
        if (subTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
            System.out.println(subTaskMap.get(key));
        }
    }

    //Создание задачи Task
    public Task maikingTask(String name, String description){
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setId(counterID++);
        task.setStatus("NEW");
        taskMap.put(task.getId(), task);
        System.out.println("Номер вашей задачи " + task.getId() + "\n");
        return task;
    }

    //Создание задачи EpicTask
    public EpicTask maikingEpic(String name, String description){
        EpicTask epicTask = new EpicTask();
        epicTask.setName(name);
        epicTask.setDescription(description);
        epicTask.setId(counterID++);
        epicTask.setStatus("NEW");
        epicTaskMap.put(epicTask.getId(), epicTask);
        System.out.println("Номер вашей Эпик задачи " + epicTask.getId() + "\n");
        return epicTask;
    }

    //Создание задачи SubTask
    public SubTask maikingSubTask(String name, String description, int id){
        SubTask subTask = new SubTask();
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setEpicTaskNumber(id);
        subTask.setStatus("NEW");
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке Эпик задач - нет");
        } else {
            for (Object i : setKeysTask) {
                EpicTask epicTask = epicTaskMap.get(i);
                if (id != epicTask.getId()) {
                    System.out.println("Tакого id в списке Эпик задач - нет");
                } else {
        subTask.setId(counterID++);
        epicTask.addSubTask(subTask.getId());
        subTaskMap.put(subTask.getId(), subTask);
        System.out.println("Номер вашей подзадачи " + subTask.getId() + "\n"
                + "Она входит в Эпик задачу " + subTask.epicTaskNumber + "\n");

                }
            }
        }
        return subTask;
    }

    //Удаление задачи Task по идентификатору.
    public void deleteAnObjectByIdTask (int key) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(key)) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            taskMap.remove(key);
            System.out.println("Задача удалена");
                }
            }

    //Удаление задачи EpicTask по идентификатору.
    public void deleteAnObjectByIdEpicTask (int key){
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTaskMap.get(key);
            ArrayList<Integer> listSubTask = epicTask.listSubtask;
                for (int i : listSubTask){
                    subTaskMap.remove(i);
                }
                epicTaskMap.remove(key);
                System.out.println("Эпик задача удалена вмести с подзадачами");
                }
            }

    //Удаление задачи SubTask по идентификатору.
    public void deleteAnObjectByIdSubTask (int key){
         if (subTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else if (!subTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке подзадач задач - нет");
        } else {
             subTaskMap.remove(key);
             System.out.println("Подзадача удалена");
             ArrayList<Integer> numberOfSubTask;
             Set<Integer> setKeys = epicTaskMap.keySet();
             for (Object k: setKeys){
                 EpicTask epicTask = epicTaskMap.get(k);
                 numberOfSubTask = epicTask.listSubtask;
                 if (numberOfSubTask.size() == 0){
                     continue;
                 }else{
                     for ( int j =0; j < numberOfSubTask.size(); j++) {
                         int idSubTask = numberOfSubTask.get(j);
                         if (key == idSubTask) {
                             numberOfSubTask.remove(j);
                             epicTask.setListSubtask(numberOfSubTask);
                             break;
                         }
                     }
                 }
             }
        }
    }

    //Обнавление задачи Task
    public HashMap<Integer, Task> updatingAnObjectByIdTask (Task taskUpdate) {
        if (taskMap.isEmpty()) {
            System.out.println("Tакого id в списке задач - нет");
        } else if (!taskMap.containsKey(taskUpdate.getId())) {
            System.out.println("Tакого id в списке задач - нет");
        } else {
            taskMap.put(taskUpdate.getId(), taskUpdate);
            System.out.println("Задача обновлена");
        }return taskMap;
    }

    //Обнавление задачи EpicTask
    public void updatingAnObjectByIdEpicTask (EpicTask taskUpdate) {
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


    //Обнавление задачи SubTask
    public void updatingAnObjectByIdSubTask (SubTask taskUpdate) {
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
            EpicTask epicTask = epicTaskMap.get(numberOfEpicTask);
            ArrayList<Integer> listSubTask = epicTask.listSubtask;
            for(int j : listSubTask){
                SubTask subTask1 = subTaskMap.get(j);
                String status = subTask1.getStatus();
                if(status == "NEW"){
                    counterNew++;
                }else if(status == "IN_PROGRESS"){
                    counterInProcess++;
                }else{
                    counterDone++;
                }
            }
            if(counterNew == epicTask.listSubtask.size()){
            }else if(counterInProcess>0){
                epicTask.setStatus("IN_PROGRESS");
            }else if(counterDone == epicTask.listSubtask.size()){
                epicTask.setStatus("DONE");
            }
            System.out.println("Подзадача обновлена");
        }
    }

    //Получение всех задач Эпик задачи
    public void getallSubTaskInEpic(int key){
        if (epicTaskMap.isEmpty()) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else if (!epicTaskMap.containsKey(key)) {
            System.out.println("Tакого id в списке эпик задач - нет");
        } else {
            EpicTask epicTask = epicTaskMap.get(key);
            ArrayList<Integer> listSubTask = epicTask.listSubtask;
            if(listSubTask.size() == 0){
                System.out.println("У Эпик задачи нет подзадач");
            }else {
                System.out.println("Список id подзадач " + listSubTask + " у Эпик задачи id " + epicTask.getId());
                }
            }
        }
    }


