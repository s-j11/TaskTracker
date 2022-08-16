package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EpicTask extends Task{
        private List<Integer> subTaskСatalogue = new ArrayList<>();

        private Optional<LocalDateTime> endTime = Optional.empty();

        public void addSubTask(int subTask){
                subTaskСatalogue.add(subTask);
        }

        public List<Integer> getSubtaskCatalogue() {
                return subTaskСatalogue;
        }

        public void setSubTaskСatalogue(List<Integer> subTaskСatalogue) {
                this.subTaskСatalogue = subTaskСatalogue;
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
                return Objects.equals(subTaskСatalogue, epicTask.subTaskСatalogue);
        }

        @Override
        public int hashCode() {
                return Objects.hash(super.hashCode(), subTaskСatalogue);
        }

        public EpicTask() {
        }

        public EpicTask(String name, String description) {
                super(name, description);

        }

        public EpicTask(String name, String description, int id, Status status, List<Integer> subTaskСatalogue) {
                super(name, description, id, status);
                this.subTaskСatalogue = subTaskСatalogue;
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration) {
                super(name, description, id, status, startTime, duration);
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration, List<Integer> subTaskСatalogue) {
                super(name, description, id, status, startTime, duration);
                this.subTaskСatalogue = subTaskСatalogue;
        }

        public EpicTask(String name, String description, int id, Status status, Optional<LocalDateTime> startTime,
                        int duration, List<Integer> subTaskСatalogue, Optional<LocalDateTime> endTime) {
                super(name, description, id, status, startTime, duration);
                this.subTaskСatalogue = subTaskСatalogue;
                this.endTime = endTime;
        }

        public EpicTask(String name, String description, int id) {
                super(name, description, id);
        }

        @Override
        public String toString() {
                String result = "{name= " + getName();
                if (getDescription() != null) {
                        result = result + ", description= " + getDescription().length();
                } else {
                        result = result + ", description= null ";
                }
                result = result + ", id= " + getId() + ", ListSubTaskNumber= "
                        + subTaskСatalogue + ", status= " + getStatus();
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
