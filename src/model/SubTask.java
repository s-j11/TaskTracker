package model;

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

    public SubTask(String name, String description, int id, String status) {
        super(name, description, id, status);
    }

    public SubTask(String name, String description, int id, String status, int epicTaskNumber) {
        super(name, description, id, status);
        this.epicTaskNumber = epicTaskNumber;
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