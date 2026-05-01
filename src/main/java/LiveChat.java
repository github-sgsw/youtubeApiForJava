import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Youtube API reference
 * https://developers.google.com/youtube/v3/live/docs/liveChatMessages/list
 */
public class LiveChat {
    private String apiKey;
    private String videoId;
    private String chatId;

    LiveChat (String apiKey, String videoId, String chatId) {
        this.apiKey = apiKey;
        this.videoId = videoId;
        this.chatId = chatId;
    }

    public void getChatInfo(CreateLogFile createLogFile) {
        final String url = "https://www.googleapis.com/youtube/v3/liveChat/messages?";
        JsonNode jsonNode;
        ObjectMapper mapper = new ObjectMapper();
        var client = HttpClient.newHttpClient();
        //初回読み込み時以降のコメント差分取得用
        String pageToken = "";
        String params = "key=" + apiKey + "&liveChatId=" + chatId + "&part=id,snippet,authorDetails" + "&pageToken=";

        System.out.println("----- STATE GET LOG -----");

        while (Objects.equals(getLiveStates(), "live")) {
            try {
                var request = HttpRequest.newBuilder().uri(URI.create(url + params + pageToken)).build();
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                jsonNode = mapper.readTree(response.body());

                JsonNode items = jsonNode.get("items");
                if (items != null) {
                    for (JsonNode json : items) {
                        createLogFile.append(mapper.convertValue(json, CommentDetailsModel.class));
                    }
                }
                // 5秒間次のコメントを待つ
                Thread.sleep(5000);

                pageToken = Optional.ofNullable(jsonNode.get("nextPageToken")).map(JsonNode::stringValue).orElse("");

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("----- FINISH GET LOG -----");
    }

    public String getLiveStates() {
        /**
         * ライブステータスを取得するメソッド
         * update...予約中, live...配信中, none...配信終了
         * @return 現在のライブステータスを返却します。
         */
        String params = "key=" + this.apiKey + "&" + "id=" + this.videoId + "&" + "part=snippet";
        String url = "https://www.googleapis.com/youtube/v3/videos?";
        String liveStates = "";
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder().uri(URI.create(url + params)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            JsonNode items = jsonNode.get("items");
            if (items == null || !items.has(0) || items.get(0) == null) {
                return "none"; // no items -> treat as not live
            }
            JsonNode snippet = items.get(0).get("snippet");
            if (snippet == null) {
                return "none";
            }
            JsonNode liveContent = snippet.get("liveBroadcastContent");
            liveStates = liveContent != null ? liveContent.stringValue() : "none";

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return liveStates;
    }

}
