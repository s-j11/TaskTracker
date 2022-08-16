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
    private final Gson gson = new Gson();
    private final FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange)  throws IOException{

        fileBackedTasksManager.fromFile();
        Collection tasks = fileBackedTasksManager.getTasksСatalogue(fileBackedTasksManager.getTasks());
        Collection epicTasks = fileBackedTasksManager.getEpicTasksСatalogue(fileBackedTasksManager.getEpicTasks());
        Collection subTasks = fileBackedTasksManager.getSubTasksСatalogue(fileBackedTasksManager.getSubTasks());

        String task = gson.toJson(tasks);
        String epicTask = gson.toJson(epicTasks);
        String subTask = gson.toJson(subTasks);
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
