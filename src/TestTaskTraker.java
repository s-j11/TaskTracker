import java.util.HashMap;

public class TestTaskTraker {

    Manager manager = new Manager();
    HashMap<Integer,Task> taskHashMap = manager.taskMap;
    HashMap<Integer,EpicTask> epicTaskMap = manager.epicTaskMap;
    HashMap<Integer,SubTask> subTaskMap= manager.subTaskMap;

    public TestTaskTraker() {
    }

    public void testEpicTask (){
        Task task = manager.maikingTask("Планирование","Разработка плана");
        EpicTask epicTask = manager.maikingEpic("Разработка", "Разработка приложения");
        EpicTask epicTask1 = manager.maikingEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = manager.maikingSubTask("Разработка меню","Разработка класса меню",2);
        SubTask subTask1 = manager.maikingSubTask("Разработка логики","Разработка класса логики",2);
        SubTask subTask2 = manager.maikingSubTask("Класс тестирования","Разработка класа тестирования",
                3);

        System.out.println(task);
        System.out.println(epicTask);
        System.out.println(epicTask1);
        System.out.println(subTask);
        System.out.println(subTask1);
        System.out.println(subTask2);

        task = new Task("Планирование 2", "Разработака плана2", task.getId(), "IN_PROGRESS");
        taskHashMap = manager.updatingAnObjectByIdTask(task);
        manager.updatingAnObjectByIdEpicTask(epicTask1);
        subTask = new SubTask("Разработка меню 2", "Разработка класса меню 2", subTask.getId(),
                "IN_PROGRESS",subTask.getEpicTaskNumber());
        manager.updatingAnObjectByIdSubTask(subTask);
        subTask1 = new SubTask("Разработка логики 2", "Разработка класса логики 2", subTask1.getId(),
                "DONE", subTask1.getEpicTaskNumber());
        manager.updatingAnObjectByIdSubTask(subTask1);
        subTask2 = new SubTask("Класс тестирования 2", "Разработка класа тестирования 2",
                subTask2.getId(), "IN_PROGRESS", subTask2.getEpicTaskNumber());
        manager.updatingAnObjectByIdSubTask(subTask2);
        epicTask = new EpicTask("Разработка 2","Разработка приложение 2", epicTask.getId(),
                epicTask.getStatus(),epicTask.getListSubtask());
        manager.updatingAnObjectByIdEpicTask(epicTask);
        epicTask1 = new EpicTask("Тестирование 2","Разработка тестирования 2", epicTask1.getId(),
                epicTask1.getStatus(),epicTask1.getListSubtask());
        manager.updatingAnObjectByIdEpicTask(epicTask1);

        System.out.println(task);
        System.out.println(subTask);
        System.out.println(subTask1);
        System.out.println(subTask2);
        System.out.println(epicTask);
        System.out.println(epicTask1);

        subTask = new SubTask("Разработка меню 3", "Разработка класса меню 3", subTask.getId(),
                "DONE",subTask.getEpicTaskNumber());
        manager.updatingAnObjectByIdSubTask(subTask);
        subTask2 = new SubTask("Класс тестирования 3", "Разработка класа тестирования 3",
                subTask2.getId(), "DONE", subTask2.getEpicTaskNumber());
        manager.updatingAnObjectByIdSubTask(subTask2);

        System.out.println(subTask);
        System.out.println(subTask2);
        System.out.println(epicTask);
        System.out.println(epicTask1);

        manager.deleteAnObjectByIdTask(1);
        manager.deleteAnObjectByIdSubTask(4);
        manager.deleteAnObjectByIdEpicTask(3);

        System.out.println(taskHashMap);
        System.out.println(epicTaskMap);
        System.out.println(subTaskMap);


        taskHashMap.clear();
        epicTaskMap.clear();
        subTaskMap.clear();
        System.out.println();
    }

}
