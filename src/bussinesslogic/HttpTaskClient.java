package bussinesslogic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpTaskClient {

    HttpClient client = HttpClient.newHttpClient();
    URI url = URI.create("http://localhost:8080/tasks/task/");
    HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
    HttpResponse<String> response;

    {
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}