package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class PrioritizedTasksEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Headers headers = exchange.getResponseHeaders();
        headers.add("application","json");
        Set set = fileBackedTasksManager.getPrioritizedTasks();

        String response = gson.toJson(set);

        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
