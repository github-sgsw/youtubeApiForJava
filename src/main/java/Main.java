import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final String INIT_FILE_PATH = "./src/main/resources/.env";
        String apiKey = "";
        try {
            // 設定ファイルを読み取る処理
            Properties property = new Properties();
            property.load(new FileInputStream(INIT_FILE_PATH));

            // 読み取ったものから実際に設定値を取り出す処理
            apiKey = property.getProperty("youtubeApiKey");

        } catch (IOException e) {
            // ファイルの読み込みエラー
            e.printStackTrace();
            return;
        }
        Scanner sc = new Scanner(System.in);

        // live中のチャンネルid  https://www.youtube.com/watch?v=*********** の***********のこと
        System.out.print("videoId -->  ");
        String videoId = sc.next();

        LiveChatId liveChatId = new LiveChatId(apiKey, videoId);
        LiveChat liveChat = new LiveChat(apiKey, videoId, liveChatId.getChatId());
        CreateLogFile createLogFile = new CreateLogFile(liveChat.getChatInfo());

        createLogFile.createCsvFile(createLogFile.createLogMap());

    }
}

