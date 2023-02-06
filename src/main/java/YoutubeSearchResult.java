import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YoutubeSearchResult {
    Scanner sc = new Scanner(System.in);
    JsonNode jsonNode;
    ObjectMapper mapper = new ObjectMapper();

    public void requestApi() {
        System.out.print("検索ワード -->  ");
        String searchQuery = sc.next();
        searchQuery = searchQuery.replaceAll(" |　", "+");
        try {
            var client = HttpClient.newHttpClient();
            String url = "https://www.googleapis.com/youtube/v3/search" +
                    "?key=AIzaSyDXA3tm85n2YcvL3RRCdXRZ4R6pHTh0FJU" +
                    "&type=video" +
                    "&part=snippet" +
                    "&maxResults=5" +
                    "&q=" + searchQuery;
            var request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            jsonNode = mapper.readTree(response.body());

            for (JsonNode json : jsonNode.get("items")) {
                String id = json.get("id").get("videoId").asText();
                String title = json.get("snippet").get("title").asText();
                System.out.println("id -> " + id + "\n" +  "title -> " + title);
            }

        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * とりあえずなんとなくidとtitleはmapに格納したけど
 * コンソールに表示させたidコピペしてchromeDriver起動するから格納処理いらんくね感でできた；；
 * ↑map関連のコメントアウトもろもろ消した
 *
 * 返却されるjson確認用
 * https://www.googleapis.com/youtube/v3/search?key=AIzaSyDXA3tm85n2YcvL3RRCdXRZ4R6pHTh0FJU&type=video&part=snippet&order=viewCount&maxResults=5&q=
 */
