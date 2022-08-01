package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class AllTasksEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");


    @Override
    public void handle(HttpExchange exchange)  throws IOException{

        fileBackedTasksManager.fromFile();
        Collection listTask = fileBackedTasksManager.getListTasks(fileBackedTasksManager.getTaskMap());
        Collection listEpicTask = fileBackedTasksManager.getListEpicTasks(fileBackedTasksManager.getEpicTaskMap());
        Collection listSubTask = fileBackedTasksManager.getListSubTasks(fileBackedTasksManager.getSubTaskMap());

        String task = gson.toJson(listTask);
        String epicTask = gson.toJson(listEpicTask);
        String subTask = gson.toJson(listSubTask);
        Collection allTasks = new ArrayList<>();
        allTasks.add(task);
        allTasks.add(epicTask);
        allTasks.add(subTask);
        String response = allTasks.toString();

        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
