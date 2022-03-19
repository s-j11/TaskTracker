package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Node;
import model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private  static List<Node> history = new ArrayList<>();
    private  static Map<Integer, Node> indexMap = new HashMap<Integer, Node>();
    private  static Node<Task> head;
    private  static Node<Task> tail;

    public Node<Task> linkLast(Task task){
         Node<Task> lastNode = tail;
         Node<Task> newNode = new Node( tail, task, null);
        if (lastNode == null){
            head = newNode;
            tail = newNode;
            newNode.prev = null;
            newNode.next = null;
            history.add(newNode);
        }else{
            lastNode.next = newNode;
            newNode.prev = lastNode;
            tail = newNode;
            history.add(newNode);
        }return newNode;
    }

    //Добоыление задачи в историю
    @Override
    public void add(Task task) {
        Node node = new Node(task);
        if (indexMap.isEmpty()) {
            node = linkLast(task);
            indexMap.put(task.getId(),node);
        }else if(!indexMap.containsKey(task.getId())){
            indexMap.put(task.getId(),linkLast(task));
        }else{
            remove(indexMap.get(task.getId()));
            indexMap.put(task.getId(),linkLast(task));
        }
    }

    //Удаление объекта
    @Override
    public Node<Task> remove(Node node) {
        node = indexMap.get(((Task) node.data).getId());
        if (node.prev == null && node.next == null) {
            history.remove(indexMap.get(((Task) node.data).getId()));
            indexMap.remove(((Task) node.data).getId());
        }else if (node.prev == null && node.next != null) {
                Node nextNode = node.next;
                nextNode.prev = null;
                head = nextNode;
                history.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } else if (node.prev != null && node.next != null) {
                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                history.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } else if (node.prev != null & node.next == null) {
                Node prevNode = node.prev;
                node.prev = node.prev.prev;
                prevNode.next = null;
                tail = prevNode;
                history.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } return node;
        }

    //История
    @Override
    public List<Task> getHistory() {
        List<Task> listHistory = new ArrayList<>();
        Node<Task> node = head;
        while ((node != null)){
            listHistory.add(node.data);
            node = node.next;
        } return listHistory;
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        history.clear();
    }
}


