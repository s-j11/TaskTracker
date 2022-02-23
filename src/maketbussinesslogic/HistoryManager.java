package maketbussinesslogic;

import model.Task;

public interface HistoryManager {

    //Добоыление задачи в историю
   void  add(Task task);

   //История последних 10 сообщений
    void getHistory();

    public void clearHistory();
}
