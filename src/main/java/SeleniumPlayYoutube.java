import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SeleniumPlayYoutube {
    YoutubeSearchResult youtube = new YoutubeSearchResult();
    Scanner sc = new Scanner(System.in);
    WebDriver driver = new ChromeDriver();
    public void createDriver() {

        youtube.requestApi();
        System.out.print("再生する動画id -->  ");
        String playId = sc.next();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver.get("https://www.youtube.com/watch?v=" + playId);
    }
}