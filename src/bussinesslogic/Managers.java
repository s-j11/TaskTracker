package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import maketbussinesslogic.TaskManager;

public class Managers {
    public static TaskManager getDefault(){
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedManager() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("./src/store/store.csv");
        return fileBackedTasksManager;
    }
}
