package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EpicTask extends Task{
        private List<Integer> listSubtask = new ArrayList<>();

        private Optional<LocalDateTime> endTime = Optional.empty();


        public void addSubTask(int subTask){
                listSubtask.add(subTask);
        }

        public List<Integer> getListSubtask() {
                return listSubtask;
        }

        public void setListSubtask(List<Integer> listSubtask) {
                this.listSubtask = listSubtask;
        }


        @Override
        public Optional<LocalDateTime> getEndTime() {
                return endTime;
        }

        public void setEndTime(Optional<LocalDateTime> endTime) {
                this.endTime = endTime;
        }


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                if (!super.equals(o)) return false;
                EpicTask epicTask = (EpicTask) o;
                return Objects.equals(listSubtask, epicTask.listSubtask);
        }

        @Override
        public int hashCode() {
                return Objects.hash(super.hashCode(), listSubtask);
        }

        public EpicTask() {
        }

        public EpicTask(String name, String description) {
                super(name, description);

        }

        public EpicTask(String name, String description, int id, Status status, List<Integer> listSubtask) {
                super(name, description, id, status);
                this.listSubtask = listSubtask;
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration) {
                super(name, description, id, status, startTime, duration);
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration, List<Integer> listSubtask) {
                super(name, description, id, status, startTime, duration);
                this.listSubtask = listSubtask;
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration, List<Integer> listSubtask, Optional<LocalDateTime> endTime) {
                super(name, description, id, status, startTime, duration);
                this.listSubtask = listSubtask;
                this.endTime = endTime;
        }

        public EpicTask(String name, String description, int id) {
                super(name, description, id);
        }

        public String toString() {
                String result = "{name= " + getName();
                if (getDescription() != null) {
                        result = result + ", description= " + getDescription().length();
                } else {
                        result = result + ", description= null ";
                }
                result = result + ", id= " + getId() + ", ListSubTaskNumber= "
                        + listSubtask + ", status= " + getStatus();
                if(getStartTime().isEmpty()) {
                        result = result +", start time = Нет данных";
                }else {
                        result = result +   ", start time= " + getStartTime().get();
                }
                 if(getEndTime().isEmpty()) {
                         result = result + ", end time= Нет данных";
                 }else {
                        result = result  + ", end time= " + endTime.get();
                }
                 result = result + ", duration= " + getDuration()  + '}';
                return result;
        }

}
