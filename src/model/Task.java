package model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task implements Comparable<Task>{
   private String name;
    private String description;
    private int id;
    private Status status;

    private int duration;

    private Optional<LocalDateTime> startTime = Optional.empty();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Optional<LocalDateTime> getStartTime() {
        return startTime;
    }

    public void setStartTime(Optional<LocalDateTime> startTime) {
        this.startTime = startTime;
    }

    public Optional<LocalDateTime> getEndTime(){
        LocalDateTime endTime = startTime.get().plusMinutes(duration);
        return Optional.of(endTime);
    }
    public Task() {
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(String name, String description, int id, Status status, Optional<LocalDateTime> startTime, int duration)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        String result = "{name= " + name;
                 if(description != null) {
                     result = result + ", description= " + description.length();
                 }else{
                     result = result + ", description= null ";
                 }
                result = result + ", id= " + id  + ", status= " + status + ", start time= " + startTime.get()
                        + ", end time= " + getEndTime().get() + ", duration= " + duration + '}';
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public int compareTo(Task o) {
        int i = 0;
        if (this.getStartTime().get().isBefore(o.getStartTime().get())){
            i = 1;
        }else if (this.getStartTime().get().isAfter(o.getStartTime().get())){
            i = -1;
        }else if(this.getStartTime().get().isEqual(o.getStartTime().get())){
            i = 0;
        }
        return i;
    }
}
