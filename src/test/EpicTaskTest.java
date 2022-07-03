package test;

import bussinesslogic.Managers;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    }
    @Test
    public void shouldThrowEmptyListSubTask(){
       inFileBackedTaksManager.deleteAllSubTask();
       EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не NEW");
    }
    @Test
    public void shouldThrowAllNewSubTasks(){
        EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не NEW");
    }
    @Test
    public void shouldThrowAllDoneSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTaskMap().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTaskMap().get(3);
        SubTask subTask2 = inFileBackedTaksManager.getSubTaskMap().get(4);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        inFileBackedTaksManager.updateSubTaskById(subTask2);
        EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(1);
        Assertions.assertEquals(Status.DONE, eT.getStatus(), "Статус не DONE");
    }
    @Test
    public void shouldThrowNewAndDoneSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTaskMap().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTaskMap().get(3);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не DONE");
    }
    @Test
    public void shouldThrowAllInProgressSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTaskMap().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTaskMap().get(3);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        EpicTask eT = inFileBackedTaksManager.getEpicTaskMap().get(1);
        Assertions.assertEquals(Status.IN_PROGRESS, eT.getStatus(), "Статус не IN_PROGRESS");
    }
}