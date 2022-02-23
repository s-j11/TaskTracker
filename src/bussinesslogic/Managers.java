package bussinesslogic;

import maketbussinesslogic.TaskManager;

public class Managers {
    public static TaskManager getDefault(){
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }
}
