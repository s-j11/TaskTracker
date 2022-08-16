package test;

import bussinesslogic.ManagersProvider;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

class EpicTaskTest {
    private ManagersProvider managersProvider = new ManagersProvider();
    private TaskManager inFileBackedTaksManager = managersProvider.getDefaultFileBackedManager("src/store/test.csv");
    @BeforeEach
    public void shouldPreparedTestEnvironment(){
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2022,1,1,15,
                30));
        Optional<LocalDateTime> startTime1 =Optional.of(LocalDateTime.of(2022,1,5,15,
                30));
        Optional<LocalDateTime> startTime2 = Optional.of(LocalDateTime.of(2022,1,10,15,
                30));
        EpicTask epicTask = inFileBackedTaksManager.makeEpic("Тестирование", "Разработка тестирования");
        SubTask subTask = inFileBackedTaksManager.makeSubTask("Разработка меню",
                "Разработка класса меню",1, startTime,30);
        SubTask subTask1 = inFileBackedTaksManager.makeSubTask("Разработка логики",
                "Разработка класса логики", 1, startTime1,30);
        SubTask subTask2 = inFileBackedTaksManager.makeSubTask("Класс тестирования",
                "Разработка класа тестирования", 1,startTime2,30);
    }
    @Test
    public void shouldThrowEmptyListSubTask(){
       inFileBackedTaksManager.deleteAllSubTasks();
       EpicTask eT = inFileBackedTaksManager.getEpicTasks().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не NEW");
    }
    @Test
    public void shouldThrowAllNewSubTasks(){
        EpicTask eT = inFileBackedTaksManager.getEpicTasks().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не NEW");
    }
    @Test
    public void shouldThrowAllDoneSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTasks().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTasks().get(3);
        SubTask subTask2 = inFileBackedTaksManager.getSubTasks().get(4);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        inFileBackedTaksManager.updateSubTaskById(subTask2);
        EpicTask eT = inFileBackedTaksManager.getEpicTasks().get(1);
        Assertions.assertEquals(Status.DONE, eT.getStatus(), "Статус не DONE");
    }
    @Test
    public void shouldThrowNewAndDoneSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTasks().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTasks().get(3);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        EpicTask eT = inFileBackedTaksManager.getEpicTasks().get(1);
        Assertions.assertEquals(Status.NEW, eT.getStatus(), "Статус не DONE");
    }
    @Test
    public void shouldThrowAllInProgressSubTasks(){
        SubTask subTask = inFileBackedTaksManager.getSubTasks().get(2);
        SubTask subTask1 = inFileBackedTaksManager.getSubTasks().get(3);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.DONE);
        inFileBackedTaksManager.updateSubTaskById(subTask);
        inFileBackedTaksManager.updateSubTaskById(subTask1);
        EpicTask eT = inFileBackedTaksManager.getEpicTasks().get(1);
        Assertions.assertEquals(Status.IN_PROGRESS, eT.getStatus(), "Статус не IN_PROGRESS");
    }
}