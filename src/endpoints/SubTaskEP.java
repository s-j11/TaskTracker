package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class SubTaskEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Collection listSubTask = fileBackedTasksManager.getListSubTasks(fileBackedTasksManager.getSubTaskMap());
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        Headers headers = exchange.getResponseHeaders();
        SubTask task;
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = null;
        headers.add("application","json");
        int id = 0;

        if (path.equals("/tasks/subtask")) {
            switch (method) {
                case "GET":
                    String subTask = gson.toJson(listSubTask);
                    response = subTask;
                    break;
                case "POST":
                    task = gson.fromJson(body, SubTask.class);
                    fileBackedTasksManager.makeSubTask(task.getName(),task.getDescription(),task.getEpicTaskNumber(),
                            task.getStartTime(), task.getDuration());
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteAllSubTask();
                    break;
                default:
                    response = "Некорректный метод!";
            }
        }else{
            String idFromRequest = path.split("/")[3];
            id =Integer.parseInt(idFromRequest);
            switch (method) {
                case "GET":
                    //headers.add("application","json");
                    task = fileBackedTasksManager.getSubTaskMap().get(id);
                    String taskString = gson.toJson(task);
                    response = taskString;
                    break;
                case "POST":
                    task = gson.fromJson(body, SubTask.class);
                    fileBackedTasksManager.updateTaskById(task);
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteSubTaskById(id);
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