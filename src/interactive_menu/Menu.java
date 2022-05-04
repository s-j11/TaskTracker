package interactive_menu;

import bussinesslogic.Managers;
import maketbussinesslogic.TaskManager;
import model.*;
import test.TestTaskTraker;

import java.io.IOException;
import java.util.*;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Managers managers = new Managers();
    private TaskManager  fileBackedTasksManager = managers.getDefaultFileBackedManager();
    private Map<Integer, Task> taskMap = fileBackedTasksManager.getTaskMap();
    private Map<Integer, EpicTask> epicTaskMap = fileBackedTasksManager.getEpicTaskMap();
    private Map<Integer, SubTask> subTaskMap = fileBackedTasksManager.getSubTaskMap();
    private TestTaskTraker testTaskTraker = new TestTaskTraker(taskMap, epicTaskMap, subTaskMap);

    @Override
    public String toString() {
        return "{"
                + "taskMap=" + taskMap
                + ", epicTaskMap=" + epicTaskMap
                + ", subTaskMap=" + subTaskMap + '}';
    }

    public void prinMenu() throws IOException {
        while (true) {
            System.out.println("Какие операции Вы хотите провести в приложение \"Tрекер задач\"?");
            System.out.println("1 - Вывести список всех задач.");
            System.out.println("2 - Удаление всех задач");
            System.out.println("3 - Вывести задачу по ее номеру");
            System.out.println("4 - Создать новую задачу");
            System.out.println("5 - Изменить задачу по ее номеру");
            System.out.println("6 - Удалить задачу по ее номеру");
            System.out.println("7 - Вывести список подзадач по номеру Эпик");
            System.out.println("8 - Тестирование");
            System.out.println("9 - Вывести историю последних 10 операций");
            System.out.println("10 - Загрузить данные из файла");
            System.out.println("11 - Выйти из приложения");


            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println("Список всех задач: ");
                System.out.println("Список задач: " + fileBackedTasksManager.getListTasks(taskMap));
                System.out.println("Список Эпик задач: " + fileBackedTasksManager.getListEpicTasks(epicTaskMap));
                System.out.println("Список подзадач: " + fileBackedTasksManager.getListSubTasks(subTaskMap));
            } else if (command == 2) {
                System.out.println("Введите номер списка задач, который хотите удалить:\n"
                        + "1 - Список задач\n"
                        + "2 - Список эпиков\n"
                        + "3 - Список подзадач");
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        fileBackedTasksManager.deleteAllTask();
                        System.out.println("Список задач удален");
                        break;
                    case 2:
                        fileBackedTasksManager.deleteAllEpic();
                        System.out.println("Список Эпик задач удален и связыанные с ними подзадачи");
                        break;
                    case 3:
                        fileBackedTasksManager.deleteAllSubTask();
                        System.out.println("Список подзадач удален");
                        break;
                    default:
                        System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
            } else if (command == 3) {
                System.out.println("Введите id задачи, которую хотите найти: ");
                int id = scanner.nextInt();
                fileBackedTasksManager.getTaskById(id);
                fileBackedTasksManager.getEpicTaskById(id);
                fileBackedTasksManager.getSubTaskById(id);
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
                switch (button) {
                    case 1:
                        fileBackedTasksManager.makeTask(name, descriptions);
                        break;
                    case 2:
                        fileBackedTasksManager.makeEpic(name, descriptions);
                        break;
                    case 3:
                        System.out.println("Введите номер эпик задачи");
                        int id = scanner.nextInt();
                        fileBackedTasksManager.makeSubTask(name, descriptions, id);
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
                Status status = null;
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        status = Status.NEW;
                        break;
                    case 2:
                        status = Status.IN_PROGRESS;
                        break;
                    case 3:
                        status = Status.DONE;
                        break;
                    default:
                        System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
                status = status;
                Task task = new Task(name, descriptions, id, status);
                SubTask subTask = new SubTask(name, descriptions, id, status);
                EpicTask epicTask = new EpicTask(name, descriptions, id);
                fileBackedTasksManager.updateTaskById(task);
                fileBackedTasksManager.updateEpicTaskById(epicTask);
                fileBackedTasksManager.updateSubTaskById(subTask);
            } else if (command == 6) {
                System.out.println("Введите id задачи, которую хотите удалить: ");
                int id = scanner.nextInt();
                fileBackedTasksManager.deleteTaskById(id);
                fileBackedTasksManager.deleteEpicTaskById(id);
                fileBackedTasksManager.deleteSubTaskById(id);
            } else if (command == 7) {
                System.out.println("Введите id Эпик задачи, в которой хотите просмотреть все подзадачи: ");
                int id = scanner.nextInt();
                fileBackedTasksManager.getAllSubTaskInEpic(id);
            } else if (command == 8) {
                testTaskTraker.testEpicTask();
            } else if (command == 9) {
                System.out.println(fileBackedTasksManager.getHistoryManager().getHistory());
            }else if(command == 10) {
//                taskMap.clear();
//                epicTaskMap.clear();
//                subTaskMap.clear();
            fileBackedTasksManager.fromFile();
            }else if (command == 11) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды нет.");
            }

        }
    }
}


