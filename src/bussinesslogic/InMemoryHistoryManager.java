package bussinesslogic;

import maketbussinesslogic.HistoryManager;
import model.Node;
import model.Task;
import java.util.*;

    public class InMemoryHistoryManager implements HistoryManager {
    private  List<Node> history = new ArrayList<>();
    private  Map<Integer, Node> index = new HashMap<Integer, Node>();
    private  Node<Task> head = null;
    private  Node<Task> tail = null;
        public Map<Integer, Node> getIndex() {
            return index;
        }
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
        if (index.isEmpty()) {
            node = linkLast(task);
            index.put(task.getId(),node);
        }else if(!index.containsKey(task.getId())){
            index.put(task.getId(),linkLast(task));
        }else{
            remove(index.get(task.getId()));
            index.put(task.getId(),linkLast(task));
        }
    }

    //Удаление объекта
    @Override
    public Node<Task> remove(Node node) {
        node = index.get(((Task) node.data).getId());
        if (node.prev == null && node.next == null) {
            history.remove(index.get(((Task) node.data).getId()));
            index.remove(((Task) node.data).getId());
            head = null;
        }else if (node.prev == null && node.next != null) {
                Node nextNode = node.next;
                nextNode.prev = null;
                head = nextNode;
                history.remove(index.get(((Task) node.data).getId()));
                index.remove(((Task) node.data).getId());
            } else if (node.prev != null && node.next != null) {
                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                history.remove(index.get(((Task) node.data).getId()));
                index.remove(((Task) node.data).getId());
            } else if (node.prev != null & node.next == null) {
                Node prevNode = node.prev;
                node.prev = node.prev.prev;
                prevNode.next = null;
                tail = prevNode;
                history.remove(index.get(((Task) node.data).getId()));
                index.remove(((Task) node.data).getId());
            } return node;
        }

    //История
    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> node = this.head;
        while (head != null){
            history.add(node.data);
            if(node.next != null) {
                node = node.next;
            }else break;
        } return history;
    }

    //Очистка списка истории.
    @Override
    public void clearHistory() {
        history.clear();
    }
    @Override
    public void removeAllNode(){
       Collection<Node> historyToRemove = this.history;
       for (int i = 0; i < history.size(); i++){
        Node node = history.get(i);
        remove(node);
        historyToRemove.remove(node);
        i--;
       }
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InMemoryHistoryManager that = (InMemoryHistoryManager) o;
            return Objects.equals(history, that.history) && Objects.equals(index, that.index)
                    && Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
        }
        @Override
        public int hashCode() {
            return Objects.hash(history, index, head, tail);
        }
    }



