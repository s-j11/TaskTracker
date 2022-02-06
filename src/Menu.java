import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    HashMap<Integer,Task> taskMap = manager.taskMap;
    HashMap<Integer,EpicTask> epicTaskMap = manager.epicTaskMap;
    HashMap<Integer,SubTask> subTaskMap = manager.subTaskMap;
    TestTaskTraker testTaskTraker = new TestTaskTraker();

    @Override
    public String toString() {
        return "{"
                + "taskMap=" + taskMap
                + ", epicTaskMap=" + epicTaskMap
                + ", subTaskMap=" + subTaskMap + '}';
    }

    public void prinMenu() {
        while (true) {
            System.out.println("Какие операции Вы хотите провести в приложение \"Tрекер задач\"?");
            System.out.println("1 - Вывести список всех задач.");
            System.out.println("2 - Удаление всех задач");
            System.out.println("3 - Вывести задачу по ее номеру");
            System.out.println("4 - Создать новую задачу");
            System.out.println("5 - Изменить задачу по ее номеру");
            System.out.println("6 - Удалить задачу по ее номеру");
            System.out.println("7 - Вывести список подзадач по номеру Эпик");
            System.out.println("8 - Узнать статус задачи по номеру");
            System.out.println("9 - Выйти из приложения");

            int command = scanner.nextInt();

            if (command == 1) {
                System.out.println("Список всех задач: ");
                        System.out.println("Список задач: " + manager.getListTasks(taskMap));
                        System.out.println("Список Эпик задач: " + manager.getListEpicTasks(epicTaskMap));
                        System.out.println("Список подзадач: " + manager.getListSubTasks(subTaskMap));
            } else if (command == 2) {
                System.out.println("Введите номер списка задач, который хотите удалить:\n"
                        + "1 - Список задач\n"
                        + "2 - Список эпиков\n"
                        + "3 - Список подзадач");
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        manager.deleteAllTasks();
                        System.out.println("Список задач удален");
                        break;
                    case 2:
                        manager.deleteAllEpic();
                        System.out.println("Список Эпик задач удален и связыанные с ними подзадачи");
                        break;
                    case 3:
                        manager.deleteAllSubTask();
                        System.out.println("Список подзадач удален");
                        break;
                    default:
                        System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
            } else if (command == 3) {
                System.out.println("Введите id задачи, которую хотите найти: ");
                int id = scanner.nextInt();
                manager.getingAnObjectByIdTask(id);
                manager.getingAnObjectByIdEpicTask(id);
                manager.getingAnObjectByIdSubTask(id);
            } else if (command == 4) {
                System.out.println("Введите название задачи");
                String name = scanner.next();
                System.out.println("Введите описание задачи");
                String descriptions = scanner.next();
                System.out.println("Ведите тип задачи которую хотите завести:\n"
                        + "1 - Задача\n"
                        + "2 - Эпик\n"
                        + "3 - Подзадача");
                int button = scanner.nextInt();
                switch (button){
                    case 1:
                        manager.maikingTask(name,descriptions);
                        break;
                    case 2:
                        manager.maikingEpic(name,descriptions);
                        break;
                    case 3:
                        System.out.println("Введите номер эпик задачи");
                        int id = scanner.nextInt();
                        manager.maikingSubTask(name,descriptions,id);
                        break;
                        default:
                            System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
            } else if (command == 5) {
                System.out.println("Введите id задачи, которую хотите обновить: ");
                int id = scanner.nextInt();
                System.out.println("Введите новое название: ");
                String name = scanner.next();
                System.out.println("Введите новое описание задачи: ");
                String descriptions = scanner.next();
                System.out.println("Выберети новый статус задачи:\n"
                        + "1 - NEW\n"
                        + "2 - IN_PROGRESS\n"
                        + "3 - DONE");
                String status = null;
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        status = "NEW";
                        break;
                    case 2:
                        status = "IN_PROGRESS";
                        break;
                    case 3:
                        status = "DONE";
                        break;
                    default:
                        System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
               status = status;
                Task task = new Task(name, descriptions, id, status);
                SubTask subTask = new SubTask(name,descriptions,id,status);
                EpicTask epicTask = new EpicTask(name, descriptions, id);
                manager.updatingAnObjectByIdTask(task);
                manager.updatingAnObjectByIdEpicTask(epicTask);
                manager.updatingAnObjectByIdSubTask(subTask);
            } else if (command == 6) {
                System.out.println("Введите id задачи, которую хотите удалить: ");
                int id = scanner.nextInt();
                manager.deleteAnObjectByIdTask(id);
                manager.deleteAnObjectByIdEpicTask(id);
                manager.deleteAnObjectByIdSubTask(id);
            } else if (command == 7) {
                System.out.println("Введите id Эпик задачи, в которой хотите просмотреть все подзадачи: ");
                int id = scanner.nextInt();
                manager.getallSubTaskInEpic(id);
            } else if (command == 8) {
                testTaskTraker.testEpicTask();
            } else if (command == 9) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды нет.");
            }
        }
   }
}


