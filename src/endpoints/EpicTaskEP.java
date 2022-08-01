package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.EpicTask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class EpicTaskEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Collection listEpicTask = fileBackedTasksManager.getListEpicTasks(fileBackedTasksManager.getEpicTaskMap());
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        Headers headers = exchange.getResponseHeaders();
        EpicTask task;
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = null;
        headers.add("application","json");
        int id = 0;
        if (path.equals("/tasks/epic")) {
            switch (method) {
                case "GET":
                    String epicTask = gson.toJson(listEpicTask);
                    response = epicTask;
                    break;
                case "POST":
                    task = gson.fromJson(body, EpicTask.class);
                    fileBackedTasksManager.makeEpic(task.getName(),task.getDescription());
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteAllEpic();
                    break;
                default:
                    response = "Некорректный метод!";
            }
        }else{
            String idFromRequest = path.split("/")[3];
            id =Integer.parseInt(idFromRequest);
            switch (method) {
                case "GET":
                    task = fileBackedTasksManager.getEpicTaskMap().get(id);
                    String taskString = gson.toJson(task);
                    response = taskString;
                    break;
                case "POST":
                    task = gson.fromJson(body, EpicTask.class);
                    fileBackedTasksManager.updateEpicTaskById(task);
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteEpicTaskById(id);
                    break;
                default:
                    response = "Некорректный метод!";
            }
            response = response;

        }
        response = response;



        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
