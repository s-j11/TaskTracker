package interactive_menu;

import bussinesslogic.HTTPTaskServer;
import bussinesslogic.KVTaskClient;
import bussinesslogic.ManagersProvider;
import maketbussinesslogic.TaskManager;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ManagersProvider managersProvider = new ManagersProvider();
    private TaskManager httpTaskManager = managersProvider.getDefaultHTTPTaskManager("http://localhost:8078");
    private Map<Integer, Task> tasks = httpTaskManager.getTasks();
    private Map<Integer, EpicTask> epicTasks = httpTaskManager.getEpicTasks();
    private Map<Integer, SubTask> subTasks = httpTaskManager.getSubTasks();

    private HTTPTaskServer httpTaskServer = managersProvider.getDefaultHTTPTaskServer();

    private KVTaskClient kvTaskClient =new KVTaskClient();



    @Override
    public String toString() {
        return "{"
                + "taskMap=" + tasks
                + ", epicTaskMap=" + epicTasks
                + ", subTaskMap=" + subTasks + '}';
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
            System.out.println("8 - Вывести зададчи по времени выполнения");
            System.out.println("9 - Вывести историю последних 10 операций");
            System.out.println("10 - Загрузить данные из файла");
            System.out.println("11 - Запустить HTTP-server");
            System.out.println("12 - Остановить HTTP-server");
            System.out.println("13 - Загрузить данные из хранилища");
            System.out.println("14 - Выйти из приложения");


            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println("Список всех задач: ");
                System.out.println("Список задач: " + httpTaskManager.getTasksСatalogue(tasks));
                System.out.println("Список Эпик задач: " + httpTaskManager.getEpicTasksСatalogue(epicTasks));
                System.out.println("Список подзадач: " + httpTaskManager.getSubTasksСatalogue(subTasks));
                System.out.println();
            } else if (command == 2) {
                System.out.println("Введите номер списка задач, который хотите удалить:\n"
                        + "1 - Список задач\n"
                        + "2 - Список эпиков\n"
                        + "3 - Список подзадач");
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        httpTaskManager.deleteAllTasks();
                        System.out.println("Список задач удален");
                        break;
                    case 2:
                        httpTaskManager.deleteAllEpicTasks();
                        System.out.println("Список Эпик задач удален и связыанные с ними подзадачи");
                        break;
                    case 3:
                        httpTaskManager.deleteAllSubTasks();
                        System.out.println("Список подзадач удален");
                        break;
                    default:
                        System.out.println("Такого номера типа задачи нет введите от 1 до 3");
                }
            } else if (command == 3) {
                System.out.println("Введите id задачи, которую хотите найти: ");
                int id = scanner.nextInt();
                httpTaskManager.getTaskById(id);
                httpTaskManager.getEpicTaskById(id);
                httpTaskManager.getSubTaskById(id);
                System.out.println();
            } else if (command == 4) {
                System.out.println("Введите название задачи");
                String name = scanner.next();
                System.out.println("Введите описание задачи");
                String descriptions = scanner.next();
                System.out.println("Введите год");
                int year = scanner.nextInt();
                System.out.println("Введите месяц");
                int mouth = scanner.nextInt();
                System.out.println("Введите день");
                int day = scanner.nextInt();
                System.out.println("Введите час");
                int hour = scanner.nextInt();
                System.out.println("Введите миннуты");
                int minutes = scanner.nextInt();
                Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(year,mouth,day,hour,minutes));
                System.out.println("Введите продолжительность задачи в минутах");
                int duration = scanner.nextInt();
                System.out.println("Ведите тип задачи которую хотите завести:\n"
                        + "1 - Задача\n"
                        + "2 - Эпик\n"
                        + "3 - Подзадача");
                int button = scanner.nextInt();
                switch (button) {
                    case 1:
                        httpTaskManager.makeTask(name, descriptions, startTime, duration);
                        break;
                    case 2:
                        httpTaskManager.makeEpic(name, descriptions);
                        break;
                    case 3:
                        System.out.println("Введите номер эпик задачи");
                        int id = scanner.nextInt();
                        httpTaskManager.makeSubTask(name,descriptions,id,startTime,duration);
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
                System.out.println("Веедите дату начало задачи");
                System.out.println("Введите год");
                int year = scanner.nextInt();
                System.out.println("Введите месяц");
                int mouth = scanner.nextInt();
                System.out.println("Введите день");
                int day = scanner.nextInt();
                System.out.println("Введите час");
                int hour = scanner.nextInt();
                System.out.println("Введите миннуты");
                int minutes = scanner.nextInt();
                Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(year,mouth,day,hour,minutes));
                System.out.println("Введите новую продолжительность задачи в минутах");
                int duration = scanner.nextInt();
                status = status;
                Task task = new Task(name, descriptions, id, status,startTime,duration);
                SubTask subTask = new SubTask(name,descriptions,id,status,startTime,duration);
                EpicTask epicTask = new EpicTask(name, descriptions, id);
                httpTaskManager.updateTaskById(task);
                httpTaskManager.updateEpicTaskById(epicTask);
                httpTaskManager.updateSubTaskById(subTask);
            } else if (command == 6) {
                System.out.println("Введите id задачи, которую хотите удалить: ");
                int id = scanner.nextInt();
                httpTaskManager.deleteTaskById(id);
                httpTaskManager.deleteEpicTaskById(id);
                httpTaskManager.deleteSubTaskById(id);
            } else if (command == 7) {
                System.out.println("Введите id Эпик задачи, в которой хотите просмотреть все подзадачи: ");
                int id = scanner.nextInt();
                httpTaskManager.getAllSubTaskInEpic(id);
            }else if (command == 8){
                httpTaskManager.getPrioritizedTasks().stream().sorted(Comparator.reverseOrder())
                        .forEach(System.out::println);
            } else if (command == 9) {
                System.out.println(httpTaskManager.getHistoryManager().getHistory());
            }else if(command == 10) {
                tasks.clear();
                epicTasks.clear();
                subTasks.clear();
            httpTaskManager.fromFile();
            }else if(command == 11) {
                httpTaskServer.getStartServer();
            } else if(command == 12) {
                httpTaskServer.getStopServer();
            } else if(command == 13) {
                httpTaskManager.fromFile();
            } else if (command == 14) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды нет.");
            }
        }
    }
}


