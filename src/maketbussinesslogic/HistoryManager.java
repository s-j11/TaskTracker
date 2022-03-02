package maketbussinesslogic;

import model.Task;

import java.util.List;

public interface HistoryManager {

    //Добоыление задачи в историю
   void  add(Task task);

   //Удаление задачи
   void remove(int id);

   //История последних 10 сообщений
     List<Task> getHistory();

     //Очистка истории
     void clearHistory();
}
