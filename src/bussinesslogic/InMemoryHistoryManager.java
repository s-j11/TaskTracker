package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private static LinkedList<Task> history = new LinkedList<>();

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

    //История последних 10 сообщений
    @Override
    public Collection getHistory() {
        Collection listHistory = new ArrayList<>();
        int count = 1;
        for (Task task : history) {
            listHistory.add(count + " " + " " + task);
            count++;
        }return listHistory;
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        history.clear();
    }


}
