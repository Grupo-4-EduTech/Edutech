import org.json.JSONObject;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://hooks.slack.com/services/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";

    public static void enviarMensagem(JSONObject content)throws IOException,InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("aceppt", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));

    }
}
