import java.util.HashMap;

public class TestTaskTraker {



    Manager manager = new Manager();


    public TestTaskTraker() {

    }

    public void testEpicTask (){
        HashMap<Integer,EpicTask> epicTaskMap = manager.epicTaskMap;
        HashMap<Integer,SubTask> subTaskMap= manager.subTaskMap;
        manager.maikingEpic("Work", "Working");
        manager.maikingEpic("Work2", "Working2");
        manager.maikingSubTask("Work3","Working3",1);
        manager.maikingSubTask("Work4","Working4",1);
        manager.maikingSubTask("Work5","Working5",2);
        manager.toString();

    }

}
