import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class LiveChatId {
  /**
   * Youtube API reference
   * https://developers.google.com/youtube/v3/docs/videos/list
   *
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
    String params = "key=" + this.apiKey + "&id=" + this.videoId + "&part=liveStreamingDetails";

    try {
      var client = HttpClient.newHttpClient();
      String url = "https://www.googleapis.com/youtube/v3/videos?";
      var request = HttpRequest.newBuilder().uri(URI.create(url + params)).build();
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());

      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonNode = mapper.readTree(response.body());

      // videoIDが取得できなかった場合は空文字を返す
      JsonNode items = jsonNode.get("items");
      if (items == null || !items.has(0)) {
        System.out.println("chatIdが取得できませんでした");
        return "";
      }

      JsonNode liveStreamingDetails = items.get(0).get("liveStreamingDetails");
      if (liveStreamingDetails == null || liveStreamingDetails.get("activeLiveChatId") == null) {
        System.out.println("activeLiveChatIdが取得できませんでした");
        return "";
      }

      chatId = liveStreamingDetails.get("activeLiveChatId").stringValue();

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return chatId;
  }
}
