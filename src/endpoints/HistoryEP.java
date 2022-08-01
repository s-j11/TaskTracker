package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class HistoryEP implements HttpHandler {
    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Headers headers = exchange.getResponseHeaders();
        headers.add("application","json");
        Collection list = fileBackedTasksManager.getHistoryManager().getHistory();

       String response = gson.toJson(list);

        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }
}
