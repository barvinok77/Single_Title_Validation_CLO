package core;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Single_Title_CLO {
	
	@Parameter(names = {"-u", "--url"}, description = "URL" )
	static String url;
	
	@Parameter(names = {"-t", "--title_expected"}, description = "Title Expected" )
	static String title_expected;
	
	@Parameter(names = "--help", help = true, hidden = true)
	static boolean help;

	public static void main(String[] args) {
	
		 JCommander clo = new JCommander(new Single_Title_CLO(), args);
		
		if (help) {
			clo.usage();
			System.exit(0);
		}
		
		if (url == null) {
			System.err.println("URL is empty");
			System.exit(1);
		} else if (title_expected == null) {
			System.err.println("Title is empty");
			System.exit(1);
		} else {
			System.out.println("URL: " + url + ", Title: " + title_expected);
		}
		
		WebDriver driver = new FirefoxDriver();
		String text_case_id = "TC-001.01";
		String useragentregex = "(?:Mozilla/5.0)\\s(?:\\(.*\\))\\s(?:Gecko/\\d{8})\\s(\\w+)/(\\d+.\\d+)";
		String useragent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		Matcher m_browser = Pattern.compile(useragentregex).matcher(useragent);m_browser.find();
		
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String title_actual = driver.getTitle();

		if (title_expected.equals(title_actual)) {
			System.out.println("Test Case ID: \t\t" + text_case_id);
			System.out.println("Browser: \t\t" + m_browser.group(1) + " " + m_browser.group(2));
			System.out.println("URL: \t\t\t" + url);
			System.out.println("Title Expected: \t" + title_expected);
			System.out.println("Title Actual: \t\t" + title_actual);
			System.out.println("Test Case Result: \t" + "PASSED");
			
		} else {
			System.out.println("Test Case ID: \t\t" + text_case_id);
			System.out.println("Browser: \t\t" + m_browser.group(1) + " " + m_browser.group(2));
			System.out.println("URL: \t\t\t" + url);
			System.out.println("Title Expected: \t" + title_expected);
			System.out.println("Title Actual: \t\t" + title_actual);
			System.out.println("Test Case Result: \t" + "FAILED");
		}
		
		System.out.println("\nUserAgent: \t\t" + (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;"));
		
		driver.quit();

	}

}
