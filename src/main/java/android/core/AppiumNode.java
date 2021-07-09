package android.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import android.utils.UtilsAppium;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import webdriver.utils.Listener;

@Listeners(Listener.class)
@SuppressWarnings({ "rawtypes" })
public class AppiumNode {

	private static String reportDirectory = "reports";
	private static String reportFormat = "html";
	private static final Logger LOG = LoggerFactory.getLogger(AppiumNode.class);

	@BeforeMethod
	public void setBrowserName(ITestContext context, ITestResult result) {
		String browser = ((String) context.getAttribute("browser")).toUpperCase();
		UtilsAppium driverUtils = (UtilsAppium) context.getAttribute("driverUtils");
		result.setAttribute("browser", browser);
		setDeviceInfo(result);
		startDriver(null, context, driverUtils);
	}
	
	@AfterMethod
	public void setMethodFinishInfo(ITestContext context) {
		UtilsAppium driverUtils = (UtilsAppium) context.getAttribute("driverUtils");
		driverUtils.takeScreenShot();
		driverUtils.setLogInfo("Test finish");
	}

	@AfterClass
	public void closeAndClearApp(ITestContext context) {
		UtilsAppium driverUtils = (UtilsAppium) context.getAttribute("driverUtils");
		driverUtils.sendScript("adb shell am force-stop " + driverUtils.getParam("appPackage"));
		driverUtils.sendScript("adb shell pm clear " + driverUtils.getParam("appPackage"));
	}

	@AfterTest
	public static void closeDriver(ITestContext context) {
		UtilsAppium driverUtils = (UtilsAppium) context.getAttribute("driverUtils");
		AppiumDriverLocalService appiumService = (AppiumDriverLocalService) context.getAttribute("appiumService");
		LOG.info("Closing Appium driver");
		driverUtils.driver.quit();
		appiumService.stop();
		driverUtils.sleepSeconds(2);
		driverUtils.driver = null;
		driverUtils.clearParams();
	}

	public static void openAppiumNode(UtilsAppium driverUtils, ITestContext context) {
		AppiumDriverLocalService service;
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingAnyFreePort();
		builder.withCapabilities(setCapabilities(driverUtils));
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
//		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		context.setAttribute("appiumService", service);
		LOG.info("Open appium node on port => " + service.getUrl().getPort());
	}

	public static UtilsAppium startDriver(HashMap<String, String> capabilities, ITestContext context) {
		return startDriver(capabilities, context, null);
	}

	public static UtilsAppium startDriver(HashMap<String, String> capabilities, ITestContext context,
			UtilsAppium utils) {
		UtilsAppium driverUtils;
		if (utils == null) {
			driverUtils = new UtilsAppium();
			driverUtils.putCapabilities(capabilities);
			openAppiumNode(driverUtils, context);
		} else
			driverUtils = utils;

		AppiumDriverLocalService appiumService = (AppiumDriverLocalService) context.getAttribute("appiumService");
		clearData(driverUtils);
		setPhoneInHome(driverUtils);

		if (driverUtils.driver == null) {
			driverUtils.driver = new AndroidDriver(appiumService.getUrl(), setCapabilities(driverUtils));
			((AndroidDriver) driverUtils.driver).setLogLevel(Level.INFO);
			driverUtils.wait = new WebDriverWait(driverUtils.driver, 10);
		} else {
			if (driverUtils.getParam("appWaitActivity") == null)
				driverUtils.openApp(driverUtils.getParam("appPackage"), driverUtils.getParam("appActivity"));
			else
				driverUtils.openApp(driverUtils.getParam("appPackage"), driverUtils.getParam("appActivity"),
						driverUtils.getParam("appWaitActivity"));
		}

		if (checkPhoneIsLocked(driverUtils)) {
			driverUtils.sleepSeconds(2);
			setPhoneInHome(driverUtils);
			((AndroidDriver) driverUtils.driver).startActivity(
					new Activity(driverUtils.getParam("appPackage"), driverUtils.getParam("appActivity")));
		}
		forcePermissions(driverUtils);

		context.setAttribute("driverUtils", driverUtils);
		context.setAttribute("browser", "android");
		return driverUtils;
	}

	/**
	 * Fill DesiredCapabilities of current test
	 * 
	 * @param driverUtils
	 * 
	 * @param device      String with device's serialNo
	 * @return filled DesiredCapabilities
	 */
	private static DesiredCapabilities setCapabilities(UtilsAppium driverUtils) {

		int timeout = 600;
		boolean noReset = Boolean.parseBoolean(driverUtils.getParam("noReset"));
		String testName = "Suite " + driverUtils.getParam("suite");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("reportDirectory", reportDirectory);
		capabilities.setCapability("reportFormat", reportFormat);
		capabilities.setCapability("deviceName", "yosuPhone");
		capabilities.setCapability("testName", testName);
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability("resetKeyboard", "true");
		capabilities.setCapability("noReset", noReset);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, timeout);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, driverUtils.getParam("appPackage"));
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, driverUtils.getParam("appActivity"));
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
		capabilities.setCapability(MobileCapabilityType.LANGUAGE, "es");
		capabilities.setCapability(MobileCapabilityType.LOCALE, "ES");

		return capabilities;
	}

	/**
	 * Clear device data of app under test
	 * 
	 * @param driverUtils
	 * 
	 * @param device      String with device's serialNo
	 */
	private static void clearData(UtilsAppium driverUtils) {
		boolean noReset = Boolean.parseBoolean(driverUtils.getParam("noReset"));
		if (!noReset)
			driverUtils.sendScript("adb shell pm clear " + driverUtils.getParam("appPackage"));
	}

	/**
	 * Checks if current devices is locked and unlock it
	 * 
	 * @param driverUtils
	 * 
	 * @return boolean of lock status
	 */
	private static boolean checkPhoneIsLocked(UtilsAppium driverUtils) {
		boolean locked = ((AndroidDriver) driverUtils.driver).isDeviceLocked();

		if (locked)
			((AndroidDriver) driverUtils.driver).unlockDevice();

		return locked;
	}

	/**
	 * Set phone in home by ADB CLI
	 * 
	 * @param driverUtils
	 * 
	 * @param device      String with device's serialNo
	 */
	private static void setPhoneInHome(UtilsAppium driverUtils) {
		driverUtils.sendScript("adb shell am force-stop " + driverUtils.getParam("appPackage"));
		driverUtils.sendScript("adb shell input keyevent HOME");
		driverUtils.sendScript("adb shell input keyevent HOME");
	}

	/**
	 * Force all permissions by ADB CLI
	 * 
	 * @param driverUtils
	 * 
	 * @param device      String with device's serialNo
	 */
	private static void forcePermissions(UtilsAppium driverUtils) {
		String app = driverUtils.getParam("appPackage");
		String command = "adb shell pm grant " + app + " ";
		String permissions[] = { "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE",
				"android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION",
				"android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS",
				"android.permission.RECORD_AUDIO" };
		Arrays.asList(permissions).forEach(x -> driverUtils.sendScript(command + x));
	}

	public void setDeviceInfo(ITestResult result) {
		String command = "";
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		BufferedReader output = null;
		String manufacturer = null;
		String model = null;
		String apiLvl = null;
		try {
			command = "adb shell getprop ro.product.manufacturer";
			pr = rt.exec(command);
			output = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			manufacturer = output.readLine();

			command = "adb shell getprop ro.product.model";
			pr = rt.exec(command);
			output = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			model = output.readLine();

			command = "adb shell getprop ro.product.first_api_level";
			pr = rt.exec(command);
			output = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			apiLvl = output.readLine();

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		result.setAttribute("manufacturer", manufacturer);
		result.setAttribute("model", model);
		result.setAttribute("apiLvl", apiLvl);
	}

}
