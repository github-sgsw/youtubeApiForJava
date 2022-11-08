import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Youtube API reference
 * @link https://developers.google.com/youtube/v3/docs/videos/list
 */
public class LiveChatId {
    private String apiKey;
    private String videoId;
    LiveChatId(String apiKey, String videoId) {
        this.apiKey = apiKey;
        this.videoId = videoId;
    }

    public String getChatId() {
        String chatId = "";
        String params = "key=" + this.apiKey + "&" + "id=" + this.videoId + "&" + "part=liveStreamingDetails";
        try {
            var client = HttpClient.newHttpClient();
            String url = "https://www.googleapis.com/youtube/v3/videos?";
            var request = HttpRequest.newBuilder().uri(URI.create(url + params)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            chatId = jsonNode.get("items").get(0).get("liveStreamingDetails").get("activeLiveChatId").asText();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return chatId;
    }
}