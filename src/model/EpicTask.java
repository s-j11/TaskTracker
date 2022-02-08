package model;

import java.util.ArrayList;


public class EpicTask extends Task{
        private ArrayList<Integer> listSubtask = new ArrayList<>();

        public void addSubTask(int subTask){
                listSubtask.add(subTask);
        }

        public ArrayList<Integer> getListSubtask() {
                return listSubtask;
        }

        public void setListSubtask(ArrayList<Integer> listSubtask) {
                this.listSubtask = listSubtask;
        }

        public EpicTask() {
        }

        public EpicTask(String name, String description) {
                super(name, description);

        }

        public EpicTask(String name, String description, int id, String status, ArrayList<Integer> listSubtask) {
                super(name, description, id, status);
                this.listSubtask = listSubtask;
        }

        public EpicTask(String name, String description, int id) {
                super(name, description, id);
        }

        public String toString() {
                String result = "{name=' " + getName() + '\'';
                if (getDescription() != null) {
                        result = result + ", description= " + getDescription().length() + '\'';
                } else {
                        result = result + ", description= null ";
                }
                result = result + ", id= " + getId() + ", ListSubTaskNumber= "
                        + listSubtask + ", status= '" + getStatus() + '\'' + '}';
                return result;
        }

}