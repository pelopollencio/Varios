package webdriver.utils;

import static webdriver.utils.extentreport.ExtentTestManager.getTest;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * This class manage params, log report, and data provider
 * 
 * @author Jose Sanjuan Gonzalez
 *
 */
public class CommonUtils {

	private HashMap<String, String> params = new HashMap<>();
	public final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	public static String suiteName = "";
	public static String reportPath = "";

	public void putCapabilities(HashMap<String, String> capabilities) {
		params.putAll(capabilities);
	}
	
	public void putParam(String key, String value) {
		params.put(key, value);
	}

	public String getParam(String key) {
		return params.get(key);
	}

	public void clearParams() {
		params.clear();
	}

	public String getLocatorFromElement(WebElement element) {
		String locator = element.toString();
		try {
			locator = locator.substring(locator.indexOf("-> ") + 3);
		} catch (Exception e) {
			LOG.error("Error creating locator from webelement");
			LOG.error(e.getMessage());
		}
		return locator;
	}

	/**
	 * Check if a port is currently on use or not
	 * 
	 * @param port integer with port number
	 * @return boolean, true if port is free
	 */
	public boolean checkIfPortIsBusy(int port) {
		boolean running = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (IOException e) {
			running = true;
		} finally {
			socket = null;
		}
		return running;
	}

	/**
	 * Set new line into log info with the last screen capture
	 * 
	 * @param action
	 * @return
	 */
	public void setLogInfo(String action) {
		if (getParam("screenshot") == null || getParam("screenshot").isEmpty())
			getTest().log(Status.INFO, action);
		else
			getTest().log(Status.INFO, action,
					MediaEntityBuilder.createScreenCaptureFromPath(getParam("screenshot")).build());
		Reporter.log(action + "<br>");
		LOG.info(action);
		putParam("screenshot", "");
	}
	
	/**
	 * Set new line into log info with the last screen capture
	 * 
	 * @param action
	 * @return
	 */
	public void setLogPass(String action) {
		if (getParam("screenshot") == null || getParam("screenshot").isEmpty())
			getTest().log(Status.PASS, action);
		else
			getTest().log(Status.PASS, action,
					MediaEntityBuilder.createScreenCaptureFromPath(getParam("screenshot")).build());
		Reporter.log(action + "<br>");
		LOG.info(action);
		putParam("screenshot", "");
	}

	/**
	 * Set new line into log with the last screen capture
	 * 
	 * @param action
	 * @return
	 */
	public void setLogError(String action) {
		if (getParam("screenshot") == null || getParam("screenshot").isEmpty())
			getTest().log(Status.FAIL, action);
		else
			getTest().log(Status.FAIL, action,
					MediaEntityBuilder.createScreenCaptureFromPath(getParam("screenshot")).build());
		Reporter.log(action + "<br>");
		LOG.error(action);
		putParam("screenshot", "");
	}

	/**
	 * Set new line into log with the last screen capture
	 * 
	 * @param action
	 * @return
	 */
	public void setLogWarning(String action) {
		if (getParam("screenshot") == null || getParam("screenshot").isEmpty())
			getTest().log(Status.WARNING, action);
		else
			getTest().log(Status.WARNING, action,
					MediaEntityBuilder.createScreenCaptureFromPath(getParam("screenshot")).build());
		Reporter.log(action + "<br>");
		LOG.warn(action);
		putParam("screenshot", "");
	}

	public String getTimeStamp() {
		return String.valueOf(new Date().getTime());
	}

}
