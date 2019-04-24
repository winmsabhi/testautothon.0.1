package commonFunctions;

import net.sourceforge.htmlunit.corejs.javascript.ast.ErrorCollector;

import org.apache.commons.logging.Log;
import org.apache.poi.ss.formula.DataValidationEvaluator.OperatorEnum;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.Cache;

public class CommonMethods {
	public static WebDriver driver;
	
	public static void scrollPageRight() {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT)
					.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT)
					.perform();
			actions.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT)
					.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT)
					.perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void scrollPageLeft() {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT)
					.sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT)
					.perform();
			actions.sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT)
					.sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT)
					.perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void scrollPageUp() {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ARROW_UP).sendKeys(Keys.ARROW_UP)
					.sendKeys(Keys.ARROW_UP).sendKeys(Keys.ARROW_UP).perform();
			actions.sendKeys(Keys.ARROW_UP).sendKeys(Keys.ARROW_UP)
					.sendKeys(Keys.ARROW_UP).sendKeys(Keys.ARROW_UP).perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void scrollPageDown() {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
					.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
					.perform();
			actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
					.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
					.perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void refreshPage() {
		waitForXSeconds(2000);
		driver.navigate().refresh();
		waitForXSeconds(5000);
		waitForElementPresence("page_loading");
	}
	
	public static void waitForXSeconds(int timeinMillis) {
		long timeNow = System.currentTimeMillis();
		long expectedWait = timeNow + timeinMillis;
		while (expectedWait > timeNow) {
			timeNow = System.currentTimeMillis();
		}
	}
	
	public static void waitForElementPresence(String locatorName) {
		String locatorType = Xls_Reader.getLocatorType(locatorName);
		String locatorValue = Xls_Reader.getLocatorValue(locatorName);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		if (locatorType.toUpperCase().equals("CSS")) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(locatorValue)));
			} catch (Throwable t) {
				Log.error(locatorName + ": was not present");
				ErrorCollector.VerifyFail(locatorName + ": was not present");
			}
		} else if (locatorType.toUpperCase().equals("XPATH")) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(locatorValue)));
			} catch (Throwable t) {
				Log.error(locatorName + ": was not present");
				ErrorCollector.VerifyFail(locatorName + ": was not present");
			}
		} else if (locatorType.toUpperCase().equals("LINKTEXT")) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.linkText(locatorValue)));
			} catch (Throwable t) {
				Log.error(locatorName + ": was not present");
				ErrorCollector.VerifyFail(locatorName + ": was not present");
			}
		}
	}
	
	public static WebDriver launchBrowser() {
		DesiredCapabilities capabilities = null;
		String browserType = Util.getConfigData("browser");
		try {
			if (browserType.toUpperCase().equals("CHROME")) {
				System.out.println("chrome");
				capabilities = DesiredCapabilities.chrome();
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "//drivers//chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
				driver = new ChromeDriver(capabilities);
				
			} else if (browserType.toUpperCase().equals("IE")) {
				System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
				DesiredCapabilities cap= DesiredCapabilities.internetExplorer();
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(cap);
			} else if (browserType.toUpperCase().equals("FIREFOX")) {
				driver = new FirefoxDriver();
			}else {
				//Log.error("Browser not valid : " + browserType);
			}
		} catch (Throwable t) {
			// TODO: handle exception
			//Log.error("Unable to launch browser :" + browserType);
			//ErrorCollector.VerifyFail("unable to launch browser :" + browserType);
		}
		return driver;
	}
}
