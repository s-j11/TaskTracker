package endpoints;

import bussinesslogic.FileBackedTasksManager;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class AllSubTaskInEpicEP implements HttpHandler {

    Gson gson = new Gson();
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        fileBackedTasksManager.fromFile();
        Headers headers = exchange.getResponseHeaders();
        String path = exchange.getRequestURI().getPath();
        String response = null;
        headers.add("application","json");
        int id = 0;
        String idFromRequest = path.split("/")[3];
        id =Integer.parseInt(idFromRequest);

        if(fileBackedTasksManager.getEpicTaskMap().containsKey(id)) {

            Collection list = fileBackedTasksManager.getEpicTaskMap().get(id).getListSubtask();
            response = gson.toJson(list);
            exchange.sendResponseHeaders(200, 0);
        }else{
            exchange.sendResponseHeaders(404, 0);
            response =gson.toJson("Ошибка 404");
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
