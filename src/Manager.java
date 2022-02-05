import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Manager {
    int counterID = 1;
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
        Set<Integer> setKeysTask = taskMap.keySet();
        for (Object i : setKeysTask) {
            Task task = taskMap.get(i);
            if (key == task.getId()) {
                System.out.println(task);
            } else {
                System.out.println("Tакого id в списке задач - нет");
            }
        }
    }

    //Получение задачи EpicTask по идентификатору.
    public void getingAnObjectByIdEpicTask (int key){
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        for (Object i : setKeysTask) {
            EpicTask epicTask = epicTaskMap.get(i);
            if (key == epicTask.getId()) {
                System.out.println(epicTask);
            } else {
                System.out.println("Tакого id в списке Эпик задач - нет");
            }
        }
    }

    //Получение задачи SubTask по идентификатору.
    public void getingAnObjectByIdSubTask (int key){
        Set<Integer> setKeysTask = subTaskMap.keySet();
        for (Object i : setKeysTask) {
            SubTask subTask = subTaskMap.get(i);
            if (key == subTask.getId()) {
                System.out.println(subTask);
            } else {
                System.out.println("Tакого id в списке подзадач - нет");
            }
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
        Set<Integer> setKeysTask = taskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке задач - нет");
        } else {
        for (Object i : setKeysTask) {
            Task task = taskMap.get(i);
            if (key != task.getId()) {
                System.out.println("Tакого id в списке задач - нет");
            } else {
                taskMap.remove(key);
                System.out.println("Задача удалена");
                }
            }
        }
    }

    //Удаление задачи EpicTask по идентификатору.
    public void deleteAnObjectByIdEpicTask (int key){
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке Эпик задач - нет");
        } else {
        for (Object i : setKeysTask) {
            EpicTask epicTask = epicTaskMap.get(i);
            if (key != epicTask.getId()) {
                System.out.println("Tакого id в списке Эпик задач - нет");
            } else {
                ArrayList<Integer> listSubTask = epicTask.listSubtask;
                for (int j : listSubTask){
                    subTaskMap.remove(j);
                }
                epicTaskMap.remove(key);
                System.out.println("Эпик задача удалена вмести с подзадачами");
                }
            }
        }
    }

    //Удаление задачи SubTask по идентификатору.
    public void deleteAnObjectByIdSubTask (int key){
        Set<Integer> setKeysTask = subTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке подзадач - нет");
        } else {
        for (Object i : setKeysTask) {
            SubTask subTask = subTaskMap.get(i);
            if (key != subTask.getId()) {
                System.out.println("Tакого id в списке подзадач - нет");
            } else {
                subTaskMap.remove(key);
                System.out.println("Подзадача удалена");
            }
        }
        }
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

    //Обнавление задачи Task
    public HashMap<Integer, Task> updatingAnObjectByIdTask (Task taskUpdate) {
        Set<Integer> setKeysTask = taskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке задач - нет");
        } else {
            for (Object i : setKeysTask) {
                Task task = taskMap.get(i);
                if (taskUpdate.getId() != task.getId()) {
                    System.out.println("Tакого id в списке задач - нет");
                } else {
                    taskMap.put(taskUpdate.getId(), taskUpdate);
                    System.out.println("Задача обновлена");
                }
            }
        }return taskMap;
    }

    //Обнавление задачи EpicTask
    public void updatingAnObjectByIdEpicTask (EpicTask taskUpdate) {
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке задач - нет");
        } else {
            for (Object i : setKeysTask) {
                EpicTask epicTask = epicTaskMap.get(i);
                if (taskUpdate.getId() != epicTask.getId()) {
                    System.out.println("Tакого id в списке задач - нет");
                } else {
                    epicTaskMap.put(taskUpdate.getId(), taskUpdate);
                    System.out.println("Задача обновлена");
                }
            }
        }
    }

    //Обнавление задачи SubTask
    public void updatingAnObjectByIdSubTask (SubTask taskUpdate) {
        Set<Integer> setKeysTask = subTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке задач - нет");
        } else {
            for (Object i : setKeysTask) {
                SubTask subTask = subTaskMap.get(i);
                if (taskUpdate.getId() != subTask.getId()) {
                    System.out.println("Tакого id в списке задач - нет");
                } else {
                    taskUpdate.setEpicTaskNumber(subTask.epicTaskNumber);
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
        }
    }

    //Получение всех задач Эпик задачи
    public void getallSubTaskInEpic(int id){
        Set<Integer> setKeysTask = epicTaskMap.keySet();
        if(setKeysTask.size()==0){
            System.out.println("Tакого id в списке задач - нет");
        } else {
            for (Object i : setKeysTask) {
                EpicTask epicTask = epicTaskMap.get(i);
                if (epicTask.getId() != epicTask.getId()) {
                    System.out.println("Tакого id в списке Эпик задач - нет");
                } else {
                    ArrayList<Integer> listSubTask = epicTask.listSubtask;
                    if(listSubTask.size() == 0){
                        System.out.println("У Эпик задачи нет подзадач");
                    }else {
                        System.out.println("Список № подзадач " + listSubTask + " у Эпик задачи № " + epicTask.getId());
                }
            }
        }
    }
    }

}
