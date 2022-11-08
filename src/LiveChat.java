import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
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
    private Optional<String> pageToken = Optional.ofNullable(null);

    public ArrayList<CommentDetailsModel> getChatInfo() {
        final String url = "https://www.googleapis.com/youtube/v3/liveChat/messages?";
        JsonNode jsonNode;
        ArrayList<CommentDetailsModel> cdmList = new ArrayList<>();
        var client = HttpClient.newHttpClient();
        System.out.println("----- STATE GET LOG -----");
        while (Objects.equals(getLiveStates(), "live")) {
            try {
                Thread.sleep(10000);
                String params = "key=" + apiKey + "&" + "liveChatId=" + chatId + "&" + "part=id,snippet,authorDetails";
                if (pageToken.isPresent()) {
                    params = params + "&pageToken=" + pageToken.get();
                }

                var request = HttpRequest.newBuilder().uri(URI.create(url + params)).build();
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper mapper = new ObjectMapper();
                jsonNode = mapper.readTree(response.body());

                for (JsonNode json : jsonNode.get("items")) {
                   cdmList.add(mapper.convertValue(json, CommentDetailsModel.class));
                }

                this.pageToken = Optional.ofNullable(jsonNode.get("nextPageToken").asText());

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("----- FINISH GET LOG -----");

        return cdmList;
    }

    public String getLiveStates() {
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
