import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public ArrayList<CommentDetailsModel> getChatInfo() {
        final String url = "https://www.googleapis.com/youtube/v3/liveChat/messages?";
        JsonNode jsonNode;
        //１つ１つのコメントを格納するリスト
        ArrayList<CommentDetailsModel> cdmList = new ArrayList<>();
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

                for (JsonNode json : jsonNode.get("items")) {
                   cdmList.add(mapper.convertValue(json, CommentDetailsModel.class));
                }
                // 10秒間次のコメントを待つ
                Thread.sleep(10000);

                pageToken = Optional.ofNullable(jsonNode.get("nextPageToken").asText()).orElse("");

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("----- FINISH GET LOG -----");

        return cdmList;
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

            liveStates = jsonNode.get("items").get(0).get("snippet").get("liveBroadcastContent").asText();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return liveStates;
    }

}
