package maketbussinesslogic;

import model.Task;

import java.util.Collection;

public interface HistoryManager {

    //Добоыление задачи в историю
   void  add(Task task);

   //История последних 10 сообщений
     Collection getHistory();

     void clearHistory();
}
