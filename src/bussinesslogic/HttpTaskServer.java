package bussinesslogic;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import endpoints.AllTasksEP;
import endpoints.EpicTaskEP;
import endpoints.SubTaskEP;
import endpoints.TaskEP;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    Gson gson = new Gson();
    private FileBackedTasksManager fileBackedTasksManager = new Managers()
            .getDefaultFileBackedManager("src/store/store2.csv");

    HttpServer httpServer;

    public void getStartServer(){


            try {
                httpServer = HttpServer.create();
                httpServer.bind(new InetSocketAddress(PORT), 0);
                httpServer.createContext("/tasks", new AllTasksEP());
                httpServer.createContext("/tasks/task", new TaskEP());
                httpServer.createContext("/tasks/epic", new EpicTaskEP());
                httpServer.createContext("/tasks/subtask", new SubTaskEP());
                httpServer.start();
                System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

            } catch (IOException  e) {
                throw new RuntimeException(e);
            }
        }

        public void getStopServer(){
            System.out.println("HTTP-сервер будет остановлен на " + PORT + " порту, через 3 секунд");
        httpServer.stop(3);

        }
    }

