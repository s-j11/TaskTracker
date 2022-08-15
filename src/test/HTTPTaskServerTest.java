package test;

import bussinesslogic.FileBackedTasksManager;
import bussinesslogic.HTTPTaskServer;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Перед каждым тестированием необходимо восстановить файл store2.csv из папки Old, инаце идет измененеие данных
// в результате преобразований. Тесты запускаются последовательно.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HTTPTaskServerTest {
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("src/store/store2.csv");
    HTTPTaskServer httpTaskServer = new HTTPTaskServer();
    HttpClient client = HttpClient.newHttpClient();
    private URI uri = URI.create("http://localhost:8080");
    
    HttpRequest.BodyPublisher body;

    @BeforeEach
    public void startHTTPTaskServer(){
        httpTaskServer.getStartServer();
    }
    @AfterEach
    public void stopHTTPTaskServer(){
        httpTaskServer.getStopServer();
    }

    @Order(1)
    @Test
    public void shouldGetAllTasks() {
        String result = null;
        uri = URI.create(uri + "/tasks");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[[{\"name\":\"П\",\"description\":\"П\",\"id\":1,\"status\":\"NEW\"," +
                "\"duration\":30,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":1,\"minute\":15,\"second\":0,\"nano\":0}}}}]," +
                " [{\"listSubtask\":[3,4],\"endTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":3,\"minute\":5,\"second\":0,\"nano\":0}}},\"name\":\"Р\",\"description\":\"Р\"" +
                ",\"id\":2,\"status\":\"NEW\",\"duration\":25,\"startTime\":{\"value\":{\"date\":{\"year\":1," +
                "\"month\":1,\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}}]," +
                " [{\"epicTaskNumber\":2,\"name\":\"Т\",\"description\":\"Т\",\"id\":3,\"status\":\"NEW\"," +
                "\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}},{\"epicTaskNumber\":2," +
                "\"name\":\"Т2\",\"description\":\"Т2\",\"id\":4,\"status\":\"NEW\",\"duration\":15," +
                "\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2," +
                "\"minute\":50,\"second\":0,\"nano\":0}}}}]]", result);
    }
    @Order(2)
    @Test
    public void shouldGetTaskprioritized() {
        String result = null;
        uri = URI.create(uri + "/tasks/prioritized");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[{\"epicTaskNumber\":2,\"name\":\"Т2\",\"description\":\"Т2\",\"id\":4," +
                "\"status\":\"NEW\",\"duration\":15,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":2,\"minute\":50,\"second\":0,\"nano\":0}}}},{\"epicTaskNumber\":2," +
                "\"name\":\"Т\",\"description\":\"Т\",\"id\":3,\"status\":\"NEW\",\"duration\":10,\"startTime\":" +
                "{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2,\"minute\":30," +
                "\"second\":0,\"nano\":0}}}},{\"name\":\"П\",\"description\":\"П\",\"id\":1,\"status\":\"NEW\"," +
                "\"duration\":30,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":" +
                "{\"hour\":1,\"minute\":15,\"second\":0,\"nano\":0}}}}]", result);
    }
    @Order(3)
    @Test
    public void shouldGetSubTaskInEpic() {
        String result = null;
        uri = URI.create(uri + "/tasks/astie/2");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[3,4]", result);
    }

    @Order(10)
    @Test
    public void shouldGetHistory() {
        String result = null;
        uri = URI.create(uri + "/tasks/history");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[{\"epicTaskNumber\":2,\"name\":\"Т\",\"description\":\"Т\",\"id\":3," +
                "\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}},{\"name\":\"П\"," +
                "\"description\":\"П\",\"id\":1,\"status\":\"NEW\",\"duration\":30,\"startTime\":{\"value\":" +
                "{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":1,\"minute\":15,\"second\":0," +
                "\"nano\":0}}}},{\"listSubtask\":[3,4],\"endTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":3,\"minute\":5,\"second\":0,\"nano\":0}}},\"name\":\"Р\"," +
                "\"description\":\"Р\",\"id\":2,\"status\":\"NEW\",\"duration\":25,\"startTime\":{\"value\":" +
                "{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0," +
                "\"nano\":0}}}}]", result);
    }

    @Order(4)
    @Test
    public void shouldGetAllTask() {
        String result = null;
        uri = URI.create(uri + "/tasks/task");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[{\"name\":\"П\",\"description\":\"П\",\"id\":1,\"status\":\"NEW\"," +
                "\"duration\":30,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":1,\"minute\":15,\"second\":0,\"nano\":0}}}}]", result);
    }
    @Order(8)
    @Test
    public void shouldGetTaskByID() {
        String result = null;
        uri = URI.create(uri + "/tasks/task/1");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("{\"name\":\"П\",\"description\":\"П\",\"id\":1,\"status\":\"NEW\"," +
                "\"duration\":30,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":1,\"minute\":15,\"second\":0,\"nano\":0}}}}", result);
    }

    @Order(11)
    @Test
    public void shouldAddTask() {
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"name\":\"П2\",\"description\":\"П2\",\"duration\":15," +
                "\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":3," +
                "\"minute\":30,\"second\":0,\"nano\":0}}}}");
        uri = URI.create(uri + "/tasks/task");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, Task> map = fileBackedTasksManager.getTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,Task> entry: map.entrySet()){
            id = entry.getKey();
        }

        uri = URI.create(uri + "/"+id);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"name\":\"П2\",\"description\":\"П2\",\"id\":"+id+",\"status\":\"NEW\"," +
                "\"duration\":15,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":3,\"minute\":30,\"second\":0,\"nano\":0}}}}", result);
    }

    @Order(12)
    @Test
    public void shouldUpdateTask() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, Task> map = fileBackedTasksManager.getTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,Task> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/task/"+id);
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"name\":\"П2\",\"description\":\"П2\",\"id\":"+id+"," +
                "\"status\":\"NEW\",\"duration\": 18,\"startTime\":{\"value\":{\"date\":{\"year\": 1," +
                "\"month\":1,\"day\":1},\"time\":{\"hour\": 3,\"minute\":30,\"second\":0,\"nano\":0}}}}");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"name\":\"П2\",\"description\":\"П2\",\"id\":"+id+",\"status\":\"NEW\"," +
                "\"duration\":18,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":3,\"minute\":30,\"second\":0,\"nano\":0}}}}", result);
    }
    @Order(17)
    @Test
    public void shouldDeleteTaskbyID() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, Task> map = fileBackedTasksManager.getTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,Task> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/task/"+id);

        String result = null;

        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        String newUri = String.valueOf(uri);
        newUri = newUri.replace("/"+id, "");
        uri = URI.create(newUri);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("[{\"name\":\"П\",\"description\":\"П\",\"id\":1,\"status\":\"NEW\"," +
                "\"duration\":30,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":1,\"minute\":15,\"second\":0,\"nano\":0}}}}]", result);
    }
    @Order(18)
    @Test
    public void shouldDeleteAllTask() {
        uri = URI.create(uri + "/tasks/task");
        String result = null;
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);


        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        Assertions.assertEquals("[]", result);
    }
    @Order(6)
    @Test
    public void shouldGetAllSubTask() {
        String result = null;
        uri = URI.create(uri + "/tasks/subtask");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[{\"epicTaskNumber\":2,\"name\":\"Т\",\"description\":\"Т\",\"id\":3," +
                "\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}},{\"epicTaskNumber\":2," +
                "\"name\":\"Т2\",\"description\":\"Т2\",\"id\":4,\"status\":\"NEW\",\"duration\":15," +
                "\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2," +
                "\"minute\":50,\"second\":0,\"nano\":0}}}}]", result);
    }

    @Order(7)
    @Test
    public void shouldGetSubTaskByID() {
        String result = null;
        uri = URI.create(uri + "/tasks/subtask/3");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("{\"epicTaskNumber\":2,\"name\":\"Т\",\"description\":\"Т\",\"id\":3," +
                "\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}}", result);
    }

    @Order(15)
    @Test
    public void shouldAddSubTask() {
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"epicTaskNumber\":2,\"name\":\"Т3\",\"description\":\"Т3\"" +
                ",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1}," +
                "\"time\":{\"hour\":3,\"minute\":49,\"second\":0,\"nano\": 0}}}}");
        uri = URI.create(uri + "/tasks/subtask");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, SubTask> map = fileBackedTasksManager.getSubTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,SubTask> entry: map.entrySet()){
            id = entry.getKey();
        }

        uri = URI.create(uri + "/"+id);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"epicTaskNumber\":2,\"name\":\"Т3\",\"description\":\"Т3\",\"id\":" +
                id+","+"\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,"+
                "\"day\":1},\"time\":{\"hour\":3,\"minute\":49,\"second\":0,\"nano\":0}}}}", result);
    }
    @Order(16)
    @Test
    public void shouldUpdateSubTask() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, SubTask> map = fileBackedTasksManager.getSubTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,SubTask> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/subtask/"+id);
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"epicTaskNumber\": 2,\"name\": \"Т3\"," +
                "\"description\": \"Т3\",\"duration\": 15,\"startTime\": {\"value\": {\"date\": {\"year\": 1," +
                "\"month\": 1,\"day\": 1},\"time\": {\"hour\": 3,\"minute\": 49,\"second\": 0,\"nano\": 0}}}}");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"epicTaskNumber\":2,\"name\":\"Т3\",\"description\":\"Т3\",\"id\":"+id+ ","
                + "\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":3,\"minute\":49,\"second\":0,\"nano\":0}}}}", result);
    }

    @Order(19)
    @Test
    public void shouldDeleteSubTaskbyID() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, SubTask> map = fileBackedTasksManager.getSubTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,SubTask> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/subtask/"+id);

        String result = null;

        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        String newUri = String.valueOf(uri);
        newUri = newUri.replace("/"+id, "");
        uri = URI.create(newUri);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("[{\"epicTaskNumber\":2,\"name\":\"Т\",\"description\":\"Т\",\"id\":3," +
                "\"status\":\"NEW\",\"duration\":10,\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1," +
                "\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0,\"nano\":0}}}},{\"epicTaskNumber\":2," +
                "\"name\":\"Т2\",\"description\":\"Т2\",\"id\":4,\"status\":\"NEW\",\"duration\":15," +
                "\"startTime\":{\"value\":{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2," +
                "\"minute\":50,\"second\":0,\"nano\":0}}}}]", result);
    }

    @Order(20)
    @Test
    public void shouldDeleteAllSubTask() {
        uri = URI.create(uri + "/tasks/subtask");
        String result = null;
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        Assertions.assertEquals("[]", result);
    }
    @Order(5)
    @Test
    public void shouldGetAllEpic() {
        String result = null;
        uri = URI.create(uri + "/tasks/epic");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("[{\"listSubtask\":[3,4],\"endTime\":{\"value\":{\"date\":{\"year\":1," +
                "\"month\":1,\"day\":1},\"time\":{\"hour\":3,\"minute\":5,\"second\":0,\"nano\":0}}},\"name\":\"Р\"," +
                "\"description\":\"Р\",\"id\":2,\"status\":\"NEW\",\"duration\":25,\"startTime\":{\"value\":{\"date\"" +
                ":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0," +
                "\"nano\":0}}}}]", result);
    }

    @Order(9)
    @Test
    public void shouldGetEpicByID() {
        String result = null;
        uri = URI.create(uri + "/tasks/epic/2");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                code = response.statusCode();
                result = response.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        Assertions.assertEquals(200, code);
        Assertions.assertEquals("{\"listSubtask\":[3,4],\"endTime\":{\"value\":{\"date\":{\"year\":1," +
                "\"month\":1,\"day\":1},\"time\":{\"hour\":3,\"minute\":5,\"second\":0,\"nano\":0}}},\"name\":\"Р\"," +
                "\"description\":\"Р\",\"id\":2,\"status\":\"NEW\",\"duration\":25,\"startTime\":{\"value\":" +
                "{\"date\":{\"year\":1,\"month\":1,\"day\":1},\"time\":{\"hour\":2,\"minute\":30,\"second\":0," +
                "\"nano\":0}}}}", result);
    }
    @Order(13)
    @Test
    public void shouldAddEpic() {
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"listSubtask\": [],\"endTime\": {},\"name\": \"Р2\"," +
                "\"description\": \"Р2\",\"id\": 6,\"status\": \"NEW\",\"duration\": 0,\"startTime\": {}}");
        uri = URI.create(uri + "/tasks/epic");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, EpicTask> map = fileBackedTasksManager.getEpicTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,EpicTask> entry: map.entrySet()){
            id = entry.getKey();
        }

        uri = URI.create(uri + "/"+id);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"listSubtask\":[],\"endTime\":{},\"name\":\"Р2\",\"description\":\"Р2\"," +
                "\"id\":"+id+",\"status\":\"NEW\",\"duration\":0,\"startTime\":{}}", result);
    }
    @Order(14)
    @Test
    public void shouldUpdateEpic() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, EpicTask> map = fileBackedTasksManager.getEpicTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,EpicTask> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/epic/"+id);
        String result = null;
        body = HttpRequest.BodyPublishers.ofString("{\"listSubtask\":[],\"endTime\":{},\"name\":\"Р2\"," +
                "\"description\": \"Р2-1\",\"id\":"+id+",\"status\": \"NEW\",\"duration\": 0,\"startTime\": {}}");
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("{\"listSubtask\":[],\"endTime\":{},\"name\":\"Р2\"," +
                "\"description\":\"Р2-1\",\"id\":"+id+",\"status\":\"NEW\",\"duration\":0,\"startTime\":{}}", result);
    }
    @Order(21)
    @Test
    public void shouldDeleteEpicbyID() {
        try {
            fileBackedTasksManager.fromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, EpicTask> map = fileBackedTasksManager.getEpicTaskMap();
        int id = 0;

        Task task;
        for (Map.Entry<Integer,EpicTask> entry: map.entrySet()){
            id = entry.getKey();
        }
        uri = URI.create(uri +"/tasks/epic/"+id);

        String result = null;

        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        String newUri = String.valueOf(uri);
        newUri = newUri.replace("/"+id, "");
        uri = URI.create(newUri);

        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);

        Assertions.assertEquals("[{\"listSubtask\":[],\"endTime\":{},\"name\":\"Р\",\"description\":\"Р\"," +
                "\"id\":2,\"status\":\"NEW\",\"duration\":0,\"startTime\":{}}]", result);
    }
    @Order(22)
    @Test
    public void shouldDeleteAllEpic() {
        uri = URI.create(uri + "/tasks/epic");
        String result = null;
        int code = 0;
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                code = response.statusCode();
                System.out.println("Данные отправлены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);


        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response2 = client.send(request2, handler);
            if (response2.statusCode() == 200) {
                code = response2.statusCode();
                result = response2.body();
                System.out.println("Данные получены");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response2.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        assertEquals(200, code);
        Assertions.assertEquals("[]", result);
    }

}