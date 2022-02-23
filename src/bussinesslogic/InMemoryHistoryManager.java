package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private static ArrayList<Task> history = new ArrayList<>();

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
    public void getHistory() {
        int count = 1;
        for(Task task : history){
            System.out.println(count + " " + " "+ task);
            count++;
        }
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        history.clear();
    }


}
