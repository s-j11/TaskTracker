package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends  Task {
    int epicTaskNumber;

    public int getEpicTaskNumber() {
        return epicTaskNumber;
    }

    public void setEpicTaskNumber(int epicTaskNumber) {
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask() {
    }

    public SubTask(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public SubTask(String name, String description, int id, Status status, int epicTaskNumber) {
        super(name, description, id, status);
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask(String name, String description, int id, Status status, LocalDateTime startTime, int duration) {
        super(name, description, id, status, startTime, duration);
    }

    public SubTask(String name, String description, int id, Status status, LocalDateTime startTime, int duration,
                   int epicTaskNumber) {
        super(name, description, id, status, startTime,duration);
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask(int epicTaskNumber) {
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask(String name, String description, int epicTaskNumber) {
        super(name, description);
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask(String name, String description, Status status, int epicTaskNumber) {
        super(name, description, status);
        this.epicTaskNumber = epicTaskNumber;
    }

    public SubTask(String name, String description, int id, int epicTaskNumber) {
        super(name, description, id);
        this.epicTaskNumber = epicTaskNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicTaskNumber == subTask.epicTaskNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicTaskNumber);
    }

    public String toString() {
        String result = "{name=' " + getName() + '\'';
        if (getDescription() != null) {
            result = result + ", description= " + getDescription().length() + '\'';
        } else {
            result = result + ", description= null ";
        }
        result = result + ", id= " + getId() + ", " + "epic task number= "
                + epicTaskNumber + ", status= '" + getStatus() + '\'' + '}';
        return result;
    }

}