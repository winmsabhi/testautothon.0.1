package test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import commonFunctions.CommonMethods;
import commonFunctions.Util;

public class Twittter {
	@Test
	public void test() {
		WebDriver driver = CommonMethods.launchBrowser();
		driver.get(Util.getConfigData("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int maxRetweet = 0;
		if (driver.getTitle().equalsIgnoreCase(
				"STeP-IN Forum (@stepin_forum) | Twitter")) {
			// twite xpath js-stream-item stream-item stream-item
			// CommonMethods.scrollPageDown();
			List<WebElement> tweets = CommonMethods.findElements("xpath",
			 ".//*[@id='stream-items-id']/li");
					//".//button[@class='ProfileTweet-actionButton js-actionButton js-actionRetweet']");
			while (tweets.size() < 50) {
				System.out.println("Total tweets on this page are : "
						+ tweets.size());
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				tweets = CommonMethods.findElements("xpath",
						".//*[@id='stream-items-id']/li");
			}
			System.out.println("Total tweets on this page are : "
					+ tweets.size());
			
			for (int i = 0; i < tweets.size(); i++) {
				WebElement element = tweets.get(i);
				String text = null;
				int tweetCount = 0 ; 
				String twitterHandleName = "";
				element.getText().indexOf("Retweet");
				int startindex = element.getText().indexOf("Retweet");
				//System.out.println(element.getText().indexOf("Retweet")+  7);
				try {
					tweetCount = Integer.parseInt(element.getText().substring(startindex + 8, startindex + 9  ).trim());
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(e.getMessage());
				}
				
				//System.out.println(tweetCount);
				if(tweetCount > maxRetweet){
					System.out.println("tweet count is : " + tweetCount);
					System.out.println("maxRetweet count is : " + maxRetweet);
					maxRetweet = tweetCount;					
				}
				// System.out.println(element.getText().equalsIgnoreCase("Retweet"));
				// div[@Title='Retweet']/following-sibling::span/span
			}
		}
		System.out.println("Max Retweet count is " + maxRetweet);
	}
}
