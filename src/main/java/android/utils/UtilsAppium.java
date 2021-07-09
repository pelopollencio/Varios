package android.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import webdriver.utils.CommonActions;

@SuppressWarnings({ "rawtypes" })
public class UtilsAppium extends CommonActions {

	/**
	 * Send Enter by ADB command
	 */
	public boolean sendEnter() {
		try {
			sendScript("adb shell input keyevent ENTER");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Scrolls to direction from the middle of screen Directions = down, up, right
	 * and left
	 * 
	 * @param direction String with direction
	 */
	private void scrollDirection(String direction) {
		Dimension size = driver.manage().window().getSize();
		int width = (int) size.width;
		int height = (int) (size.height);
		int middleWidth = (int) (width / 2);
		int middleHeight = (int) (size.height / 2);

		TouchAction action = new TouchAction((MobileDriver) driver);

		if ("down".equalsIgnoreCase(direction)) {
			PointOption init = new PointOption().withCoordinates(middleWidth, middleHeight);
			PointOption finish = new PointOption().withCoordinates(middleWidth, 20);
			action.longPress(init).moveTo(finish).release().perform();
		}
		if ("up".equalsIgnoreCase(direction)) {
			PointOption init = new PointOption().withCoordinates(middleWidth, middleHeight);
			PointOption finish = new PointOption().withCoordinates(middleWidth, height - 20);
			action.longPress(init).moveTo(finish).release().perform();
		}
		if ("right".equalsIgnoreCase(direction)) {
			PointOption init = new PointOption().withCoordinates(width - 20, middleHeight);
			PointOption finish = new PointOption().withCoordinates(20, middleHeight);
			action.longPress(init).moveTo(finish).release().perform();
		}
		if ("left".equalsIgnoreCase(direction)) {
			PointOption init = new PointOption().withCoordinates(20, middleHeight);
			PointOption finish = new PointOption().withCoordinates(width - 20, middleHeight);
			action.longPress(init).moveTo(finish).release().perform();
		}

	}

	public boolean scrollTo(By locator) {
		try {
			while (!waitForVisibility(locator, 3)) {
				scrollDown();
			}
			setLogInfo("Scroll to locator => " + locator.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean scrollTo(WebElement element) {
		try {
			while (!waitForVisibility(element, 3)) {
				scrollDown();
			}
			setLogInfo("Scroll to element => " + element.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Scroll down
	 */
	public boolean scrollDown() {
		try {
			scrollDirection("down");
			setLogInfo("Scroll down");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Scroll up
	 */
	public boolean scrollUp() {
		try {
			scrollDirection("up");
			setLogInfo("Scroll up");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Scroll right
	 */
	public boolean scrollRight() {
		try {
			scrollDirection("right");
			setLogInfo("Scroll right");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Scroll left
	 */
	public boolean scrollLeft() {
		try {
			scrollDirection("left");
			setLogInfo("Scroll left");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void openApp(String appPackage, String appActivity) {
		((AndroidDriver) driver).startActivity(new Activity(appPackage, appActivity));
	}

	public void openApp(String appPackage, String appActivity, String waitActivity) {
		((AndroidDriver) driver).startActivity(new Activity(appPackage, appActivity).setAppWaitActivity(waitActivity));
	}

}
