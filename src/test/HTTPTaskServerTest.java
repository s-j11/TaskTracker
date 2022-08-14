package test;

import bussinesslogic.FileBackedTasksManager;
import bussinesslogic.HTTPTaskServer;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.EnumSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Перед каждым тестированием необходимо восстановить файл store2.csv из папки Old, инаце идет измененеие данных
// в результате преобразований. Тесты запускаются последовательно.
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

    @Test
    public void should1GetAllTasks() {
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

    @Test
    public void should2GetAllTask() {
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

    @Test
    public void should3GetTaskByID() {
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

    @Test
    public void should4AddTask() {
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

    @Test
    public void should5UpdateTask() {
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

    @Test
    public void should6DeleteTaskbyID() {
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
    @Test
    public void should7DeleteAllTask() {
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
    @Test
    public void should8GetAllSubTask() {
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

    @Test
    public void should9GetSubTaskByID() {
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

    @Test
    public void should10AddSubTask() {
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

    @Test
    public void should11UpdateSubTask() {
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

    @Test
    public void should12DeleteSubTaskbyID() {
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

    @Test
    public void should13DeleteAllSubTask() {
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

}