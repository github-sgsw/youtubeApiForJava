import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

public class SeleniumPlayYoutube {
    YoutubeSearchResult youtube = new YoutubeSearchResult();
    Scanner sc = new Scanner(System.in);
    WebDriver driver = new ChromeDriver();
    public void createDriver() {
        youtube.requestApi();
        System.out.print("再生する動画id -->  ");
        String playId = sc.next();

        // 動画idで直検索
        driver.get("https://www.youtube.com/watch?v=" + playId);
        /**
         * 1. 広告スキップなし→そのまま動画スタート...5秒くらいループさせて終わり
         * 2. 5 ~ 6秒後にスキップボタン表示→動画スタート...広告中無限ループ時にスキップボタンが有効になったらクリックして飛ばす
         * 3. スキップできない広告→5 ~ 6秒後にスキップボタン表示(2回目の広告)...故意的にこれ発生させれないから対応できてるかわからない。
         *    ２回目でxパス変わっていたら無理そう
         */
        while (driver.findElement((By.xpath("//*[@id=\"movie_player\"]/div[4]"))).isDisplayed()) {
            System.out.println("広告検知");
            var adArea = driver.findElement(By.xpath("//*[@id=\"skip-button:6\"]/span/button"));
            if (adArea.isDisplayed()) adArea.click();
        }
        System.out.println("広告なし");
    }
}