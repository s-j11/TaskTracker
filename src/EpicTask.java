import java.util.ArrayList;


public class EpicTask extends Task{
        ArrayList<Integer> listSubtask = new ArrayList<>();

        void addSubTask(int subTask){
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
