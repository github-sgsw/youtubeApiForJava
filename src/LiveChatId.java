import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LiveChatId {
    /**
     * Youtube API reference
     * https://developers.google.com/youtube/v3/docs/videos/list
     * @param videoId live中のチャンネルid  https://www.youtube.com/watch?v=*********** の***********のこと
     * @return ライブ配信のチャット欄のID
     */
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
            System.out.println("対象のliveChatIdが取得できませんでした。ライブ配信が終了している可能性があります。");
            e.printStackTrace();
        }

        return chatId;
    }
}