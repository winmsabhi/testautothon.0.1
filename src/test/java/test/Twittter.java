package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;












import org.apache.commons.math3.analysis.differentiation.JacobianFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import commonFunctions.CommonMethods;
import commonFunctions.JsonCreator;
import commonFunctions.Util;

public class Twittter {
	WebDriver driver = null;
	JsonObject json = null;
	
	@BeforeSuite
	public void setup() {
		//driver = CommonMethods.launchBrowser("Windows", "Chrome");
		json = new JsonObject();
	}
	
	@BeforeMethod
	public void setthings(){
		//driver = CommonMethods.launchBrowser();
		driver = CommonMethods.launchBrowser("Windows", "Chrome");
		driver.get(Util.getConfigData("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@AfterMethod
	public void tearDown(){
		driver.close();
	}
	
	@Test(description = "This method will fetch the maximum retweet count",priority = 0)
	public void getMaxRetweetCounts() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int maxRetweet = 0;
		System.out.println(driver.getTitle());
		if (driver.getTitle().equalsIgnoreCase(
				"STeP-IN Forum (@stepin_forum) | Twitter")) {
			List<WebElement> tweets = CommonMethods.findElements("xpath",
					".//*[@id='stream-items-id']/li");
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
				int tweetCount = 0;
				element.getText().indexOf("Retweet");
				int startindex = element.getText().indexOf("Retweet");
				try {
					tweetCount = Integer.parseInt(element.getText()
							.substring(startindex + 8, startindex + 9).trim());
				} catch (Exception e) {
				}
				if (tweetCount > maxRetweet) {
					maxRetweet = tweetCount;
				}
			}
			
		}
/*		WebElement element = driver.findElement(By.tagName("header"));
		js.executeScript("arguments[0].scrollIntoView();", element); */
		driver.get(Util.getConfigData("url"));
		CommonMethods.waitForXSeconds(5000);
		System.out.println("Max Retweet count is " + maxRetweet);
		json.addProperty("top_retweet_count", maxRetweet);

	}
	
	@Test(description = "This will give users ",priority = 1)
	public void getUsers() {
		driver.get(Util.getConfigData("url"));
		CommonMethods.waitTillElementVisble("xpath", ".//*[@class='RelatedUsers-users']/div");
		driver.findElements(By
				.xpath(".//*[@class='RelatedUsers-users']/div"));
		int counter = 1;
		JsonArray jsonArray = new JsonArray();
		//JsonObject js2 = new JsonObject();
		
		HashMap<String, String> followersMap = new HashMap<String,String>();
		
		
			while (counter <= 3) {
				JsonObject js1 = new JsonObject();
				
				String name = null;
				String handleName = null;
				WebElement element = driver.findElement(By
						.xpath(".//*[@class='RelatedUsers-users']/div[" + counter + "]"));
				name = element.findElement(By.tagName("Strong")).getText();
				handleName = "@"
						+ element.findElement(By.tagName("b")).getText();
				System.out.println("Name : " + name);
				js1.addProperty("name", name);
				System.out.println("Handle Name : " + handleName);
				js1.addProperty("handleName", handleName);
				System.out.println(js1.toString());
				
				element.click();
				js1 = getFollowersandFollowing(js1,name, followersMap);
				//driver.get(Util.getConfigData("url"));
				counter += 1;
				// jArray.pu
				jsonArray.add(js1);
				System.out.println(js1.toString());

				// Actions actions = new Actions(driver);
				// actions.moveToElement(mouseHower).build().perform();
			}
			
			
			json.add("Biographics", jsonArray);
			System.out.println(json);
	}
	
	public JsonObject getFollowersandFollowing(JsonObject js,String name,HashMap<String, String> followersMap){
		
		js.addProperty("Followers", getFollowers("xpath","//div[@class='ProfileNav']/ul/li[2]/a/span[3]"));
		//String values = "Followers : " + getFollowers("xpath","//div[@class='ProfileNav']/ul/li[2]/a/span[3]");
		js.addProperty("Following", getFollowers("xpath", "//div[@class='ProfileNav']/ul/li[3]/a/span[3]"));
		//values = values + "," + "Following : " + getFollowers("xpath", "//div[@class='ProfileNav']/ul/li[3]/a/span[3]") + ";";
		//followersMap.put(name, values);
		driver.navigate().back();
		driver.get(Util.getConfigData("url"));
		return js;
	}
	
	
	public String getFollowers(String locatortype,String locatorValue){
		//CommonMethods.waitForXSeconds(5000);
		CommonMethods.waitTillElementVisble(locatortype, locatorValue);
		WebElement followers = CommonMethods.findElement(locatortype, locatorValue);
		return followers.getText();
		//driver.get(Util.getConfigData("url"));
	}
	

	@AfterSuite
	public void createFile() {
		driver.get(Util.getConfigData("upload_Url"));
		File file = new File(System.getProperty("user.dir")
				+ "//test-output/jsonFile.json");
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(file);
			fWriter.write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fWriter != null) {
				try {
					fWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
