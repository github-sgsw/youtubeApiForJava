import java.util.Scanner;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        private static final String INIT_FILE_PATH = "resourse/.env";
        private static final Properties properties;
        try {
            // 設定ファイルを読み取る処理
            Properties property = new Properties();
            property.load(new FileInputStream(PROPERTY_FILE));

            // 読み取ったものから実際に設定値を取り出す処理
            final String apiKey = property.getProperty("youtubeApiKey");

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

