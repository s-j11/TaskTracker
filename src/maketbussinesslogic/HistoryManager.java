package maketbussinesslogic;

import model.Node;
import model.Task;

import java.util.List;
import java.util.Map;

public interface HistoryManager {

    //Получение последовательности возникновения node
    //List<Node> getHistoryList();

    //Передача данных в список
   //void setHistoryList( List<Node> list);

    //Получение Map связи node и порядка следования
    //Map<Integer,Node> getIndexMap();

    //Передача данных в Map связи node и порядка следования
    //void  setIndexMap(Map<Integer,Node> map);

    //Получение head связанного списка
    //Node<Task> getHead();

    //Передача данных в head связанного списка
    //void setHead(Node<Task> head);

    //Получение tail связанного списка
    //Node<Task> getTail();

    //Передача данных в tail связанного списка
    //void setTail(Node<Task> tail);

    //Добоыление задачи в историю
   void  add(Task task);

   //Удаление задачи
   Node<Task> remove(Node node);

   //История последних сообщений
     List<Task> getHistory();

     //Очистка истории
     void clearHistory();
}
