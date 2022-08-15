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

    public static HTTPTaskServer getDefaultHTTPTaskServer(){
        HTTPTaskServer httpTaskServer = new HTTPTaskServer();
        return httpTaskServer;
    }
    public static HTTPTaskManager getDefaultHTTPTaskManager(String path){
        HTTPTaskManager httpTaskManager = new HTTPTaskManager(path);
        return httpTaskManager;
    }

}
