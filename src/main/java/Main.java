import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        YoutubeSearchResult test = new YoutubeSearchResult();
        test.requestApi();
        /*
        Scanner sc = new Scanner(System.in);
        final String apiKey = "AIzaSyDXA3tm85n2YcvL3RRCdXRZ4R6pHTh0FJU";

        // live中のチャンネルid  https://www.youtube.com/watch?v=*********** の***********のこと
        System.out.print("videoId -->  ");
        String videoId = sc.next();

        LiveChatId liveChatId = new LiveChatId(apiKey, videoId);
        LiveChat liveChat = new LiveChat(apiKey, videoId, liveChatId.getChatId());
        CreateLogFile createLogFile = new CreateLogFile(liveChat.getChatInfo());

        createLogFile.createCsvFile(createLogFile.createLogMap());

         */

    }
}

