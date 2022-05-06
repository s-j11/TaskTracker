package maketbussinesslogic;

import model.Node;
import model.Task;

import java.util.List;

public interface HistoryManager {

    //Добоыление задачи в историю
   void  add(Task task);

   //Удаление задачи
   Node<Task> remove(Node node);

   //История последних сообщений
     List<Task> getHistory();

     //Очистка истории
     void clearHistory();

     //Удаление всей цепочки node
    void removeAllNode();
}
