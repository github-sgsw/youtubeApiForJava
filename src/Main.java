import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        final String apiKey = "AIzaSyDXA3tm85n2YcvL3RRCdXRZ4R6pHTh0FJU";
        // live中のチャンネルid  https://www.youtube.com/watch?v=*********** の***********のこと
        final String videoId = "AGcSkf16qJE"; //いずれ自動取得

        LiveChatId liveChatId = new LiveChatId(apiKey, videoId);
        LiveChat liveChat = new LiveChat(apiKey, videoId, liveChatId.getChatId());
        CreateLogFile createLogFile = new CreateLogFile(liveChat.getChatInfo());

        createLogFile.createCsvFile(createLogFile.createLogMap());

    }
}
/*
 * コメント格納もうちょいなんとかする。現状無理やりすぎて無駄多そう
 */

