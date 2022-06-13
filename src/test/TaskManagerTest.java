package test;

import bussinesslogic.Managers;
import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest<T extends TaskManager> {

    private Managers managers = new Managers();
    private TaskManager inFileBackedTaksManager = managers.getDefaultFileBackedManager("src/store/test.csv");
    private HistoryManager historyManager = inFileBackedTaksManager.getHistoryManager();


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
    public void shouldExistEpicTask(){
    SubTask sT = inFileBackedTaksManager.getSubTaskMap().get(3);
        Assertions.assertEquals(1, sT.getEpicTaskNumber());
    }

    @Test
    public void shouldCountStatusEpicTask(){

    }

}