package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Node;
import model.Task;
import java.util.*;

    public class InMemoryHistoryManager implements HistoryManager {
    private  List<Node> historyList = new ArrayList<>();
    private  Map<Integer, Node> indexMap = new HashMap<Integer, Node>();
    private  Node<Task> head = null;
    private  Node<Task> tail = null;

        public Map<Integer, Node> getIndexMap() {
            return indexMap;
        }

        public Node<Task> linkLast(Task task){
         Node<Task> lastNode = tail;
         Node<Task> newNode = new Node( tail, task, null);
        if (lastNode == null){
            head = newNode;
            tail = newNode;
            newNode.prev = null;
            newNode.next = null;
            historyList.add(newNode);
        }else{
            lastNode.next = newNode;
            newNode.prev = lastNode;
            tail = newNode;
            historyList.add(newNode);
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
            historyList.remove(indexMap.get(((Task) node.data).getId()));
            indexMap.remove(((Task) node.data).getId());
            head = null;
        }else if (node.prev == null && node.next != null) {
                Node nextNode = node.next;
                nextNode.prev = null;
                head = nextNode;
                historyList.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } else if (node.prev != null && node.next != null) {
                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                historyList.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } else if (node.prev != null & node.next == null) {
                Node prevNode = node.prev;
                node.prev = node.prev.prev;
                prevNode.next = null;
                tail = prevNode;
                historyList.remove(indexMap.get(((Task) node.data).getId()));
                indexMap.remove(((Task) node.data).getId());
            } return node;
        }

    //История
    @Override
    public List<Task> getHistory() {
        List<Task> listHistory = new ArrayList<>();
        Node<Task> node = this.head;
        while (head != null){
            listHistory.add(node.data);
            if(node.next != null) {
                node = node.next;
            }else break;
        } return listHistory;
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        historyList.clear();
    }


    @Override
    public void removeAllNode(){
       Collection<Node> list = this.historyList;
       for (int i =0; i < historyList.size(); i++){
        Node node = historyList.get(i);
        remove(node);
        list.remove(node);
        i--;
       }

        }
    }



