package bussinesslogic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();

    private String path;
    private URI url;

    private String token;

    public String getToken() {
        return token;
    }

    public KVTaskClient() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void put(String key, String json){
        url = URI.create(getPath()+"/save"+"/"+key+"?API_TOKEN="+getToken());
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
                System.out.println("Данные загруженны в хранилище на сервере.");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        }catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }


    public String load(String key){
        String value = null;

        url = URI.create(getPath()+"/load"+"/"+key+"?API_TOKEN="+getToken());
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request,handler);
            if (response.statusCode() == 200) {
                value = response.body();
                // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
                System.out.println("Данные загруженны в хранилище на сервере.");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        }catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return value;
    }

    public void getTokenFromServer(String path)  {
        setPath(path);
        url = URI.create(getPath()+"/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .GET()
                .build();
    try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем, успешно ли обработан запрос
        if (response.statusCode() == 200) {
            // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
            JsonElement jsonElement = JsonParser.parseString(response.body());
            if (!jsonElement.isJsonPrimitive()) { // проверяем, точно ли мы получили JSON-объект
                System.out.println("Ответ от сервера не соответствует ожидаемому.");
                return;
            }
            // Плдучаем строку из  JSON-элемента
            String jsonObject = jsonElement.getAsString();

            // Получение токена
            setToken(jsonObject);
        } else {
            System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
        }
        }catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
        System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }
}

