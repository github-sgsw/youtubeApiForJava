public class Main {
    public static void main(String[] args) {
        final String apiKey = "AIzaSyDXA3tm85n2YcvL3RRCdXRZ4R6pHTh0FJU";
        // live中のチャンネルid  https://www.youtube.com/watch?v=*********** の***********のこと
        final String videoId = "6l9Kt0vokUo";

        LiveChatId liveChatId = new LiveChatId(apiKey, videoId);
        LiveChat liveChat = new LiveChat(apiKey, videoId, liveChatId.getChatId());
        CreateLogFile createLogFile = new CreateLogFile(liveChat.getChatInfo());

        createLogFile.createCsvFile(createLogFile.createLogMap());

    }
}
/*
 * 仕事では自動でissueに紐づけてる？
 * 先頭にイシュー番号入れるらしい
 */

