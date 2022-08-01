package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;

public class Managers {
    String path;
    public static TaskManager getDefault(){
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedManager(String path) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(path);
        return fileBackedTasksManager;
    }

    public static HttpTaskServer getDefaultHttpTaskMangger(){
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        return httpTaskServer;
    }
}
