package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static ArrayList<Task> history = new ArrayList<Task>();

    void linkLast(){

    }
    Task getTask(){
        Task task  = new Task();
        return task;
    }



    //Добоыление задачи в историю
    @Override
    public void add(Task task) {
        if (history.isEmpty()) {
            history.add(task);
        }else if(history.size() < 10){
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public void remove(int id) {

    }

    //История последних 10 сообщений
    @Override
    public List<Task> getHistory() {
        List<Task> listHistory = new ArrayList<>();
        int count = 1;
        for (Task task : history) {
            listHistory.add(task);
            count++;
        }return listHistory;
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        history.clear();
    }


}
