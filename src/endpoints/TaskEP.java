package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public class TaskEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Collection listTask = fileBackedTasksManager.getListTasks(fileBackedTasksManager.getTaskMap());
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        Headers headers = exchange.getResponseHeaders();
        Task task;
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = null;
        headers.add("application","json");
        int id = 0;

        if (path.equals("/tasks/task")) {
            switch (method) {
                case "GET":
                    String allTasks = gson.toJson(listTask);
                     response = allTasks;
                    break;
                case "POST":
                    task = gson.fromJson(body, Task.class);
                    fileBackedTasksManager.makeTask(task.getName(),task.getDescription(),task.getStartTime(),
                            task.getDuration());
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteAllTask();
                    break;
                default:
                    response = "Некорректный метод!";
            }
            exchange.sendResponseHeaders(200, 0);
            response = response;
        }else {
            String idFromRequest = path.split("/")[3];
            id = Integer.parseInt(idFromRequest);
            if (fileBackedTasksManager.getTaskMap().containsKey(id)) {
                switch (method) {
                    case "GET":
                        fileBackedTasksManager.getTaskById(id);
                        task = fileBackedTasksManager.getTaskMap().get(id);
                        String taskString = gson.toJson(task);
                        response = taskString;
                        break;
                    case "POST":
                        task = gson.fromJson(body, Task.class);
                        fileBackedTasksManager.updateTaskById(task);
                        break;
                    case "DELETE":
                        fileBackedTasksManager.deleteTaskById(id);
                        break;
                    default:
                        response = "Некорректный метод!";
                }
                response = response;
                exchange.sendResponseHeaders(200, 0);
            } else {
                exchange.sendResponseHeaders(404, 0);
                response = gson.toJson("Ошибка 404");
            }
            response = response;
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
}
}
