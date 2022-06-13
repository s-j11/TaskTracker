package test;

import bussinesslogic.Managers;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

class EpicTaskTest {
    private Managers managers = new Managers();
    private TaskManager inFileBackedTaksManager = managers.getDefaultFileBackedManager("src/store/test.csv");

    @BeforeEach
    public void shouldPreparedTestEnvironment(){
        EpicTask epicTask = inFileBackedTaksManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inFileBackedTaksManager.makeSubTask("Разработка меню",
                "Разработка класса меню", 1);
        SubTask subTask1 = inFileBackedTaksManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 1);
        SubTask subTask2 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);
        SubTask subTask3 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);
        SubTask subTask4 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);
        SubTask subTask5 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1);

        subTask.setStatus(Status.DONE);
        subTask2.setStatus(Status.IN_PROGRESS);
        subTask3.setStatus(Status.DONE);
    }

    @Test
    public void shouldThrowEmptyListSubTask(){
        EpicTask epicTask = new EpicTask();
        Collection list = new ArrayList<>();
        Assertions.assertEquals(list, epicTask.getListSubtask(), "Список подзадач не пуст");
    }

    @Test
    public void shouldThrowAllNewSubTasks(){
        List<SubTask> listSubtasks = inFileBackedTaksManager.getListSubTasks(inFileBackedTaksManager.getSubTaskMap());
        List<SubTask> listSubtasksNew = listSubtasks.stream()
                .filter(x -> x.getStatus().equals(Status.NEW))
                .collect(Collectors.toList());

        Assertions.assertEquals(3, listSubtasksNew.size(), "Колличество SubTask не соответвует заданным");
    }

    @Test
    public void shouldThrowAllDoneSubTasks(){
        List<SubTask> listSubtasks = inFileBackedTaksManager.getListSubTasks(inFileBackedTaksManager.getSubTaskMap());
        List<SubTask> listSubtasksNew = listSubtasks.stream()
                .filter(x -> x.getStatus().equals(Status.DONE))
                .collect(Collectors.toList());

        Assertions.assertEquals(2, listSubtasksNew.size(),
                "Колличество SubTask не соответвует заданным");
    }

    @Test
    public void shouldThrowAllNewAndDoneSubTasks(){
        List<SubTask> listSubtasks = inFileBackedTaksManager.getListSubTasks(inFileBackedTaksManager.getSubTaskMap());
        List<SubTask> listSubtasksNew = listSubtasks.stream()
                .filter(x -> x.getStatus().equals(Status.NEW)|| x.getStatus().equals(Status.DONE))
                .collect(Collectors.toList());

        Assertions.assertEquals(5, listSubtasksNew.size(),
                "Колличество SubTask не соответвует заданным");
    }

    @Test
    public void shouldThrowAllInProgressSubTasks(){
        List<SubTask> listSubtasks = inFileBackedTaksManager.getListSubTasks(inFileBackedTaksManager.getSubTaskMap());
        List<SubTask> listSubtasksNew = listSubtasks.stream()
                .filter(x -> x.getStatus().equals(Status.IN_PROGRESS))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, listSubtasksNew.size(),
                "Колличество SubTask не соответвует заданным");
    }
}