package selenium_test;

import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {
	public static void main(String[] args) {
		//웹드라이버 세팅 - 크롬 사용
		System.setProperty("webdriver.chrome.driver", "C:\\Bigdata\\webdriver\\chromedriver.exe");
		
		//크롬 브라우저 옵션 - 최대화, 테스트도구 알림 숨김
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		
		//크롬 브라우저 오픈
		WebDriver driver = new ChromeDriver(options);
		
		//접속 사이트 지정(페이지 이동)
		driver.get("https://movie.naver.com/movie/bi/mi/basic.nhn?code=136873");
		
//		//폼 객체 획득 - id속성 사용
//		WebElement id = driver.findElement(By.id("username"));
//		WebElement pw = driver.findElement(By.id("password"));
//		
//		id.clear();
//		pw.clear();
//		
//		//폼 객체에 값 입력(key in)
//		id.sendKeys("아이디");
//		pw.sendKeys("비밀번호");
		
		//페이지 소스 출력
		String page = driver.getPageSource();
		Document doc = Jsoup.parse(page);
		System.out.println(doc.select("h3.h_movie > a:nth-child(1)").first().text());
		
		//브라우저 종료
		// driver.quit();
	}
}
