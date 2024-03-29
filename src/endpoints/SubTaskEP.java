package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public class SubTaskEP implements HttpHandler {
    private final Gson gson = new Gson();
    private final FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Collection subTaskСatalogue = fileBackedTasksManager.getSubTasksСatalogue(fileBackedTasksManager.getSubTasks());
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        Headers headers = exchange.getResponseHeaders();
        SubTask task;
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = null;
        headers.add("application", "json");
        int id = 0;

        if (path.equals("/tasks/subtask")) {
            switch (method) {
                case "GET":
                    String subTask = gson.toJson(subTaskСatalogue);
                    response = subTask;
                    break;
                case "POST":
                    task = gson.fromJson(body, SubTask.class);
                    fileBackedTasksManager.makeSubTask(task.getName(), task.getDescription(), task.getEpicTaskNumber(),
                            task.getStartTime(), task.getDuration());
                    break;
                case "DELETE":
                    fileBackedTasksManager.deleteAllSubTasks();
                    break;
                default:
                    response = "Некорректный метод!";
            }
            exchange.sendResponseHeaders(200, 0);
            response = response;
        } else {
            String idFromRequest = path.split("/")[3];
            id = Integer.parseInt(idFromRequest);
            if (fileBackedTasksManager.getSubTasks().containsKey(id)) {
                switch (method) {
                    case "GET":
                        fileBackedTasksManager.getSubTaskById(id);
                        task = fileBackedTasksManager.getSubTasks().get(id);
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
                exchange.sendResponseHeaders(200, 0);
            } else {
                exchange.sendResponseHeaders(404, 0);
                response = gson.toJson("Ошибка 404");
            }
            response = response;
        }
        response = response;
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}