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
    String chatId = liveChatId.getChatId();
    if (chatId == null || chatId.isEmpty()) {
      System.out.println("有効なchatIdが取得できませんでした。配信がライブ中か、videoIdが正しいか確認してください。");
      return;
    }
    LiveChat liveChat = new LiveChat(apiKey, videoId, chatId);
    try (CreateLogFile createLogFile = new CreateLogFile()) {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
          createLogFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }));
      liveChat.getChatInfo(createLogFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}

