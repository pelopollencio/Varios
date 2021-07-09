package selenium.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import selenium.utils.UtilsSelenium;
import webdriver.utils.Listener;

@Listeners(Listener.class)
/**
 * Manage creation and destruction of selenium driver
 * 
 * @author Jose Sanjuan Gonzalez
 *
 */
public class SeleniumCore {

	private final static String DRIVERS_PATH = "lib/drivers/";
	private final static String REMOTE_WB_URL = "http://127.0.0.1:4444/wd/hub";
	private final boolean CHECK_JS_ERRORS = true;
	private final boolean DETAILED_JS_ERRORS_REPORT = true;
	private static final Logger LOG = LoggerFactory.getLogger(SeleniumCore.class);

	public static UtilsSelenium openDriver(String browser, ITestContext context) {
		UtilsSelenium driverUtils = new UtilsSelenium();
		String extension = System.getProperty("os.name").contains("indows") ? ".exe" : "";
		driverUtils.putParam("browser", browser);
		ChromeOptions chromeOptions;

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + "chromedriver" + extension);
			chromeOptions = new ChromeOptions();
			LoggingPreferences logPrefs = new LoggingPreferences();

			logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
			chromeOptions.addArguments("--incognito");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.setCapability("goog:loggingPrefs", logPrefs);
			if (extension.equals("")) {
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--headless");
			}
			driverUtils.driver = new ChromeDriver(new ChromeDriverService.Builder().usingAnyFreePort().build(),
					chromeOptions);
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + "geckodriver" + extension);
			FirefoxOptions firefoxOptions = new FirefoxOptions();

			firefoxOptions.addArguments("--private");
			if (extension.equals(""))
				firefoxOptions.addArguments("-headless");

			driverUtils.driver = new FirefoxDriver(new GeckoDriverService.Builder().usingAnyFreePort().build(),
					firefoxOptions);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", DRIVERS_PATH + "msedgedriver" + extension);
			EdgeOptions edgeOptions = new EdgeOptions();

			edgeOptions.setCapability("InPrivate", true);
			edgeOptions.setCapability("requireWindowFocus", false);
//			edgeOptions.setCapability("unexpectedAlertBehaviour", "accept");

			driverUtils.driver = new EdgeDriver(new EdgeDriverService.Builder().usingAnyFreePort().build(),
					edgeOptions);
			break;
		case "explorer":
			System.setProperty("webdriver.ie.driver", DRIVERS_PATH + "IEDriverServer" + extension);
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();

			ieOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

			driverUtils.driver = new InternetExplorerDriver(
					new InternetExplorerDriverService.Builder().usingAnyFreePort().build(), ieOptions);
			break;
		case "remote": // This needs to have the selenium hub up
			DesiredCapabilities remoteCap = new DesiredCapabilities();
			remoteCap.setBrowserName(BrowserType.EDGE);
			remoteCap.setPlatform(Platform.WINDOWS);
			try {
				driverUtils.driver = new RemoteWebDriver(new URL(REMOTE_WB_URL), remoteCap);
			} catch (MalformedURLException e) {
				driverUtils.setLogError("Exception while create remote driver: " + e.getMessage());
				driverUtils.assertTrue(false);
			}
			break;
		default:
			LOG.error("Unknow browser: " + browser);
			System.exit(1);
			break;
		}

		LOG.info("Opened a " + browser + " Selenium driver");
		driverUtils.wait = new WebDriverWait(driverUtils.driver, 10);
		driverUtils.driver.manage().window().maximize();
		driverUtils.putParam("mainWindow", "");
		driverUtils.saveMainWindow();

		context.setAttribute("driverUtils", driverUtils);
		context.setAttribute("browser", browser);
		return driverUtils;
	}

	@BeforeMethod
	public void setBrowserName(ITestContext context, ITestResult result) {
		String browser = ((String) context.getAttribute("browser")).toUpperCase();
		result.setAttribute("browser", browser);
	}

	@AfterMethod
	public void setMethodFinishInfo(ITestContext context) {
		UtilsSelenium driverUtils = (UtilsSelenium) context.getAttribute("driverUtils");
		driverUtils.takeScreenShot();
		driverUtils.setLogInfo("Test finish");

		if (CHECK_JS_ERRORS && driverUtils.getParam("browser").equals("chrome"))
			driverUtils.checkJSErrors(DETAILED_JS_ERRORS_REPORT);
	}

	@AfterClass
	public void closeDriver(ITestContext context) {
		UtilsSelenium driverUtils = (UtilsSelenium) context.getAttribute("driverUtils");
		String browser = (String) context.getAttribute("browser");
		LOG.info("Closing " + browser + " Selenium driver");
		driverUtils.driver.quit();
		driverUtils.driver = null;
		driverUtils.clearParams();
	}

}
